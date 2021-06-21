package freshStart

data class Table(val size: Int) : ITable {}

class UndefinedTable() : ITable {
    override fun equals(other: Any?): Boolean {
        return true
    }

}