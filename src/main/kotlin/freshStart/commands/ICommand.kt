package freshStart.commands

import freshStart.SharedTable

interface ICommand {
    fun isValidReservation(sharedTable: SharedTable): Boolean
}
