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


    fun removeReservation(reservationNumber: IdCustomer): DailySeats {  //TODO: renommer ce param en idCustomer
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

    fun updateReservationQuantity(
        searchedIdCustomer: IdCustomer,
        searchedDate: LocalDate,
        seats: Quantity
    ): DailySeats {
     /*
        val dailyAccumulation2new =
            dailyAccumulation.entries
                .filter { (date, value) -> searchedDate != date && value.any { (first, _) ->  first != searchedIdCustomer } }
                .associateBy(keySelector = { it.key }, valueTransform = { it.value })
        return DailySeats(dailyAccumulation2new)*/
        val    newDailySeats= removeReservation(searchedIdCustomer )
        return newDailySeats.addReservation(date = searchedDate, seats = seats, idCustomer = searchedIdCustomer)
    }
}