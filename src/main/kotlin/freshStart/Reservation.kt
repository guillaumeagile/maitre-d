package freshStart

import java.time.LocalDate

class DailySeats() {
    var dailyAccumulation: HashMap<LocalDate, Int> = HashMap<LocalDate, Int>()

    fun reserve(date: LocalDate, seats: Int) {
        //  TODO("remplacer Int par Quantity")
        this.dailyAccumulation.put(date, seats + this.dailyAccumulation.getOrDefault(date, 0))
    }

    fun howManyReservedOn2(date: LocalDate): Quantity =  Quantity(  dailyAccumulation.getOrDefault(date, 0))
}

data class Reservation(val date: LocalDate, val quantity: Quantity) {
}
