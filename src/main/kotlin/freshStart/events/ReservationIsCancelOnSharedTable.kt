package freshStart.events

import freshStart.IdCustomer

data class ReservationIsCancelOnSharedTable(val idCustomer: IdCustomer) : Event
