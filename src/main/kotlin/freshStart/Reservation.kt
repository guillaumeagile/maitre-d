package freshStart

import java.time.LocalDate

fun create( date: LocalDate,  quantity: Int): Result<Reservation>
{
    if (quantity<=0)
        return Result.failure(InvalidQuantityForReservation())
    return Result.success(Reservation(date, quantity))
}

 class Reservation(val Date: LocalDate, val Quantity: Int) {


}
