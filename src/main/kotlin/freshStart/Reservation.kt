package freshStart

import java.time.LocalDate

typealias NumeroDeReservation = Int

data class DailySeats(
    val dailyAccumulation: Map<LocalDate, List<Pair<NumeroDeReservation, Quantity>>> = mapOf()
) {
    fun addReservation(date: LocalDate, seats: Quantity, reservationNumber: NumeroDeReservation): DailySeats {
        val newListAtDate = dailyAccumulation.getOrDefault(date, emptyList()) + Pair(reservationNumber, seats)
        return DailySeats(
            dailyAccumulation + Pair(date, newListAtDate)
        )
    }


    fun howManyReservedOn(date: LocalDate): Quantity =
        dailyAccumulation.getOrDefault(date, emptyList()).map { it.second }
            .fold(Quantity(0)) { acc, next -> acc + next }


    fun removeReservation(reservationNumber: NumeroDeReservation): DailySeats {
        if (dailyAccumulation.entries.isEmpty())
            return DailySeats(dailyAccumulation)

        val entryDeLaReservationASupprimer =
            dailyAccumulation.entries.first { (_, value) -> value.any { (first, _) -> first == reservationNumber } }

        val dailyAccumulation2new =
            dailyAccumulation.entries
                .minus(entryDeLaReservationASupprimer)
                .associateBy(keySelector = { it.key }, valueTransform = { it.value })
        return DailySeats(dailyAccumulation2new)
    }
}

data class Reservation(val date: LocalDate, val quantity: Quantity)
