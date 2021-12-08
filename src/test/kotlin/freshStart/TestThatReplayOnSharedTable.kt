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
        val events: Collection<Event> = setOf<Event>()
        val sut = SharedTable(size = 4)

        val actual = sut.replayOn(listEvents = events)

        val expected = SharedTable(size = 4, dailySeatsOverallReservations = DailySeats())
        actual shouldBe expected
    }

    "with ReservationIsConfirmed event should change the SharedTable" {
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        val events: Collection<Event> = setOf<Event>().plus(
            ReservationIsConfirmedOnSharedTable(
                reservationNumber = 1,
                date = date1,
                qtte = Quantity(1)
            )
        )

        val sut = SharedTable(size = 4)

        val actual = sut.replayOn(listEvents = events)
        val expected = SharedTable(
            size = 4,
            dailySeatsOverallReservations = DailySeats().addReservation(date = date1, seats = Quantity(1))
        )
        actual shouldBe expected
    }

    "with 2 ReservationIsConfirmed event should change the SharedTable" {
        // Arrange
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        val date2 = LocalDate.of(1990, Month.DECEMBER, 2)

        val events = setOf(
            ReservationIsConfirmedOnSharedTable(
                reservationNumber = 1,
                date = date1,
                qtte = Quantity(1)
            ),
            ReservationIsConfirmedOnSharedTable(
                reservationNumber = 1,
                date = date2,
                qtte = Quantity(3)
            )
        )
        val sut = SharedTable(size = 4)

        // Act
        val actual = sut.replayOn(listEvents = events)

        // Assert
        val expectedTotalDailySeats = DailySeats()
            .addReservation(date = date1, seats = Quantity(1))
            .addReservation(date = date2, seats = Quantity(3))
        val expected = SharedTable(
            size = 4,
            dailySeatsOverallReservations = expectedTotalDailySeats
        )
        actual shouldBe expected
    }

    "event reservationIsDeclinedOnSharedTable should be ignore by replay" {
        // Arrange
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)

        val events = setOf(
            ReservationIsConfirmedOnSharedTable(
                reservationNumber = 1,
                date = date1,
                qtte = Quantity(2)
            ),
            ReservationIsDeclinedOnSharedTable(
                reservationNumber = 99
            )
        )
        val sut = SharedTable(size = 4)

        // Act
        val actual = sut.replayOn(listEvents = events)

        // Assert
        val expectedTotalDailySeats = DailySeats().addReservation(date = date1, seats = Quantity(2))
        val expected = SharedTable(
            size = 4,
            dailySeatsOverallReservations = expectedTotalDailySeats
        )
        actual shouldBe expected
    }

    "new event cancel" {
        // Arrange
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)

        val events = setOf(
            ReservationIsConfirmedOnSharedTable(
                reservationNumber = 1,
                date = date1,
                qtte = Quantity(2)
            ),
            ReservationIsCancelOnSharedTable(
                reservationNumber = 1
            )
        )
        val sut = SharedTable(size = 4)

        // Act
        val actual = sut.replayOn(listEvents = events)

        // Assert
        val expected = SharedTable(
            size = 4,
            dailySeatsOverallReservations = DailySeats()
        )
        actual shouldBe expected
    }

})


