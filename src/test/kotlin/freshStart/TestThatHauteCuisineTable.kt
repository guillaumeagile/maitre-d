package freshStart

import freshStart.tables.HauteCuisineTable
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.time.LocalDate
import java.time.Month

class TestThatHauteCuisineTable: StringSpec({

    "should be equal when size is equal" {
        val tableB: ITable = HauteCuisineTable(1)
        val tableA: ITable = HauteCuisineTable(1)
        tableA shouldBe tableB  //A CHANGER:  on doit distinguer des tables différentes (mais de mm taille) avec des ID
    }

    "Should refuse reservation on a already reserved table" {
        val tableA: ITable = HauteCuisineTable(20)
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)

        val resultTable =  tableA.reserve(date1, Quantity(1))
        resultTable.canIReserve(date1, Quantity(1)) shouldBe false
    }
})