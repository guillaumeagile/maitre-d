package freshStart

import arrow.core.Some
import arrow.core.getOrElse
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.Month

class TestThatDailySeats : StringSpec({


    "howManyReservedOnBis at date should be egal to quantity reservation" {
        val dailySeats = DailySeats()
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)

        val sut = dailySeats.addReservation(date = date1, seats = Quantity(value = 3), idCustomer = "1")

        sut.howManyReservedOn(date = date1) shouldBe 3
    }

    "howManyReservedOnBis at date should sum of all quantities reservation" {
        val dailySeats = DailySeats()
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)

        val sut = dailySeats.addReservation(date = date1, seats = Quantity(value = 3), idCustomer = "1")
        val sut2 = sut.addReservation(date = date1, seats = Quantity(value = 2), idCustomer = "2")

        sut2.howManyReservedOn(date = date1) shouldBe 3 + 2
    }

    "An empty DailySeats return zero for any date" {
        val sut = DailySeats()
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        sut.howManyReservedOn(date = date1) shouldBe 0
    }

    // TODO: poser des tests sur le Remove !!!!!!!!!

    "removeReservationOnDailySeats " {
        val dailySeats = DailySeats()

        val reservationDate = LocalDate.of(1990, Month.DECEMBER, 30)
        dailySeats.addReservation(
            date = reservationDate,
            seats = Quantity(2),
            idCustomer = "1"
        )

        dailySeats.removeReservation(idCustomer = "1", reservationDate = reservationDate)

        dailySeats.howManyReservedOn(reservationDate) shouldBe Quantity(0)
    }

    "x".config(enabled = false) {
        val dailySeats = DailySeats()

        val reservationDate = LocalDate.of(1990, Month.DECEMBER, 30)
        val dailySeats1 = dailySeats.addReservation(
            date = reservationDate,
            seats = Quantity(2),
            idCustomer = "1"
        )
        val sut = dailySeats1.addReservation(
            date = reservationDate,
            seats = Quantity(4),
            idCustomer = "42"
        )
        val reservationDate2 = LocalDate.of(2010, Month.DECEMBER, 11)
        val sut2 = sut.addReservation(
            date = reservationDate2,
            seats = Quantity(3),
            idCustomer = "1"
        )
        sut2.howManyReservedOn(reservationDate) shouldBe Quantity(6)
        sut2.howManyReservedOn(reservationDate2) shouldBe Quantity(3)

        val sut3 = sut2.removeReservation(idCustomer = "1", reservationDate = reservationDate )

        sut3.howManyReservedOn(reservationDate) shouldBe Quantity(4)
        sut3.howManyReservedOn(reservationDate2) shouldBe Quantity(3)
    }

    "lookup for reservation witg date and customerId" {
        val dailySeats = DailySeats()

        val reservationDate = LocalDate.of(1990, Month.DECEMBER, 30)
        val dailySeats1 = dailySeats.addReservation(
            date = reservationDate,
            seats = Quantity(2),
            idCustomer = "1"
        )
        // TODO: remettre cette donnée, et que le test passe
     /*   val sut = dailySeats1.addReservation(
            date = reservationDate,
            seats = Quantity(4),
            idCustomer = "42"
        )*/
        val reservationDate2 = LocalDate.of(2010, Month.DECEMBER, 11)
        val sut2 = dailySeats1.addReservation(
            date = reservationDate2,
            seats = Quantity(3),
            idCustomer = "1"
        )
//        sut2.lookupReservationsAtDateForCustomer("1", reservationDate).toPair() shouldBe Some(reservationDate to listOf("1" to Quantity(2) ) )
        sut2.lookupReservationsAtDateForCustomer2("1", reservationDate)?.toPair() shouldBe (reservationDate to listOf("1" to Quantity(2) ))
        /*   problem: data class diff for arrow.core.Some
Expected :Option.Some((1990-12-30, [(1, Quantity(value=2))]))
Actual   :Option.Some(1990-12-30=[(1, Quantity(value=2))]) */

         val actual = sut2.lookupReservationsAtDateForCustomer("1", reservationDate)
        actual.isEmpty() shouldBe false
        actual.getOrElse { null }!!.key shouldBe reservationDate
        actual.getOrElse { null }!!.value shouldBe listOf("1" to Quantity(2))

    }


//    "test entry vs key". {
//        val m = Entry ("a", 1)
//
//    }

    "removeInvalidReservationNumber" {
        val dailySeats = DailySeats()

        val reservationDate = LocalDate.of(1990, Month.DECEMBER, 30)
        val dailySeats1 = dailySeats.addReservation(
            date = reservationDate,
            seats = Quantity(2),
            idCustomer = "1"
        )

        val sut = dailySeats1.removeReservation(idCustomer = "666", reservationDate = reservationDate)

        dailySeats1.howManyReservedOn(reservationDate) shouldBe Quantity(2)
        sut.howManyReservedOn(reservationDate) shouldBe Quantity(2)
    }


    "updateReservationQuantity ".config(enabled = false) {
        val dailySeats = DailySeats()
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        val date2 = LocalDate.of(1990, Month.DECEMBER, 30)

        var sut = dailySeats.addReservation(date = date1, seats = Quantity(value = 3), idCustomer = "1")
        sut = sut.addReservation(date = date2, seats = Quantity(value = 1), idCustomer = "1")
        sut.updateReservationQuantity(searchedIdCustomer = "1", searchedDate = date1, seats = Quantity(value = 2))

        sut.howManyReservedOn(date = date2) shouldBe Quantity(value = 1)
        sut.howManyReservedOn(date = date1) shouldBe Quantity(value = 2)

    }



    "ok" {
        val dailySeats = DailySeats()
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)

        val sut = dailySeats.addReservation(date = date1, seats = Quantity(2), idCustomer = "4")

        sut.howManyReservedOn(date = date1) shouldBe 2
    }

})
