package freshStart

import java.time.LocalDate


class Reservation(val date: LocalDate, val quantity: Quantity) {
    companion object {
        var accumulation: Int = 0
        fun create(date: LocalDate, quantity: Int, table: Table): Result<Reservation> {
            val qtt = Quantity.create(quantity)
            return qtt.fold({ q ->
                if (q.value_ + accumulation > table.size )
                    return Result.failure(NoRoomLeft())
                accumulation += q.value_
                return Result.success(Reservation(date, q))
            },
                { _ -> Result.failure(InvalidQuantityForReservation()) }
            )
        }
    }
}
