package freshStart

import freshStart.events.Event
import java.time.LocalDate

data class SharedTable(override val size: Int, val dailySeatsOverallReservations: DailySeats) : ITable {

    companion object {
        fun replayOn(listEvents: Collection<Event>, initialSize: Int): SharedTable {
            return SharedTable(size = initialSize)
        }
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
