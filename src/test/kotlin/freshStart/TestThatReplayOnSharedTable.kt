package freshStart

import freshStart.events.Event
import freshStart.events.ReservationIsCancelOnSharedTable
import freshStart.events.ReservationIsConfirmedOnSharedTable
import freshStart.events.ReservationIsDeclinedOnSharedTable
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.Month

class TestThatReplayOnSharedTable : StringSpec({

    "with an empty list should let's unchanged the SharedTable" {
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        val events: Collection<Event> = setOf<Event>()
        val sut = SharedTable(size = 4)

        val actual = sut.replayOn(listEvents = events)

        val expected = SharedTable(size = 4, dailySeatsOverallReservations = DailySeats())
        actual shouldBe expected
        actual.dailySeatsOverallReservations.howManyReservedOn(date1) shouldBe 0
    }

    "with ReservationIsConfirmed event should change the SharedTable" {
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        val events: Collection<Event> = setOf<Event>().plus(
            ReservationIsConfirmedOnSharedTable(
                idCustomer = "1",
                date = date1,
                qtte = Quantity(1)
            )
        )

        val sut = SharedTable(size = 4)

        val actual = sut.replayOn(listEvents = events)
        actual.dailySeatsOverallReservations.dailyAccumulation.containsKey(date1) shouldBe true
        actual.dailySeatsOverallReservations.howManyReservedOn(date1) shouldBe Quantity(1)
    }

    "with 2 ReservationIsConfirmed event should change the SharedTable" {
        // Arrange
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        val date2 = LocalDate.of(1990, Month.DECEMBER, 2)

        val events = setOf(
            ReservationIsConfirmedOnSharedTable(
                idCustomer = "1",
                date = date1,
                qtte = Quantity(1)
            ),
            ReservationIsConfirmedOnSharedTable(
                idCustomer = "1",
                date = date2,
                qtte = Quantity(3)
            )
        )
        val sut = SharedTable(size = 4)

        // Act
        val actual = sut.replayOn(listEvents = events)

        // Assert
        actual.dailySeatsOverallReservations.dailyAccumulation.containsKey(date1) shouldBe true
        actual.dailySeatsOverallReservations.dailyAccumulation.containsKey(date2) shouldBe true
        actual.dailySeatsOverallReservations.dailyAccumulation[date1]!!.first().second shouldBe Quantity(1)
        actual.dailySeatsOverallReservations.dailyAccumulation[date2]!!.first().second shouldBe Quantity(3)
    }

    "event reservationIsDeclinedOnSharedTable should be ignore by replay" {
        // Arrange
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)

        val events = setOf(
            ReservationIsConfirmedOnSharedTable(
                idCustomer = "1",
                date = date1,
                qtte = Quantity(2)
            ),
            ReservationIsDeclinedOnSharedTable(
                idCustomer = 99
            )
        )
        val sut = SharedTable(size = 4)

        // Act
        val actual = sut.replayOn(listEvents = events)

        // Assert
        actual.dailySeatsOverallReservations.dailyAccumulation.containsKey(date1) shouldBe true
        actual.dailySeatsOverallReservations.dailyAccumulation[date1]!!.first().second shouldBe Quantity(value=2)
    }

    "new event cancel" {
        // Arrange
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)

        val events = setOf(
            ReservationIsConfirmedOnSharedTable(
                idCustomer = "1",
                date = date1,
                qtte = Quantity(2)
            ),
            ReservationIsCancelOnSharedTable(
                idCustomer = "1"
            )
        )
        val sut = SharedTable(size = 4)

        // Act
        val actual = sut.replayOn(listEvents = events)

        // Assert
        actual.dailySeatsOverallReservations.dailyAccumulation.containsKey(date1) shouldBe false
    }

    // TODO: écrire un test dans lequel on a 2 résa à la même date, et on ne veut en supprimer qu'une des 2 ;)
})
