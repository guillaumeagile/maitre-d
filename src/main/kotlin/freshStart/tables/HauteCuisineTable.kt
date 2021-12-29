package freshStart.tables

import freshStart.DailySeats
import freshStart.ITable
import freshStart.Quantity
import java.time.LocalDate

class HauteCuisineTable(
    override val size: Int,
    val dailySeatsOverallReservations: DailySeats,
    val isReserved: Boolean = false
) : ITable {

    constructor(size: Int) : this(size, DailySeats())

    companion object {
        fun createTableReservee(size: Int, dailySeatsOverallReservations: DailySeats): HauteCuisineTable =
            HauteCuisineTable(size, dailySeatsOverallReservations, true)
    }

    override fun isFull(): Boolean {
        return isReserved
    }

    override fun isFull(date: LocalDate): Boolean {
        TODO("Not yet implemented")
    }

    override fun reserve(): ITable {
        return createTableReservee(this.size, this.dailySeatsOverallReservations)
    }

    override fun reserve(date: LocalDate, qtte: Quantity): ITable {
        if (canIReserve(date, qtte))
            return createTableReservee(this.size, this.dailySeatsOverallReservations.addReservationOld(date, qtte))
        return this
    }

    override fun canIReserve(date: LocalDate, desiredQuantity: Quantity): Boolean {
        return unReservedAt(date) && isBigEnoughFor(desiredQuantity)
    }

    private fun isBigEnoughFor(desiredQuantity: Quantity) = desiredQuantity <= Quantity(size)

    private fun unReservedAt(date: LocalDate) =
        this.dailySeatsOverallReservations.howManyReservedOn(date) == Quantity(0)
}
