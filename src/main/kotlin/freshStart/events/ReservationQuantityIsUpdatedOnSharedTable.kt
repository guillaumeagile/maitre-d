package freshStart.events

import freshStart.IdCustomer
import freshStart.Quantity
import java.time.LocalDate

data class ReservationQuantityIsUpdatedOnSharedTable(val idCustomer: IdCustomer, val date: LocalDate, val qtte : Quantity) : Event
