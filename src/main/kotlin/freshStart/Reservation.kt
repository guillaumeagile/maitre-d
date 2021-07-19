package freshStart

import java.time.LocalDate

class DailySeats() {
    var dailyAccumulation: HashMap<LocalDate, Quantity> = HashMap<LocalDate, Quantity>()

    fun reserve(date: LocalDate, seats: Quantity) {
        this.dailyAccumulation.put(date, seats  +  this.dailyAccumulation.getOrDefault(date,  Quantity(0)))
    }

    fun howManyReservedOn(date: LocalDate): Quantity = dailyAccumulation.getOrDefault(date,  Quantity(0))
}

data class Reservation(val date: LocalDate, val quantity: Quantity) {
}
