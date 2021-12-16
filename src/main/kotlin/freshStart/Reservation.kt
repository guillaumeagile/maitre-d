package freshStart

import java.time.LocalDate

data class DailySeats(
    val dailyAccumulation: Map<LocalDate, Quantity> = mapOf(),
    val dailyAccumulation2: Map<LocalDate, List<Pair<Int, Quantity>>> = mapOf()
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
        val newListAtDate = dailyAccumulation2.getOrDefault(date, emptyList()) + Pair(reservationNumber, seats)
        return DailySeats(
            dailyAccumulation, dailyAccumulation2 + Pair(date, newListAtDate)
        )
    }


    fun howManyReservedOn(date: LocalDate): Quantity = dailyAccumulation.getOrDefault(date, Quantity(0))


    fun howManyReservedOnBis(date: LocalDate): Quantity {
//        if (!dailyAccumulation2.containsKey(date)) return Quantity(value = 0)
//        return dailyAccumulation2.getOrDefault(date, emptyList()).reduce { acc, next -> Pair(acc.first, acc.second + next.second)}.second
        return dailyAccumulation2.getOrDefault(date, emptyList()).fold(Quantity(0)) { acc, next -> acc + next.second }

    }

}

data class Reservation(val date: LocalDate, val quantity: Quantity)
