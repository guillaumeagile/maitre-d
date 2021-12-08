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
        // IDEE: toujours avoir une version de la ressource à modifier    https://event-driven.io/en/optimistic_concurrency_for_pessimistic_times/
        if (sharedTableCurrentState.canIReserve(
                date = reservationCommand.wishedDate,
                qtte = Quantity(reservationCommand.guestsCount)
            )
        ) {
            // que se passerait il si un autre évènement de mutation passe avant celui ci? (accès concurrent)
            // pour s'en prémunir, on vérifie que la version de la ressource dans le dernier évènement enregistré est le même
            // si OK, on change ce numéro de version juste au moment où on enregistre l'évènement de mutation
            events = events.plus(
                ReservationIsConfirmedOnSharedTable(
                    reservationNumber = 1,
                    date = command.wishedDate,
                    qtte = Quantity(command.guestsCount)
                )
            )
            // l'ajout de l'évènement sera être rejeté si un autre évènement de mutation a été trouvé pour le même ID de ressource
            // mais portant sur une version différente de la version lue au moment du replayOn (fonction de projection)
        } else {
            events = events.plus(ReservationIsDeclinedOnSharedTable(reservationNumber = 1))
        }
        // envoyer un évènement pour liberer la transaction?
    }
}
