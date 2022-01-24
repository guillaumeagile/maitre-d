package freshStart

import java.time.LocalDate

typealias IdCustomer = String

data class DailySeats(
    val dailyAccumulation: Map<LocalDate, List<Pair<IdCustomer, Quantity>>> = mapOf()
) {
    fun addReservation(date: LocalDate, seats: Quantity, idCustomer: IdCustomer): DailySeats {
        val newListAtDate = dailyAccumulation.getOrDefault(date, emptyList()) + Pair(idCustomer, seats)
        return DailySeats(
            dailyAccumulation + Pair(date, newListAtDate)
        )
    }


    fun howManyReservedOn(date: LocalDate): Quantity =
        dailyAccumulation.getOrDefault(date, emptyList()).map { it.second }
            .fold(Quantity(0)) { acc, next -> acc + next }


    fun removeReservation(reservationNumber: IdCustomer): DailySeats {
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