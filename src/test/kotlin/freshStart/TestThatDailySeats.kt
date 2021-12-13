package freshStart

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.LocalDate
import java.time.Month

class TestThatDailySeats : StringSpec({



    "rename me new one" {
        val dailySeats = DailySeats()
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)

        val sut = dailySeats.addReservation(date = date1, seats = Quantity(value = 3), reservationNumber = 1)

        sut.howManyReservedOnBis(date = date1) shouldBe 3
    }

    "fix me" {
        val sut = DailySeats()
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)


        sut.howManyReservedOnBis(date = date1) shouldBe 0
    }

})
