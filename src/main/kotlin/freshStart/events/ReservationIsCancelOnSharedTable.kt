package freshStart.events

import freshStart.NumeroDeReservation

data class ReservationIsCancelOnSharedTable(val reservationNumber: NumeroDeReservation) : Event
