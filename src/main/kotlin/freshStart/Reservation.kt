package freshStart

import java.time.LocalDate



class Reservation(val date: LocalDate, val quantity: Quantity) {
    companion object {
        fun create(date: LocalDate, quantity: Int): Result<Reservation> {
            val qtt = Quantity.create(quantity)

            return qtt.fold({ q -> Result.success(Reservation(date, q)) },
                { _ -> Result.failure(InvalidQuantityForReservation()) }
            )
        }
    }
}
