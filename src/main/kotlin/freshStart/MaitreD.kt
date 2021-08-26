package freshStart

import freshStart.tables.HauteCuisineTable
import java.time.LocalDate

class MaitreD(val tables: MutableList<ITable>) {
    var dailySeatsOverallReservations = DailySeats()

    fun reserve(date: LocalDate, numberOfGuests: Int): Result<Reservation> {
        val resultQuantity = Quantity.create(numberOfGuests)
        var lastObservedTable: ITable? = null;
        return resultQuantity.fold(
            { quantity ->
                val quantityOfReservedSeat =
                    dailySeatsOverallReservations.howManyReservedOn(date) + quantity   //TODO: ne plus utiliser
                var reservation: Reservation? = null
                for ((index, currentTable) in tables.withIndex()) {
                    lastObservedTable = currentTable
                    if (!currentTable.canIReserve(date, quantity))
                        continue
                    dailySeatsOverallReservations =
                        dailySeatsOverallReservations.addReservation(date, quantity)   //TODO: ne plus utiliser
                    reservation = Reservation(date, quantity)   //TODO: ne plus utiliser
                    tables[index] = currentTable.reserve(date, quantity)

                    break
                }
                if (reservation != null)
                    return Result.success(reservation)
                if (lastObservedTable as? HauteCuisineTable != null)
                    return Result.failure(TableAlreadyReserved())
                return Result.failure(NoRoomLeft())
            },
            { _ -> Result.failure(InvalidQuantityForReservation()) })
    }
}
