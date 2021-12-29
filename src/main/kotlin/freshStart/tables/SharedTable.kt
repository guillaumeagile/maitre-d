package freshStart

import freshStart.events.Event
import freshStart.events.ReservationIsCancelOnSharedTable
import freshStart.events.ReservationIsConfirmedOnSharedTable
import java.time.LocalDate

data class SharedTable(override val size: Int, val dailySeatsOverallReservations: DailySeats) : ITable {

    fun replayOn(listEvents: Collection<Event>): SharedTable {
        var newDailySeats = DailySeats()
        listEvents.filterIsInstance<ReservationIsConfirmedOnSharedTable>().forEach {
            newDailySeats = newDailySeats.addReservation(
                it.date,
                it.qtte,
                it.reservationNumber
            )
        }
        listEvents.filterIsInstance<ReservationIsCancelOnSharedTable>().forEach {
            newDailySeats = newDailySeats.removeReservation(it.reservationNumber)
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

    override fun reserve(date: LocalDate, qtte: Quantity): ITable {
        if (!canIReserve(date, qtte))
            return this
        return SharedTable(size, dailySeatsOverallReservations.addReservationOld(date, qtte))
    }

    override fun canIReserve(date: LocalDate, qtte: Quantity): Boolean {
        val quantityOfDesiredSeat = dailySeatsOverallReservations.howManyReservedOn(date) + qtte
        return quantityOfDesiredSeat.value <= size
    }

    override fun toString(): String {
        return "size=${this.size}"
    }
}
