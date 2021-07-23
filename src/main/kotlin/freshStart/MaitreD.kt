package freshStart

import java.time.LocalDate

class MaitreD( val tables: Set<ITable>) {
    var dailySeatsOverallReservations = DailySeats()

    fun reserve(date: LocalDate, numberOfGuests: Int): Result<Reservation> {
        val resultQuantity = Quantity.create(numberOfGuests)

        return resultQuantity.fold(
            { quantity ->
                val quantityOfReservedSeat  =  dailySeatsOverallReservations.howManyReservedOn(date) +  quantity
                val sizeFirstTable = Quantity(tables.first().size)
                if (tables.first().isAlreadyReserved())
                    return Result.failure(TableAlreadyReserved())
                if (quantityOfReservedSeat > sizeFirstTable )
                    return Result.failure(NoRoomLeft())
                dailySeatsOverallReservations.reserve(date, quantity)
                val reservation = Reservation(date, quantity)
                tables.first().reserve()
                Result.success(reservation)
            },
            { _ -> Result.failure(InvalidQuantityForReservation()) })
    }
}
