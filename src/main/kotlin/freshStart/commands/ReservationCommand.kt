package freshStart.commands

import freshStart.Quantity
import freshStart.SharedTable
import java.time.LocalDate
import java.time.Month

data class ReservationCommand(val guestsCount: Int, val wishedDate: LocalDate) : ICommand {



    override fun isValidReservation(sharedTable: SharedTable): Boolean {
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        return sharedTable.canIReserve(date1, Quantity(guestsCount))
    }
}
