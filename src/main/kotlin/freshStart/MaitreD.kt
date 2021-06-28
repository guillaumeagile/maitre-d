package freshStart

import java.time.LocalDate

class MaitreD(val sizeTable: Int) {
    fun reserve(date: LocalDate, numberOfGuests: Int): Result<Reservation> {
        val resultQuantity = Quantity.create(numberOfGuests)
        return resultQuantity.fold({ quantity ->
            val reservation = Reservation(date, quantity)
            Result.success(reservation)
        },
            { _ -> Result.failure(InvalidQuantityForReservation()) })
    }
}
