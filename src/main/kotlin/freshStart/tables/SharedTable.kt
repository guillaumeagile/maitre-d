package freshStart

import java.time.LocalDate

data class SharedTable(override val size: Int) : ITable {
    var dailySeatsOverallReservations = DailySeats()

    constructor( size: Int , dailySeatsOverallReservations_ : DailySeats ) : this (size)
    {
        this.dailySeatsOverallReservations = dailySeatsOverallReservations_
    }

    override fun isFull(): Boolean {
        return size <= 0
    }

     fun isFull(date: LocalDate): Boolean {
         val quantityOfReservedSeat  =  dailySeatsOverallReservations.howManyReservedOn(date)
         return (quantityOfReservedSeat.value_ >= size)

    }

    override fun reserve(): ITable {
        return this
    }

    fun reserve(date: LocalDate, qtte: Quantity): SharedTable {
        if (!canIReserve(date, qtte))
            return this
        dailySeatsOverallReservations.addReservation(date, qtte)
        return SharedTable(size, dailySeatsOverallReservations)
    }

    fun canIReserve(date: LocalDate, qtte: Quantity): Boolean {
        val quantityOfDesiredSeat  =  dailySeatsOverallReservations.howManyReservedOn(date) +  qtte
        return quantityOfDesiredSeat.value_ <= size
    }
}

