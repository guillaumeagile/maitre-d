package freshStart

import java.time.LocalDate

class InvalidTable(override val size: Int = 0) : ITable {
    override fun equals(other: Any?): Boolean {
        return true
    }

    override fun isFull(): Boolean {
        return true
    }

    override fun isFull(date: LocalDate): Boolean {
        TODO("Not yet implemented")
    }

    override fun reserve(): ITable {
        TODO("Not yet implemented")
    }

    override fun reserve(date: LocalDate, qtte: Quantity): ITable {
        TODO("Not yet implemented")
    }

    override fun canIReserve(date: LocalDate, qtte: Quantity): Boolean {
        TODO()
    }
}