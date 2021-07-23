package freshStart

interface ITable {
    fun isAlreadyReserved(): Boolean
    fun reserve()
    val size: Int
}
