package freshStart

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec

class TestThatSharedTable : StringSpec({

    "should be equal when size is equal" {
        val tableB : ITable = SharedTable(1)
        val tableA : ITable = SharedTable(1)
        tableA shouldBe tableB
    }

    "should be equal to itself" {
        val tableA : ITable = SharedTable(1)
        tableA shouldBe tableA
    }

    "should be not equal when size is equal" {
        val tableB : ITable = SharedTable(0)
        val tableA : ITable = SharedTable(1)
        tableA shouldNotBe  tableB
    }

    "should be equal to undefined" {
        val tableB  : ITable = SharedTable(0)
        val tableA  : ITable = UndefinedTable()
        tableA shouldBe  tableB
    }
})
