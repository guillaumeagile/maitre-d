package freshStart

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.time.LocalDate
import java.time.Month


class TestThatReservation : StringSpec({

    "Should have a date and a quantity" {
        val reservation = Reservation(LocalDate.of(1990, Month.DECEMBER, 15), 1)
        reservation.Date shouldBe LocalDate.of(1990, Month.DECEMBER, 15)
        reservation.Quantity shouldBe 1
    }

    "Should not have  a quantity  equal to 0" {
        val resultat =  create (LocalDate.of(1990, Month.DECEMBER, 15), 0)

        resultat.isFailure shouldBe true
    }
})