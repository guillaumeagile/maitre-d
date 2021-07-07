package freshStart

import java.time.LocalDate

class MaitreD(val sizeTable: Array<Int>) {
    var dailySeatsOverallReservations = DailySeats()

    fun reserve(date: LocalDate, numberOfGuests: Int): Result<Reservation> {
        val resultQuantity = Quantity.create(numberOfGuests)

        return resultQuantity.fold(
            { quantity ->
                val reservedSeats = dailySeatsOverallReservations.howManyReservedOn2(date).plus( quantity).value_
                if (reservedSeats > sizeTable.first())
                    return Result.failure(NoRoomLeft())
                dailySeatsOverallReservations.reserve(date, quantity.value_)  //TODO: utiliser QUantity (primitive obsession)
                val reservation = Reservation(date, quantity)

                Result.success(reservation)
            },
            { _ -> Result.failure(InvalidQuantityForReservation()) })
    }
}
