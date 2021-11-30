package freshStart

import freshStart.commands.ICommand
import freshStart.events.Event
import freshStart.events.ReservationIsDeclinedOnSharedTable
import freshStart.events.ReservationIsProposedOnSharedTable

class MaitreD2(val sharedTable: SharedTable) {
    var events: Collection<Event> = setOf<Event>() // TODO : avoir un getter readonly
    fun handle(command: ICommand) {
        if (command.isValidReservation(sharedTable))
            events = events.plus(ReservationIsProposedOnSharedTable(reservationNumber = 1))
        else
            events = events.plus(ReservationIsDeclinedOnSharedTable(reservationNumber = 1))
    }
}
