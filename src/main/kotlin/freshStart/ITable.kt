package freshStart

interface ITable {
    fun isAlreadyReserved(): Boolean
    fun reserve(): ITable
    val size: Int
}
