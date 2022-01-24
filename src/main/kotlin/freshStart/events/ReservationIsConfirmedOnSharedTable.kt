package freshStart.events

import freshStart.IdCustomer
import freshStart.Quantity
import java.time.LocalDate

data class ReservationIsConfirmedOnSharedTable(val idCustomer: IdCustomer, val date: LocalDate, val qtte: Quantity) :
    Event
