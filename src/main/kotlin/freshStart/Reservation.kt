package freshStart

import java.time.LocalDate



class Reservation(val date: LocalDate, val quantity: Quantity) {
    companion object {
        fun create(date: LocalDate, quantity: Int, table: Table): Result<Reservation> {
            val qtt = Quantity.create(quantity)
            return qtt.fold({q ->
                                TODO("enlever ce if (essayer un 2e fold)")
                                if (q.value_ > table.size)
                                    return Result.failure(NoRoomLeft())
                                return Result.success(Reservation(date, q))
                            },
                { _ -> Result.failure(InvalidQuantityForReservation()) }
            )
        }
    }
}
