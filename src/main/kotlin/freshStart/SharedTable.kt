package freshStart

data class SharedTable(override val size: Int) : ITable {
    override fun isAlreadyReserved(): Boolean {
        return false
    }

    override fun reserve() {
    }
}

data class HauteCuisineTable(override val size: Int) : ITable {
    var _isAlreadyReserved = false  //TODO:  rendre immutable
    override fun isAlreadyReserved(): Boolean {
        return _isAlreadyReserved
    }

    override fun reserve() {
        _isAlreadyReserved = true
    }
}


class UndefinedTable(override val size: Int = 0) : ITable {
    override fun equals(other: Any?): Boolean {
        return true
    }

    override fun isAlreadyReserved(): Boolean {
        return true
    }

    override fun reserve() {
        TODO("Not yet implemented")
    }
}