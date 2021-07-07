package freshStart

import io.kotlintest.matchers.beInstanceOf
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.time.LocalDate
import java.time.Month


class TestThatReservation : StringSpec({

    "Should have a date and a quantity" {
        val maitreD = MaitreD(  arrayOf(1))

        val actualReservation = maitreD.reserve(LocalDate.of(1990, Month.DECEMBER, 15), 1).getOrThrow()

        actualReservation.date shouldBe LocalDate.of(1990, Month.DECEMBER, 15)
        actualReservation.quantity shouldBe 1
    }

    "Should not have  a quantity  equal to 0" {
        val maitreD = MaitreD(arrayOf( 1))

        val actualReservation = maitreD.reserve(LocalDate.of(1990, Month.DECEMBER, 15), 0)

        actualReservation.isFailure shouldBe true
        actualReservation.onFailure { e -> e should beInstanceOf<InvalidQuantityForReservation>() }
    }

    "Should have not a quantity more than the table size (12)" {
        val maitreD = MaitreD(arrayOf(12))

        val actualReservation = maitreD.reserve(LocalDate.of(1990, Month.DECEMBER, 15), 13)

        actualReservation.isFailure shouldBe true
        actualReservation.onFailure { e -> e should beInstanceOf<NoRoomLeft>() }
    }

    "Should have not a negative quantity " {
        val maitreD = MaitreD(arrayOf(-1))

        val actualReservation = maitreD.reserve(LocalDate.of(1990, Month.DECEMBER, 15), -2)

        actualReservation.isFailure shouldBe true
        actualReservation.onFailure { e -> e should beInstanceOf<InvalidQuantityForReservation>() }
    }

    "Should have not a quantity more than the table capacity (4) the same day" {
        val maitreD = MaitreD(arrayOf(4))

        maitreD.reserve(LocalDate.of(1990, Month.DECEMBER, 15), 3)
        val actualReservation = maitreD.reserve(LocalDate.of(1990, Month.DECEMBER, 15), 2)

        actualReservation.isFailure shouldBe true
        actualReservation.onFailure { e -> e should beInstanceOf<NoRoomLeft>() }
    }

    "Should have  a quantity enough for the same day" {
        val maitreD = MaitreD(arrayOf(10))

        maitreD.reserve(LocalDate.of(1990, Month.DECEMBER, 15), 3)
        val actualReservation = maitreD.reserve(LocalDate.of(1990, Month.DECEMBER, 15), 7)

        actualReservation.isSuccess shouldBe true
        actualReservation.getOrThrow().quantity shouldBe 7
    }

    "Should have a quantity enough for two different day" {
        val maitreD = MaitreD(arrayOf(4))
        val aDay = LocalDate.of(1990, Month.DECEMBER, 15)

        maitreD.reserve(aDay, 2)
        val actualReservation = maitreD.reserve(aDay.plusDays(1), 3)

        actualReservation.isSuccess shouldBe true
        actualReservation.getOrThrow().quantity shouldBe 3
    }

    "Should reserve again after a failing reservation "{
        val maitreD = MaitreD(arrayOf(3))
        val aDay = LocalDate.of(1990, Month.DECEMBER, 15)
        val firstReservation = maitreD.reserve(aDay, 4)

        val actualReservation = maitreD.reserve(aDay, 1)

        actualReservation.isSuccess shouldBe true
        actualReservation.getOrThrow().quantity shouldBe 1
    }

    "Should have a quantity not enough for the same day when multiple reservation already accepted" {
        val maitreD = MaitreD(arrayOf(10))
        val aDay = LocalDate.of(1990, Month.DECEMBER, 15)

        maitreD.reserve(aDay, 3)
        maitreD.reserve(aDay, 2)
        maitreD.reserve(aDay, 3)
        val actualReservation = maitreD.reserve(aDay, 3)

        actualReservation.isFailure shouldBe true
        actualReservation.onFailure { e -> e should beInstanceOf<NoRoomLeft>() }
    }

    "Should  accept reservation for different table size (each MaitreD own its table)" {
        val maitreD = MaitreD(arrayOf(10))
        val maitreD2 = MaitreD(arrayOf(3))
        val aDay = LocalDate.of(1990, Month.DECEMBER, 15)

        val actualReservation = maitreD.reserve(aDay, 10)
        actualReservation.isSuccess shouldBe true

        val actualReservation2 = maitreD2.reserve(aDay, 3)
        actualReservation2.isSuccess shouldBe true
    }
})