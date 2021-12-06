package freshStart

import freshStart.events.Event
import freshStart.events.ReservationIsConfirmedOnSharedTable
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.Month

class TestThatReplayOnSharedTable : StringSpec({

    "with an empty list should let's unchanged the SharedTable" {
        val events: Collection<Event> = setOf<Event>()
        var sut = SharedTable(size = 4)

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

        var sut = SharedTable(size = 4)

        val actual = sut.replayOn(listEvents = events)
        val expected = SharedTable(
            size = 4,
            dailySeatsOverallReservations = DailySeats().addReservation(date = date1, seats = Quantity(1))
        )
        actual.dailySeatsOverallReservations.dailyAccumulation.size shouldBe expected.dailySeatsOverallReservations.dailyAccumulation.size
    }

    "with 2  ReservationIsConfirmed event should change the SharedTable" {
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        val date2 = LocalDate.of(1990, Month.DECEMBER, 2)
        var events: Collection<Event> = setOf<Event>().plus(
            ReservationIsConfirmedOnSharedTable(
                reservationNumber = 1,
                date = date1,
                qtte = Quantity(1)
            )
        )
        events = events.plus(
            ReservationIsConfirmedOnSharedTable(
                reservationNumber = 1,
                date = date2,
                qtte = Quantity(3)
            )
        )
        var sut = SharedTable(size = 4)
        val actual = sut.replayOn(listEvents = events)
        var expectedTotalDailySeats = DailySeats()
        expectedTotalDailySeats = expectedTotalDailySeats.addReservation(date = date1, seats = Quantity(1))
        expectedTotalDailySeats = expectedTotalDailySeats.addReservation(date = date2, seats = Quantity(3))
        val expected = SharedTable(
            size = 4,
            dailySeatsOverallReservations = expectedTotalDailySeats
        )


        actual.dailySeatsOverallReservations shouldBe expected.dailySeatsOverallReservations
    }
})
