package freshStart

import java.time.LocalDate

class MaitreD(val sizeTable: Int) {
    var dailySeatsOverallReservations = DailySeats()

    fun reserve(date: LocalDate, numberOfGuests: Int): Result<Reservation> {
        val resultQuantity = Quantity.create(numberOfGuests)

        return resultQuantity.fold(
            { quantity ->
                val reservedSeats = dailySeatsOverallReservations.howManyReservedOn(date) + quantity.value_
                if (reservedSeats > sizeTable)
                    return Result.failure(NoRoomLeft())
                dailySeatsOverallReservations.reserve(date, quantity.value_)
                val reservation = Reservation(date, quantity)

                Result.success(reservation)
            },
            { _ -> Result.failure(InvalidQuantityForReservation()) })
    }
}
