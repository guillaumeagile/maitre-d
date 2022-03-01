package freshStart

import arrow.core.Option
import arrow.core.firstOrNone
import arrow.core.getOrNone
import freshStart.events.Event
import freshStart.events.ReservationIsCancelOnSharedTable
import freshStart.events.ReservationIsConfirmedOnSharedTable
import freshStart.events.ReservationQuantityIsUpdatedOnSharedTable
import java.time.LocalDate

data class SharedTable(override val size: Int, val dailySeatsOverallReservations: DailySeats) : ITable {

    fun replayOn(listEvents: Collection<Event>): SharedTable {
        var newDailySeats = DailySeats()
        listEvents.filterIsInstance<ReservationIsConfirmedOnSharedTable>().forEach {
            newDailySeats = newDailySeats.addReservation(
                it.date,
                it.qtte,
                it.idCustomer
            )
        }
        listEvents.filterIsInstance<ReservationIsCancelOnSharedTable>().forEach {
            newDailySeats = newDailySeats.removeReservation(it.idCustomer)
        }
        listEvents.filterIsInstance<ReservationQuantityIsUpdatedOnSharedTable>().forEach{
            // newDailySeats = newDailySeats.updateReservationQuantity(it.idCustomer, it.date, it.qtte )
        }

        return SharedTable(size = this.size, newDailySeats)
    }

    constructor(size: Int) : this(size, DailySeats())

    override fun isFull(): Boolean {
        return size <= 0
    }

    override fun isFull(date: LocalDate): Boolean {
        val quantityOfReservedSeat = dailySeatsOverallReservations.howManyReservedOn(date)
        return (quantityOfReservedSeat.value >= size)
    }

    override fun reserve(): ITable {
        return this
    }

    override fun reserve(date: LocalDate, qtte: Quantity, idCustomer: String): ITable {
        if (!canIReserve(date, qtte))
            return this
        return SharedTable(size, dailySeatsOverallReservations.addReservation(date, qtte, idCustomer = idCustomer))
    }

    override fun canIReserve(date: LocalDate, qtte: Quantity): Boolean {
        val quantityOfDesiredSeat = dailySeatsOverallReservations.howManyReservedOn(date) + qtte
        return quantityOfDesiredSeat.value <= size
    }

    override fun toString(): String {
        return "size=${this.size}"
    }

    fun lookupReservation(date: LocalDate, idCustomer: String): Option<Quantity> {
        return dailySeatsOverallReservations.dailyAccumulation.getOrNone(date)
            .flatMap { it.firstOrNone { (id, _) -> id == idCustomer }}.map { (_, quantity) -> quantity }
    }
}
