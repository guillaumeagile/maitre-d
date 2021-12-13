package freshStart

import java.time.LocalDate

data class DailySeats(
    val dailyAccumulation: Map<LocalDate, Quantity> = mapOf(),
    val dailyAccumulation2: Map<LocalDate, Pair<Int, Quantity>> = mapOf()
) {


    fun addReservation(date: LocalDate, seats: Quantity): DailySeats {
        return DailySeats(
            dailyAccumulation + Pair(
                date,
                seats + this.dailyAccumulation.getOrDefault(date, Quantity(0))
            )
        )
    }

    fun addReservation(date: LocalDate, seats: Quantity, reservationNumber: Int): DailySeats {
        return DailySeats(
            dailyAccumulation, dailyAccumulation2 + Pair(date, Pair(reservationNumber, seats))
        )
    }


    fun howManyReservedOn(date: LocalDate): Quantity = dailyAccumulation.getOrDefault(date, Quantity(0))


    fun howManyReservedOnBis(date: LocalDate): Quantity {
        return dailyAccumulation2.getOrDefault(date, Pair(0, Quantity(value = 99))).second
    }

}

data class Reservation(val date: LocalDate, val quantity: Quantity)
