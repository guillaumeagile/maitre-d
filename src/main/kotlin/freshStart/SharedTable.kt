package freshStart

data class SharedTable(override val size: Int) : ITable {
    override fun isAlreadyReserved(): Boolean {
        return false
    }

    override fun reserve(): ITable {
        return this
    }
}


class UndefinedTable(override val size: Int = 0) : ITable {
    override fun equals(other: Any?): Boolean {
        return true
    }

    override fun isAlreadyReserved(): Boolean {
        return true
    }

    override fun reserve(): ITable {
        TODO("Not yet implemented")
    }
}