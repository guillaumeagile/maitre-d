package freshStart

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

        dailySeats.removeReservation(idCustomer = "1")

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
        sut.howManyReservedOn(reservationDate) shouldBe Quantity(6)

        val sut2 = sut.removeReservation(idCustomer = "1")


        sut2.howManyReservedOn(reservationDate) shouldBe Quantity(4)
    }


    "removeInvalidReservationNumber" {
        val dailySeats = DailySeats()

        val reservationDate = LocalDate.of(1990, Month.DECEMBER, 30)
        val dailySeats1 = dailySeats.addReservation(
            date = reservationDate,
            seats = Quantity(2),
            idCustomer = "1"
        )

        val sut = dailySeats1.removeReservation(idCustomer = "666")

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
