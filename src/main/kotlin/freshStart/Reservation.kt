package freshStart

import java.time.LocalDate

fun create( date: LocalDate,  quantity: Int): Result<Reservation>
{
    return Result.success(Reservation(date, quantity))
}

 class Reservation(val Date: LocalDate, val Quantity: Int) {


}
