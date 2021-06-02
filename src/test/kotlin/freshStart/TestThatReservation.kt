package freshStart

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.time.LocalDate
import java.time.Month


class TestThatReservation : StringSpec({

    "Should have a date and a quantity" {
        val reservation = Reservation.create(LocalDate.of(1990, Month.DECEMBER, 15), 1, Table(1)).getOrNull()
        reservation?.date shouldBe LocalDate.of(1990, Month.DECEMBER, 15)
        reservation?.quantity shouldBe 1
    }

    "Should not have  a quantity  equal to 0" {
        val resultat =  Reservation.create (LocalDate.of(1990, Month.DECEMBER, 15), 0, Table(1))
        resultat.isFailure shouldBe true
    }

    "Should have not a quantity more than the table capacity (12)" {
        val resultat =  Reservation.create (LocalDate.of(1990, Month.DECEMBER, 15), 13, Table(12)        )
        resultat.isFailure shouldBe true
        resultat.onFailure { e -> e is NoRoomLeft }
    }

    "Should have not a quantity more than the table capacity (4)" {
        val resultat =  Reservation.create (LocalDate.of(1990, Month.DECEMBER, 15), 5, Table(4)        )
        resultat.isFailure shouldBe true
        resultat.onFailure { e -> e is NoRoomLeft }
    }

    "Should have not a negative quantity " {
        val resultat =  Reservation.create (LocalDate.of(1990, Month.DECEMBER, 15), -2, Table(-1)        )
        resultat.isFailure shouldBe true
        resultat.onFailure { e -> e is InvalidQuantityForReservation }
    }

})