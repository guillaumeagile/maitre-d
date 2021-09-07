package freshStart

import freshStart.commands.ReservationCommand
import freshStart.events.Event
import freshStart.events.ReservationIsProposedOnSharedTable

class MaitreD2 {
    var events: Collection<Event> = setOf<Event>()  //TODO : avoir un getter readonly

    fun handle(command: ReservationCommand) {
       events = events.plus(ReservationIsProposedOnSharedTable(1))
    }

}
