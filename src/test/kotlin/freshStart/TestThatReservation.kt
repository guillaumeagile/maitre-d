package freshStart

import io.kotlintest.matchers.beInstanceOf
import io.kotlintest.should
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
        val resultat = Reservation.create(LocalDate.of(1990, Month.DECEMBER, 15), 0, Table(1))
        resultat.isFailure shouldBe true
    }

    "Should have not a quantity more than the table size (12)" {
        val resultat = Reservation.create(LocalDate.of(1990, Month.DECEMBER, 15), 13, Table(12))
        resultat.isFailure shouldBe true
        resultat.onFailure { e -> e is NoRoomLeft }
    }

    "Should have not a quantity more than the table size (4)" {
        val resultat = Reservation.create(LocalDate.of(1990, Month.DECEMBER, 15), 5, Table(4))
        resultat.isFailure shouldBe true
        resultat.onFailure { e -> e is NoRoomLeft }
    }

    "Should have not a negative quantity " {
        val resultat = Reservation.create(LocalDate.of(1990, Month.DECEMBER, 15), -2, Table(-1))
        resultat.isFailure shouldBe true
        resultat.onFailure { e -> e is InvalidQuantityForReservation }
    }

    "Should have not a quantity more than the table capacity (4) the same day" {
        val table = Table(4)
        Reservation.create(LocalDate.of(1990, Month.DECEMBER, 15), 3, table )
        val resultat = Reservation.create(LocalDate.of(1990, Month.DECEMBER, 15), 2, table)
        resultat.isFailure shouldBe true
        resultat.onFailure { e -> e is NoRoomLeft }
    }

    "Should have  a quantity enough for the same day" {
        val table = Table(10)
        Reservation.create(LocalDate.of(1990, Month.DECEMBER, 15), 3, table )
        val resultat = Reservation.create(LocalDate.of(1990, Month.DECEMBER, 15), 3, table)
        resultat.isFailure shouldBe false
        resultat.getOrNull()?.quantity shouldBe 3
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

    "Should not accept reservation if the last table size is changing" {
        Reservation.create(LocalDate.of(1990, Month.DECEMBER, 15), 1, Table(10) )
        val resultat = Reservation.create(LocalDate.of(1990, Month.DECEMBER, 15), 3, Table(4))
        resultat.isFailure shouldBe true
        resultat.onFailure { e ->  e should beInstanceOf<CannotChangeTableSize>()  }
    }

    "new design JP" {
        val result =  Reservation.create(LocalDate.of(1990, Month.DECEMBER, 15), 5, Table(10) )
//        val result2 = Reservation.create(LocalDate.of(1990, Month.DECEMBER, 15), 1, result.table )

    }
/*
    "new design Anth"{
        val table : Table(10)
        val result = table.reserve(LocalDate.of(1990, Month.DECEMBER, 15), 5)
    }
*/
})