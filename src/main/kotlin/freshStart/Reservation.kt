package freshStart

import java.time.LocalDate

class DailySeats() {
    var dailyAccumulation: HashMap<LocalDate, Int> = HashMap<LocalDate, Int>()


    fun reserve(date: LocalDate, seats: Int) {
        TODO("remplacer Int par Quantity")
        this.dailyAccumulation.put(date, seats + this.dailyAccumulation.getOrDefault(date, 0))
    }
  //  TODO("remplacer return Int par Quantity")
    fun howManyReservedOn(date: LocalDate): Int = dailyAccumulation.getOrDefault(date, 0)
}

class Reservation(val date: LocalDate, val quantity: Quantity) {
    companion object {
        var dailySeats = DailySeats()
        fun create(date: LocalDate, quantity: Int, table: Table): Result<Reservation> {
            val qtt = Quantity.create(quantity)
            return qtt.fold({ q ->
                val reservedSeats = dailySeats.howManyReservedOn(date) + q.value_
                if (reservedSeats > table.size)
                    return Result.failure(NoRoomLeft())
                dailySeats.reserve(date, q.value_)
                return Result.success(Reservation(date, q))
            },
                { _ -> Result.failure(InvalidQuantityForReservation()) }
            )
        }
    }
}
