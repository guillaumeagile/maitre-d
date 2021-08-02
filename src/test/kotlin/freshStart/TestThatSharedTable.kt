package freshStart

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import java.time.LocalDate
import java.time.Month

class TestThatSharedTable : StringSpec({

    "should be equal when size is equal" {
        val tableB: ITable = SharedTable(1)
        val tableA: ITable = SharedTable(1)
        tableA shouldBe tableB
    }

    "should be equal to itself" {
        val tableA: ITable = SharedTable(1)
        tableA shouldBe tableA
    }

    "should be not equal when size is equal" {
        val tableB: ITable = SharedTable(0)
        val tableA: ITable = SharedTable(1)
        tableA shouldNotBe tableB
    }

    "should be equal to undefined" {
        val tableB: ITable = SharedTable(0)
        val tableA: ITable = InvalidTable()
        tableA shouldBe tableB
    }

    "should reserve a table"{
        val table1 = SharedTable(2)
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        val result = table1.reserve(date1, Quantity(1))
        result.isFull(date1) shouldBe false
    }


    "should reserve a table at max capacity"{
        val table1 = SharedTable(2)
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        val result = table1.reserve(date1, Quantity(2))
        result.isFull(date1) shouldBe true

    }

    "a reservation of a table over capacity should not modify the state of the table"{
        val table1 = SharedTable(1)
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        val result = table1.reserve(date1, Quantity(2))
        result.isFull(date1) shouldBe false
    }

    "should reserve a table at different days"{
        val table1 = SharedTable(2)
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        val date2 = LocalDate.of(1990, Month.DECEMBER, 1)
        val table2 = table1.reserve(date1, Quantity(2))
        table2.isFull(date1) shouldBe true
        table2.isFull(date2) shouldBe false
        table2.canIReserve(date2, Quantity( 2)) shouldBe true

        val resultTable = table2.reserve(date2, Quantity(2))
        resultTable.isFull(date2) shouldBe true
    }

})
