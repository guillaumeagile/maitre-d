package freshStart

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.LocalDate
import java.time.Month

class TestThatDailySeats : StringSpec({



    "howManyReservedOnBis at date should be egal to quantity reservation" {
        val dailySeats = DailySeats()
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)

        val sut = dailySeats.addReservation(date = date1, seats = Quantity(value = 3), reservationNumber = 1)

        sut.howManyReservedOnBis(date = date1) shouldBe 3
    }

    "howManyReservedOnBis at date should sum of all quantities reservation" {
        val dailySeats = DailySeats()
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)

        val sut = dailySeats.addReservation(date = date1, seats = Quantity(value = 3), reservationNumber = 1)
        val sut2 = sut.addReservation(date = date1, seats = Quantity(value = 2), reservationNumber = 2)

        sut2.howManyReservedOnBis(date = date1) shouldBe 3 + 2
    }

    "An empty DailySeats return zero for any date" {
        val sut = DailySeats()
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)


        sut.howManyReservedOnBis(date = date1) shouldBe 0
    }

})
