package freshStart

class InvalidTable(override val size: Int = 0) : ITable {
    override fun equals(other: Any?): Boolean {
        return true
    }

    override fun isFull(): Boolean {
        return true
    }

    override fun reserve(): ITable {
        TODO("Not yet implemented")
    }
}