package freshStart

import io.kotlintest.matchers.beInstanceOf
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.time.LocalDate
import java.time.Month


class TestThatReservation : StringSpec({


    "Should have a date and a quantity" {
        val maitreD = MaitreD( 1)
        val actualReservation = maitreD.reserve(LocalDate.of(1990, Month.DECEMBER, 15), 1).getOrThrow()
        actualReservation.date shouldBe LocalDate.of(1990, Month.DECEMBER, 15)
        actualReservation.quantity shouldBe 1
    }

    "Should not have  a quantity  equal to 0" {
        val maitreD = MaitreD( 1)
        val actualReservation = maitreD.reserve(LocalDate.of(1990, Month.DECEMBER, 15), 0)
        actualReservation.isFailure shouldBe true
        actualReservation.onFailure { e ->  e should beInstanceOf<InvalidQuantityForReservation>() }
    }

    "Should have not a quantity more than the table size (12)" {
        val maitreD = MaitreD( 12)
        val actualReservation = maitreD.reserve(LocalDate.of(1990, Month.DECEMBER, 15), 13)
        actualReservation.isFailure shouldBe true
        actualReservation.onFailure { e ->  e should beInstanceOf<NoRoomLeft>() }
    }

    "Should have not a negative quantity " {
        val maitreD = MaitreD( -1)
        val actualReservation = maitreD.reserve(LocalDate.of(1990, Month.DECEMBER, 15), -2)
        actualReservation.isFailure shouldBe true
        actualReservation.onFailure { e ->  e should beInstanceOf<InvalidQuantityForReservation>() }
    }

    "Should have not a quantity more than the table capacity (4) the same day" {
        val maitreD = MaitreD( 4)
        maitreD.reserve(LocalDate.of(1990, Month.DECEMBER, 15), 3)
        val actualReservation = maitreD.reserve(LocalDate.of(1990, Month.DECEMBER, 15), 2)
        actualReservation.isFailure shouldBe true
        actualReservation.onFailure { e ->  e should beInstanceOf<NoRoomLeft>() }
    }

    "Should have  a quantity enough for the same day" {
        val maitreD = MaitreD( 10)
        maitreD.reserve(LocalDate.of(1990, Month.DECEMBER, 15), 3)
        val actualReservation = maitreD.reserve(LocalDate.of(1990, Month.DECEMBER, 15), 7)
        actualReservation.isFailure shouldBe false
        actualReservation.getOrThrow().quantity shouldBe 7
    }

    "Should have a quantity enough for two different day" {
        val table = Table(4)
        Reservation.create(LocalDate.of(1990, Month.DECEMBER, 15), 2, table )
        val resultat = Reservation.create(LocalDate.of(1990, Month.DECEMBER, 14), 3, table)
        resultat.isFailure shouldBe false
        resultat.getOrNull()?.quantity shouldBe 3
    }


    "Should have a quantity not enough for the same day when multiple reservation already accepter" {
        val table = Table(10)
        Reservation.create(LocalDate.of(1990, Month.DECEMBER, 15), 3, table )
        Reservation.create(LocalDate.of(1990, Month.DECEMBER, 15), 2, table )
        Reservation.create(LocalDate.of(1990, Month.DECEMBER, 15), 3, table )
        val resultat = Reservation.create(LocalDate.of(1990, Month.DECEMBER, 15), 3, table)
        resultat.isFailure shouldBe true
        resultat.onFailure { e -> e should beInstanceOf<NoRoomLeft>() }
    }
/*
    "Should not accept reservation if the last table size is changing" {
        Reservation.create(LocalDate.of(1990, Month.DECEMBER, 15), 1, Table(10) )
        val resultat = Reservation.create(LocalDate.of(1990, Month.DECEMBER, 15), 3, Table(4))
        resultat.isFailure shouldBe true
        resultat.onFailure { e ->  e should beInstanceOf<CannotChangeTableSize>()  }
    }
*/

})