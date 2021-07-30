package freshStart

import java.time.LocalDate

data class SharedTable(override val size: Int) : ITable {
    override fun isFull(): Boolean {
        return size <= 0
    }

    override fun reserve(): ITable {
        return this
    }

    fun reserve(date1: LocalDate, qtte: Quantity): SharedTable {
        if (qtte.value_ > size)
            return this
        return SharedTable(size - qtte.value_)
    }
}

