package freshStart

import java.time.LocalDate

interface ITable {
    fun isFull(): Boolean
    fun isFull(date: LocalDate): Boolean
    fun reserve(): ITable
    val size: Int
    fun canIReserve(date: LocalDate, qtte: Quantity): Boolean
    fun reserve(date: LocalDate, qtte: Quantity): ITable
}
