package freshStart.commands

data class ConfigureCommand(val action: String, val tableType: String, val size: String) : ICommand
