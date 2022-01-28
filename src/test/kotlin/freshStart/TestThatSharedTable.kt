package freshStart

import arrow.core.Some
import arrow.core.none
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
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


    "should not be equal when reservation have different dates" {
        val dateB = LocalDate.of(1990, Month.DECEMBER, 31)
        val tableB: ITable = SharedTable(size=6, dailySeatsOverallReservations = DailySeats())
        val sutB = tableB.reserve(date=dateB, qtte = Quantity(2))

        val dateA = LocalDate.of(1970, Month.MARCH, 2)
        val tableA: ITable = SharedTable(size = 6, dailySeatsOverallReservations = DailySeats())
        val sutA = tableA.reserve(date=dateA, qtte = Quantity(2))

        sutA shouldNotBe sutB
    }

    "should reserve a table" {
        val table1 = SharedTable(2)
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        val result = table1.reserve(date1, Quantity(1))
        result.isFull(date1) shouldBe false
    }

    "should reserve a table at max capacity" {
        val table1 = SharedTable(2)
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        val result = table1.reserve(date1, Quantity(2))
        result.isFull(date1) shouldBe true
    }

    "a reservation of a table over capacity should not modify the state of the table" {
        val table1 = SharedTable(1)
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        val result = table1.reserve(date1, Quantity(2))
        result.isFull(date1) shouldBe false
    }

    "should reserve a shared table at different days" {
        val table1 = SharedTable(2)
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        val date2 = LocalDate.of(1990, Month.DECEMBER, 1)
        val table2 = table1.reserve(date1, Quantity(2))
        table2.isFull(date1) shouldBe true
        table2.isFull(date2) shouldBe false
        table2.canIReserve(date2, Quantity(2)) shouldBe true

        val resultTable = table2.reserve(date2, Quantity(2))
        resultTable.isFull(date2) shouldBe true
    }

    "should reserve a shared table same day, multi reservation" {
        val table1 = SharedTable(2)
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)

        val table2 = table1.reserve(date1, Quantity(1))
        table2.isFull(date1) shouldBe false

        table2.canIReserve(date1, Quantity(1)) shouldBe true

        val resultTable = table2.reserve(date1, Quantity(1))
        resultTable.isFull(date1) shouldBe true

        resultTable.canIReserve(date1, Quantity(1)) shouldBe false
    }

    "should be able to get an empty result on lookup for inexisting reservation " {
        val table1 = SharedTable(2)
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        table1.lookupReservation(date= date1, idCustomer= "1") shouldBe none()
    }

    "should be able to lookup after a reservation for a customerId at a given date"  {
        val table1 = SharedTable(2)
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        val result = table1.reserve(date1, Quantity(1), idCustomer= "1") as SharedTable

        result.lookupReservation(date= date1, idCustomer= "1") shouldBe Some( Quantity( 1))
    }

    "should return none when looking up after a reservation for an inexistant idCustomer"  {
        val table1 = SharedTable(2)
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        val result = table1.reserve(date1, Quantity(1), idCustomer= "1") as SharedTable

        result.lookupReservation(date= date1, idCustomer= "2") shouldBe none()
    }

})
