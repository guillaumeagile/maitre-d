package freshStart.events

interface Event

data class ReservationIsProposedOnSharedTable(val reservationNumber: Int) : Event
