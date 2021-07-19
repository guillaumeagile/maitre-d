package freshStart

import java.time.LocalDate

class MaitreD(val sizeOfTables: Array<Int>) {
    var dailySeatsOverallReservations = DailySeats()

    fun reserve(date: LocalDate, numberOfGuests: Int): Result<Reservation> {
        val resultQuantity = Quantity.create(numberOfGuests)

        return resultQuantity.fold(
            { quantity ->
                val quantityOfReservedSeat  =  dailySeatsOverallReservations.howManyReservedOn(date) +  quantity
                val sizeFirstTable = Quantity(sizeOfTables.first())
                if (quantityOfReservedSeat > sizeFirstTable )
                    return Result.failure(NoRoomLeft())
                dailySeatsOverallReservations.reserve(date, quantity)
                val reservation = Reservation(date, quantity)

                Result.success(reservation)
            },
            { _ -> Result.failure(InvalidQuantityForReservation()) })
    }
}
