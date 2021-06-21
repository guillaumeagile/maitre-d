package freshStart

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec

class TestThatTable : StringSpec({

    "should be equal when size is equal" {
        val tableB : ITable = Table(1)
        val tableA : ITable = Table(1)
        tableA shouldBe tableB
    }

    "should be equal to itself" {
        val tableA : ITable = Table(1)
        tableA shouldBe tableA
    }

    "should be not equal when size is equal" {
        val tableB : ITable = Table(0)
        val tableA : ITable = Table(1)
        tableA shouldNotBe  tableB
    }

    "should be equal to undefined" {
        val tableB  : ITable = Table(0)
        val tableA  : ITable = UndefinedTable()
        tableA shouldBe  tableB
    }
})
