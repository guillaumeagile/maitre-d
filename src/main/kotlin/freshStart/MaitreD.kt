package freshStart

import java.time.LocalDate
import java.time.Month

class MaitreD(val sizeTable: Int) {
    fun reserve(date: LocalDate, numberOfGuests: Int) : Result<Reservation> {
        val reservation = Reservation(date, Quantity(numberOfGuests))

        return Result.success(reservation)
    }

}
