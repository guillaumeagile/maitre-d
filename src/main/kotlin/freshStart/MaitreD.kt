package freshStart

import freshStart.errors.InvalidQuantityForReservation
import freshStart.errors.NoRoomLeft
import freshStart.errors.TableAlreadyReserved
import freshStart.tables.HauteCuisineTable
import java.time.LocalDate

class MaitreD(val tables: MutableList<ITable>) {

    fun reserve(date: LocalDate, numberOfGuests: Int): Result<Reservation> {
        val resultQuantity = Quantity.create(numberOfGuests)
        var lastObservedTable: ITable? = null
        return resultQuantity.fold(
            { quantity ->
                var reservation: Reservation? = null
                for ((index, currentTable) in tables.withIndex()) {
                    lastObservedTable = currentTable
                    if (!currentTable.canIReserve(date, quantity))
                        continue
                    reservation = Reservation(date, quantity) // A FAIRE: ne plus utiliser ?
                    tables[index] = currentTable.reserve(date, quantity)
                    break
                }
                if (reservation != null)
                    return Result.success(reservation)
                if (lastObservedTable as? HauteCuisineTable != null)
                    return Result.failure(TableAlreadyReserved())
                return Result.failure(NoRoomLeft())
            },
            { _ -> Result.failure(InvalidQuantityForReservation()) }
        )
    }
}
