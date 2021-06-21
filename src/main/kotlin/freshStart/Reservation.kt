package freshStart

import java.time.LocalDate

class DailySeats() {
    var dailyAccumulation: HashMap<LocalDate, Int> = HashMap<LocalDate, Int>()

    fun reserve(date: LocalDate, seats: Int) {
        //  TODO("remplacer Int par Quantity")
        this.dailyAccumulation.put(date, seats + this.dailyAccumulation.getOrDefault(date, 0))
    }

    //  TODO("remplacer return Int par Quantity")
    fun howManyReservedOn(date: LocalDate): Int = dailyAccumulation.getOrDefault(date, 0)
}

class Reservation(val date: LocalDate, val quantity: Quantity) {
    companion object {
        var dailySeatsOverallReservations = DailySeats()
        var uniqueTable: ITable = UndefinedTable()   // SINGLETON = piège à con!

        fun create(date: LocalDate, quantity: Int, table: Table): Result<Reservation> {
            TODO ("TO FIX:    no singleton!  no var in companion !!!!")
            if (uniqueTable != table)
                return Result.failure(CannotChangeTableSize())

            val qtt = Quantity.create(quantity)

            return qtt.fold({ q ->
                val reservedSeats = dailySeatsOverallReservations.howManyReservedOn(date) + q.value_
                if (reservedSeats > table.size)
                    return Result.failure(NoRoomLeft())
                dailySeatsOverallReservations.reserve(date, q.value_)
                uniqueTable = table
                return Result.success(Reservation(date, q))
            },
                { _ -> Result.failure(InvalidQuantityForReservation()) }
            )
        }
    }
}
