package freshStart

import java.time.LocalDate

class DailySeats(val dailyAccumulation: Map<LocalDate, Quantity> = mapOf()) {
    fun addReservation(date: LocalDate, seats: Quantity): DailySeats {
        return DailySeats(
            dailyAccumulation + Pair(
                date,
                seats + this.dailyAccumulation.getOrDefault(date, Quantity(0))
            )
        )
    }
    fun howManyReservedOn(date: LocalDate): Quantity = dailyAccumulation.getOrDefault(date, Quantity(0))
}

data class Reservation(val date: LocalDate, val quantity: Quantity)
