package freshStart

import freshStart.commands.ICommand
import freshStart.commands.ReservationCommand
import freshStart.events.Event
import freshStart.events.ReservationIsConfirmedOnSharedTable
import freshStart.events.ReservationIsDeclinedOnSharedTable

class MaitreD2(val sharedTableInInitialState: SharedTable) {

    var events: Collection<Event> =
        setOf<Event>() // les archiver dans un autre système pour des stats => reporting..., pour la compta... (Read ONLY)
    // l'Audit est gratuit

    fun handle(command: ICommand) {

        val sharedTableCurrentState = sharedTableInInitialState.replayOn(listEvents = events)
        val reservationCommand = (command as ReservationCommand)
        // IDEE: donner une version de la ressource à modifer
        if (sharedTableCurrentState.canIReserve(
                date = reservationCommand.wishedDate,
                qtte = Quantity(reservationCommand.guestsCount)
            )
        ) {
            // que se passerait il si un autre évènement similaire passe avant celui ci? (accès concurrent)
            // pour s'en prémunir, on incrémente la version de la ressource dans l'évènement au moment de
            // l'enregistrment de l'event, et on verifié qu' il n'existe une version égale déjà présente dans les 
            // évènements passés
            events = events.plus(
                ReservationIsConfirmedOnSharedTable(
                    reservationNumber = 1,
                    date = command.wishedDate,
                    qtte = Quantity(command.guestsCount)
                )
            )
            // l'ajout de l'évènement sera être rejeté si un autre evenement de même type
            // a été trouvé pour le même ID de ressource avec une version égale ou supérieure
        } else {
            events = events.plus(ReservationIsDeclinedOnSharedTable(reservationNumber = 1))
        }
        // envoyer un évènement pour liberer la transaction?
    }
}
