package freshStart

import java.time.LocalDate

class MaitreD(val tables: MutableList<ITable>) {
    var dailySeatsOverallReservations = DailySeats()

    fun reserve(date: LocalDate, numberOfGuests: Int): Result<Reservation> {
        val resultQuantity = Quantity.create(numberOfGuests)
        var lastReservedTable : ITable? = null;
        return resultQuantity.fold(
            { quantity ->
                val quantityOfReservedSeat =
                    dailySeatsOverallReservations.howManyReservedOn(date) + quantity   //TODO: ne plus utiliser
                var reservation: Reservation? = null
                for ((index, currentTable) in tables.withIndex()) {
                    if (!currentTable.canIReserve(date, quantity))
                        continue
//                    if (quantityOfReservedSeat > Quantity(currentTable.size))
//                        return Result.failure(NoRoomLeft())

                    dailySeatsOverallReservations =
                        dailySeatsOverallReservations.addReservation(date, quantity)   //TODO: ne plus utiliser
                    reservation = Reservation(date, quantity)   //TODO: ne plus utiliser
                    tables[index] = currentTable.reserve(date, quantity)
                    lastReservedTable = currentTable
                    break
                }
                if (reservation != null)
                    return Result.success(reservation)
                if (lastReservedTable as? SharedTable != null)
                    return Result.failure(NoRoomLeft())
                return Result.failure(TableAlreadyReserved())

            },
            { _ -> Result.failure(InvalidQuantityForReservation()) })
    }
}
