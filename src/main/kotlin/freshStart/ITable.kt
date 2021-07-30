package freshStart

interface ITable {
    fun isFull(): Boolean
    fun reserve(): ITable
    val size: Int
}
