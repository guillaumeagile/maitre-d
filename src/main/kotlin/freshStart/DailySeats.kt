package freshStart

import arrow.core.*
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

    fun removeReservation(idCustomer: IdCustomer, reservationDate: LocalDate = LocalDate.MIN): DailySeats {
        if (dailyAccumulation.entries.isEmpty())
            return DailySeats(dailyAccumulation)

        val lookupFirstReservationToDelete = lookupReservationsAtDateForCustomer(idCustomer, reservationDate)

        when (lookupFirstReservationToDelete) {
            is None -> return DailySeats(dailyAccumulation)
            is Some -> {
                val dailyAccumulation2new =
                    dailyAccumulation.entries
                        .minus(lookupFirstReservationToDelete.value)
                        .associateBy(keySelector = { it.key }, valueTransform = { it.value })
                return DailySeats(dailyAccumulation2new)
            }
        }
    }

    fun lookupReservationsAtDateForCustomer(idCustomer: IdCustomer, reservationDate: LocalDate) {
        val listAllReservationAtFixedDate = dailyAccumulation.filter { it -> it.key == reservationDate }.firstNotNullOf { it -> it.value }
        return listAllReservationAtFixedDate.filter { it -> it.first == idCustomer }.firstOrNone()
//        dailyAccumulation.entries.firstOrNone() { (date, value) -> value.any { (first, _) -> first == idCustomer && date == reservationDate } }
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
        val newDailySeats = removeReservation(searchedIdCustomer)
        return newDailySeats.addReservation(date = searchedDate, seats = seats, idCustomer = searchedIdCustomer)
    }
}