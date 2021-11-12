package freshStart

import freshStart.tables.HauteCuisineTable
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

import java.time.LocalDate
import java.time.Month

class TestThatHauteCuisineTable: StringSpec({

    "should be not equal when size is equal" {
        val tableB: ITable = HauteCuisineTable(1)
        val tableA: ITable = HauteCuisineTable(1)
        tableA shouldNotBe tableB
    }

    "Should refuse reservation on a already reserved table" {
        val tableA  = HauteCuisineTable(20)
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)

        val sut =  tableA.reserve(date1, Quantity(1))
        sut.canIReserve(date1, Quantity(1)) shouldBe false
        sut.reserve(date1, Quantity(1)) shouldBe sut
    }

    "Should accept reservation if the table is not reserved" {
        val sut  = HauteCuisineTable(20)
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)

        sut.canIReserve(date1, Quantity(1)) shouldBe true
    }

    "Should accept reservation if the table is not reserved at antoher date (after the 1st reservation)" {
        val tableA  = HauteCuisineTable(20)
        val date1 = LocalDate.of(1990, Month.DECEMBER, 30)
        val date2 = LocalDate.of(1990, Month.DECEMBER, 31)

        val sut =  tableA.reserve(date1, Quantity(1))
        sut.canIReserve(date2, Quantity(1)) shouldBe true
    }

    "Should refuse reservation if the table is big enough for the quantity" {
        val tableA  = HauteCuisineTable(20)
        val date1 = LocalDate.of(1990, Month.DECEMBER, 30)

        tableA.canIReserve(date1, Quantity(21)) shouldBe false
        val sut =  tableA.reserve(date1, Quantity(21))
        sut shouldBe tableA
    }
})