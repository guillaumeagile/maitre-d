package freshStart

import freshStart.commands.ICommand
import freshStart.commands.ReservationCommand
import freshStart.events.Event
import freshStart.events.ReservationIsConfirmedOnSharedTable
import freshStart.events.ReservationIsDeclinedOnSharedTable
import freshStart.events.ReservationIsProposedOnSharedTable

class MaitreD2(val sharedTableInInitialState: SharedTable) {

    var events: Collection<Event> = setOf<Event>()

    fun handle(command: ICommand) {
        val sharedTableCurrentState = sharedTableInInitialState.replayOn(listEvents = events)
        if (command.isValidReservation(sharedTableCurrentState)) { // FIXME: ce n'est pas à la commande de valider la réservation
            //  mais plutôt à la table de valider la commande
            sharedTableCurrentState.reserve(
                date = (command as ReservationCommand).wishedDate, // éviter le cast pas beau et respecter la loi de Demeter
                qtte = Quantity(command.guestsCount) // respecter la loi de Demeter
            )
            events = events.plus(ReservationIsProposedOnSharedTable(reservationNumber = 1))
            events = events.plus(
                ReservationIsConfirmedOnSharedTable(
                    reservationNumber = 1,
                    date = command.wishedDate,
                    qtte = Quantity(command.guestsCount)
                )
            )
        } else {
            events = events.plus(ReservationIsDeclinedOnSharedTable(reservationNumber = 1))
        }
    }
}
