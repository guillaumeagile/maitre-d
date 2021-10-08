package freshStart.events

import freshStart.Reservation

interface Event{

}

data class ReservationIsProposedOnSharedTable(val reservationNumber: Int) : Event {

}
