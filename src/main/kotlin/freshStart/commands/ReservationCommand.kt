package freshStart.commands

import freshStart.Quantity
import freshStart.SharedTable
import java.time.LocalDate

data class ReservationCommand(val guestsCount: Int, val wishedDate: LocalDate, val customerName: String = "") : ICommand {

    override fun isValidReservation(sharedTable: SharedTable): Boolean {
        return (sharedTable.canIReserve(wishedDate, Quantity(guestsCount)))
    }
}
