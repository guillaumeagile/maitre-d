package freshStart

import freshStart.events.Event
import freshStart.events.ReservationIsConfirmedOnSharedTable
import java.time.LocalDate

data class SharedTable(override val size: Int, val dailySeatsOverallReservations: DailySeats) : ITable {

    fun replayOn(listEvents: Collection<Event>): SharedTable {
        var newDailySeats = DailySeats()

        listEvents.forEach {
            newDailySeats = newDailySeats.addReservation(
                (it as ReservationIsConfirmedOnSharedTable).date,
                it.qtte
            )
        }
        return SharedTable(size = this.size, newDailySeats)

    }

    constructor(size: Int) : this(size, DailySeats())

    override fun equals(other: Any?): Boolean = (other is SharedTable) && (this.size == other.size)

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
        return SharedTable(size, dailySeatsOverallReservations.addReservation(date, qtte))
    }

    override fun canIReserve(date: LocalDate, qtte: Quantity): Boolean {
        val quantityOfDesiredSeat = dailySeatsOverallReservations.howManyReservedOn(date) + qtte
        return quantityOfDesiredSeat.value <= size
    }

    override fun toString(): String {
        return "size=${this.size}"
    }
}
