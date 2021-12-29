package freshStart

import java.time.LocalDate

typealias NumeroDeReservation = Int

data class DailySeats(
    val dailyAccumulation: Map<LocalDate, Quantity> = mapOf(),
    val dailyAccumulation2: Map<LocalDate, List<Pair<NumeroDeReservation, Quantity>>> = mapOf()
) {
    fun addReservation(date: LocalDate, seats: Quantity, reservationNumber: NumeroDeReservation): DailySeats {
        val newListAtDate = dailyAccumulation2.getOrDefault(date, emptyList()) + Pair(reservationNumber, seats)
        return DailySeats(
            dailyAccumulation, dailyAccumulation2 + Pair(date, newListAtDate)
        )
    }

    @Deprecated("refactoring à finir")
    fun addReservationOld(date: LocalDate, seats: Quantity): DailySeats {
        return DailySeats(
            dailyAccumulation + Pair(
                date,
                seats + this.dailyAccumulation.getOrDefault(date, Quantity(0))
            )
        )
    }

    @Deprecated("à enlever par refacto avec howManyReservedOnBis")
    fun howManyReservedOn(date: LocalDate): Quantity = dailyAccumulation.getOrDefault(date, Quantity(0))

    fun howManyReservedOnBis(date: LocalDate): Quantity =
        dailyAccumulation2.getOrDefault(date, emptyList()).map { it.second }
            .fold(Quantity(0)) { acc, next -> acc + next }

    // TODO (en dernier)!  https://www.baeldung.com/kotlin/immutable-collections

    fun removeReservation(reservationNumber: NumeroDeReservation): DailySeats {
        if (dailyAccumulation2.entries.isEmpty())
            return DailySeats(dailyAccumulation, dailyAccumulation2)

        val entryDeLaReservationASupprimer =
            dailyAccumulation2.entries.first { (_, value) -> value.any { (first, _) -> first == reservationNumber } }

        val dailyAccumulation2new =
            dailyAccumulation2.entries
                .minus(entryDeLaReservationASupprimer)
                .associateBy(keySelector = { it.key }, valueTransform = { it.value })
        return DailySeats(dailyAccumulation, dailyAccumulation2new)
    }
}

data class Reservation(val date: LocalDate, val quantity: Quantity)
