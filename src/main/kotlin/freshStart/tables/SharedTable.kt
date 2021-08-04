package freshStart

import java.time.LocalDate

class SharedTable(override val size: Int, val dailySeatsOverallReservations: DailySeats) : ITable {

    constructor(size: Int) : this(size, DailySeats())

    override fun equals(other: Any?): Boolean = (other is SharedTable) && (this.size == other.size)

    override fun isFull(): Boolean {
        return size <= 0
    }

    fun isFull(date: LocalDate): Boolean {
        val quantityOfReservedSeat = dailySeatsOverallReservations.howManyReservedOn(date)
        return (quantityOfReservedSeat.value_ >= size)
    }

    override fun reserve(): ITable {
        return this
    }

    fun reserve(date: LocalDate, qtte: Quantity): SharedTable {
        if (!canIReserve(date, qtte))
            return this
        return SharedTable(size, dailySeatsOverallReservations.addReservation(date, qtte))
    }

    fun canIReserve(date: LocalDate, qtte: Quantity): Boolean {
        val quantityOfDesiredSeat = dailySeatsOverallReservations.howManyReservedOn(date) + qtte
        return quantityOfDesiredSeat.value_ <= size
    }
}