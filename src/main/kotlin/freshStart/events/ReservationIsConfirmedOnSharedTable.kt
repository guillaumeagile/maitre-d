package freshStart.events

import freshStart.Quantity
import java.time.LocalDate

data class ReservationIsConfirmedOnSharedTable(val reservationNumber: Int, val date: LocalDate, val qtte: Quantity) :
    Event
