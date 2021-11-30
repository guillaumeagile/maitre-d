package freshStart.commands

interface ICommand {
    fun isValidReservation(leftFreeSeatOnTable: Int): Boolean

}
