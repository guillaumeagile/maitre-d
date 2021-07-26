package freshStart

class HauteCuisineTable(override val size: Int , val isReserved : Boolean = false) : ITable {

    companion object{
        fun createTableReservee(size: Int): HauteCuisineTable = HauteCuisineTable(size, true)
    }

    override fun isAlreadyReserved(): Boolean {
        return isReserved
    }

    override fun reserve(): ITable {
        return createTableReservee(this.size)
    }
}