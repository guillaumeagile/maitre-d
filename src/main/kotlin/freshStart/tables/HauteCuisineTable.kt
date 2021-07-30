package freshStart.tables

import freshStart.ITable

class HauteCuisineTable(override val size: Int , val isReserved : Boolean = false) : ITable {

    companion object{
        fun createTableReservee(size: Int): HauteCuisineTable = HauteCuisineTable(size, true)
    }

    override fun isFull(): Boolean {
        return isReserved
    }

    override fun reserve(): ITable {
        return createTableReservee(this.size)
    }
}