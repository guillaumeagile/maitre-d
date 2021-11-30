package freshStart.commands

import java.time.LocalDate

data class ReservationCommand(  val guestsCount: Int, val wishedDate: LocalDate) : ICommand {
    override fun isValidReservation(leftFreeSeatOnTable: Int): Boolean {
        return guestsCount <= leftFreeSeatOnTable
    }

}
