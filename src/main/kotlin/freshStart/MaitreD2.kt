package freshStart

import freshStart.commands.ICommand
import freshStart.commands.ReservationCommand
import freshStart.events.Event
import freshStart.events.ReservationIsDeclinedOnSharedTable
import freshStart.events.ReservationIsProposedOnSharedTable

class MaitreD2(var sharedTable: SharedTable) {
    var events: Collection<Event> = setOf<Event>() // TODO : avoir un getter readonly
    fun handle(command: ICommand) {
//        val sharedTable2 = SharedTable.replayOn(listEvents=events, initialSize=sharedTable.size)
        if (command.isValidReservation(sharedTable)) {
            // éviter la mutation de sharedTable
            sharedTable = sharedTable.reserve(
                date = (command as ReservationCommand).wishedDate, // éviter le cast pas beau et respecter la loi de Demeter
                qtte = Quantity(command.guestsCount) // respecter la loi de Demeter
            ) as SharedTable // éviter le cast pas beau
            events = events.plus(ReservationIsProposedOnSharedTable(reservationNumber = 1))
        } else
            events = events.plus(ReservationIsDeclinedOnSharedTable(reservationNumber = 1))
    }
}
