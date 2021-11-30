package freshStart

import freshStart.commands.ICommand
import freshStart.events.Event
import freshStart.events.ReservationIsProposedOnSharedTable

class MaitreD2(sharedTable: SharedTable) {
    var events: Collection<Event> = setOf<Event>()  //TODO : avoir un getter readonly

    fun handle(command: ICommand) {
       events = events.plus(ReservationIsProposedOnSharedTable(1))
    }

}
