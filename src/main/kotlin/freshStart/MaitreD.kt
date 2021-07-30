package freshStart

import java.time.LocalDate

class MaitreD( val tables: MutableList<ITable>) {
    var dailySeatsOverallReservations = DailySeats()

    fun reserve(date: LocalDate, numberOfGuests: Int): Result<Reservation> {
        val resultQuantity = Quantity.create(numberOfGuests)

        return resultQuantity.fold(
            { quantity ->
                val quantityOfReservedSeat  =  dailySeatsOverallReservations.howManyReservedOn(date) +  quantity
                var reservation : Reservation? = null
                for ((index, currentTable) in tables.withIndex())
                {
                    if (currentTable.isFull())
                        continue
                    if (quantityOfReservedSeat > Quantity(currentTable.size) )
                        return Result.failure(NoRoomLeft())

                    dailySeatsOverallReservations.reserve(date, quantity)
                    reservation = Reservation(date, quantity)

                    tables[index] =  currentTable.reserve()
                    break
                }

                if (reservation != null)
                    return Result.success(reservation)
               return Result.failure(TableAlreadyReserved())

            },
            { _ -> Result.failure(InvalidQuantityForReservation()) })
    }
}
