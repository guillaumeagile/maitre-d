package freshStart.commands

import freshStart.SharedTable

data class ConfigureCommand(val action: String, val tableType: String, val size: String) : ICommand {

    override fun isValidReservation(sharedTable: SharedTable): Boolean {
        TODO("Not yet implemented")
    }
}
