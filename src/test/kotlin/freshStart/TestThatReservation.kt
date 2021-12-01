package freshStart

import freshStart.errors.InvalidQuantityForReservation
import freshStart.errors.NoRoomLeft
import freshStart.tables.HauteCuisineTable
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.beInstanceOf
import java.time.LocalDate
import java.time.Month

// import io.kotest.matchers.result

class TestThatReservation : StringSpec({

    fun buildMaitreDWithSharedTable(uniqueTableSize: Int) = MaitreD(mutableListOf(SharedTable(uniqueTableSize)))
    fun buildMaitreDWithOneHauteCuisineTable(uniqueTableSize: Int) =
        MaitreD(mutableListOf(HauteCuisineTable(uniqueTableSize)))

    fun buildMaitreDWithTwoHauteCuisineTable(uniqueTableSize: Int) =
        MaitreD(mutableListOf(HauteCuisineTable(uniqueTableSize), HauteCuisineTable(uniqueTableSize)))

    "Should have a date and a quantity" {
        val maitreD = buildMaitreDWithSharedTable(1)

        val actualReservation = maitreD.reserve(LocalDate.of(1990, Month.DECEMBER, 15), 1).getOrThrow()

        actualReservation.date shouldBe LocalDate.of(1990, Month.DECEMBER, 15)
        actualReservation.quantity shouldBe 1
    }

    "Should not have  a quantity  equal to 0" {
        val maitreD = buildMaitreDWithSharedTable(1)

        val actualReservation = maitreD.reserve(LocalDate.of(1990, Month.DECEMBER, 15), 0)

        actualReservation.isFailure shouldBe true
        actualReservation.onFailure { e -> e should beInstanceOf<InvalidQuantityForReservation>() }
    }

    "Should have not a quantity more than the table size (12)" {
        val maitreD = buildMaitreDWithSharedTable(12)

        val actualReservation = maitreD.reserve(LocalDate.of(1990, Month.DECEMBER, 15), 13)

        actualReservation.isFailure shouldBe true
        // actualReservation.onFailure { e -> e should beInstanceOf<NoRoomLeft>() }
    }

    "Should have not a negative quantity " {
        val maitreD = buildMaitreDWithSharedTable(-1)

        val actualReservation = maitreD.reserve(LocalDate.of(1990, Month.DECEMBER, 15), -2)

        actualReservation.isFailure shouldBe true
        actualReservation.onFailure { e -> e should beInstanceOf<InvalidQuantityForReservation>() }
    }

    "Should have not a quantity more than the table capacity (4) the same day" {
        val maitreD = buildMaitreDWithSharedTable(4)

        maitreD.reserve(LocalDate.of(1990, Month.DECEMBER, 15), 3)
        val actualReservation = maitreD.reserve(LocalDate.of(1990, Month.DECEMBER, 15), 2)

        actualReservation.isFailure shouldBe true
        actualReservation.onFailure { e -> e should beInstanceOf<NoRoomLeft>() }
        // actualReservation.onFailure { e -> e should beInstanceOf<TableAlreadyReserved>() }
    }

    "Should have  a quantity enough for the same day" {
        val maitreD = buildMaitreDWithSharedTable(10)

        maitreD.reserve(LocalDate.of(1990, Month.DECEMBER, 15), 3)
        val actualReservation = maitreD.reserve(LocalDate.of(1990, Month.DECEMBER, 15), 7)

        actualReservation.isSuccess shouldBe true
        actualReservation.getOrThrow().quantity shouldBe 7
    }

    "Should have a quantity enough for two different day" {
        val maitreD = buildMaitreDWithSharedTable(4)
        val aDay = LocalDate.of(1990, Month.DECEMBER, 15)

        maitreD.reserve(aDay, 2)
        val actualReservation = maitreD.reserve(aDay.plusDays(1), 3)

        actualReservation.isSuccess shouldBe true
        actualReservation.getOrThrow().quantity shouldBe 3
    }

    "Should reserve again after a failing reservation " {
        val maitreD = buildMaitreDWithSharedTable(3)
        val aDay = LocalDate.of(1990, Month.DECEMBER, 15)
        maitreD.reserve(aDay, 4)

        val actualReservation = maitreD.reserve(aDay, 1)

        actualReservation.isSuccess shouldBe true
        actualReservation.getOrThrow().quantity shouldBe 1
    }

    "Should have a quantity not enough for the same day when multiple reservation already accepted" {
        val maitreD = buildMaitreDWithSharedTable(10)
        val aDay = LocalDate.of(1990, Month.DECEMBER, 15)

        maitreD.reserve(aDay, 3)
        maitreD.reserve(aDay, 2)
        maitreD.reserve(aDay, 3)
        val actualReservation = maitreD.reserve(aDay, 3)

        actualReservation.isFailure shouldBe true
        actualReservation.onFailure { e -> e should beInstanceOf<NoRoomLeft>() }
        // actualReservation.onFailure { e -> e should beInstanceOf<TableAlreadyReserved>() }
    }

    "Should  accept reservation for different table size (each MaitreD own its table)" {
        val maitreD = buildMaitreDWithSharedTable(10)
        val maitreD2 = buildMaitreDWithSharedTable(3)
        val aDay = LocalDate.of(1990, Month.DECEMBER, 15)

        val actualReservation = maitreD.reserve(aDay, 10)
        actualReservation.isSuccess shouldBe true

        val actualReservation2 = maitreD2.reserve(aDay, 3)
        actualReservation2.isSuccess shouldBe true
    }

    "Should refuse reservation on a HauteCuisine table already reserved" {
        val maitreD = buildMaitreDWithOneHauteCuisineTable(2)
        val aDay = LocalDate.of(1990, Month.DECEMBER, 15)

        val firstReservation = maitreD.reserve(aDay, 1)
        val secondReservation = maitreD.reserve(aDay, 1)

        firstReservation.isSuccess shouldBe true
        secondReservation.isFailure shouldBe true
    }

    /*
    "should accept 2 reservations successively because we have 2 tables"{
        val maitreD = buildMaitreDWithTwoHauteCuisineTable(2)
        val aDay = LocalDate.of(1990, Month.DECEMBER, 15)

        val firstReservation = maitreD.reserve(aDay, 2)
        val secondReservation = maitreD.reserve(aDay, 2)

        firstReservation.isSuccess shouldBe true
        secondReservation.isSuccess shouldBe true
    }*/

    "should accept 2 x 1 reservation successively because we have 2 x 2 tables" {
        val maitreD = buildMaitreDWithTwoHauteCuisineTable(2)

        val aDay = LocalDate.of(1990, Month.DECEMBER, 15)

        val firstReservation = maitreD.reserve(aDay, 1)
        val secondReservation = maitreD.reserve(aDay, 1)

        firstReservation.isSuccess shouldBe true
        secondReservation.isSuccess shouldBe true
    }

/*    "Should  accept 2 reservations of 2 and occupied any table of size2" {
        val maitreD = MaitreD(arrayOf(2, 2))
        val aDay = LocalDate.of(1990, Month.DECEMBER, 15)

        val firstReservation = maitreD.reserve(aDay, 2)
        val secondReservation = maitreD.reserve(aDay, 2)

        firstReservation.isSuccess shouldBe true
        secondReservation.isSuccess shouldBe true
    }*/
})
