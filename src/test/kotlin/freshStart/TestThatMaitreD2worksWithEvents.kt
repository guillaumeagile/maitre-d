package freshStart

import freshStart.commands.ReservationCommand
import freshStart.events.ReservationIsConfirmedOnSharedTable
import freshStart.events.ReservationIsDeclinedOnSharedTable
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.Month

class TestThatMaitreD2worksWithEvents : StringSpec({

    "une commande de réservation est acceptée " {
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        val maitreD2 = MaitreD2(SharedTable(3, DailySeats()))

        val command = ReservationCommand(guestsCount = 2, wishedDate = date1)

        // ACT
        maitreD2.handle(command)

        // ASSERT
        maitreD2.events shouldContain ReservationIsConfirmedOnSharedTable(1, date = date1, qtte = Quantity(2))
    }

    "une commande de réservation  est refusée car le nombre de convives demandé est supérieur à la capacité" {
        // ARRANGE 
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)

        val maitreD2 = MaitreD2(SharedTable(3, DailySeats()))

        val command = ReservationCommand(guestsCount = 15, wishedDate = date1)

        // ACT
        maitreD2.handle(command)

        // ASSERT
        maitreD2.events shouldContain ReservationIsDeclinedOnSharedTable(1)
    }

    "should be able to reserve a table with maximum available seats" {
        // ARRANGE
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        val maitreD2 = MaitreD2(SharedTable(3, DailySeats()))
        val command = ReservationCommand(guestsCount = 3, wishedDate = date1)
        // ACT
        maitreD2.handle(command)
        // ASSERT
        maitreD2.events shouldContain ReservationIsConfirmedOnSharedTable(1, date = date1, qtte = Quantity(3))
    }

    "should not be able to reserve a table mutliple times same date, if the size of the table is reached"
        .config(enabled = false) { // TODO: pour fonctionner, les commandes doivent donner un numéro de commande différent, et il doit être pris en compte dans 'handle'
            // ARRANGE
            val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
            val maitreD2 = MaitreD2(SharedTable(3, DailySeats()))
            val command = ReservationCommand(guestsCount = 2, wishedDate = date1)
            val command2 = ReservationCommand(guestsCount = 3, wishedDate = date1)
            maitreD2.handle(command)
            // ACT
            maitreD2.handle(command2)
            // ASSERT
            maitreD2.events.last() shouldBe ReservationIsDeclinedOnSharedTable(1)
        }

    "should be able to reserve a table mutliple times same date, if the size of the table is ok" {
        // ARRANGE
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        val maitreD2 = MaitreD2(SharedTable(3, DailySeats()))
        val command = ReservationCommand(guestsCount = 2, wishedDate = date1)
        val command2 = ReservationCommand(guestsCount = 1, wishedDate = date1)
        maitreD2.handle(command)
        // ACT
        maitreD2.handle(command2)
        // ASSERT
        maitreD2.events.last() shouldBe ReservationIsConfirmedOnSharedTable(
            reservationNumber = 1,
            date = date1,
            qtte = Quantity(1)
        )
    }

    "should be able to reserve a table mutliple times at different dates, if the size of the table is ok" {
        // ARRANGE
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        val date2 = LocalDate.of(1990, Month.DECEMBER, 30)
        val maitreD2 = MaitreD2(SharedTable(3, DailySeats()))
        val command = ReservationCommand(guestsCount = 3, wishedDate = date1)
        val command2 = ReservationCommand(guestsCount = 3, wishedDate = date2)
        maitreD2.handle(command)
        maitreD2.events.last() shouldBe ReservationIsConfirmedOnSharedTable(
            reservationNumber = 1,
            date = date1,
            qtte = Quantity(3)
        )
        // ACT
        maitreD2.handle(command2)
        // ASSERT
        maitreD2.events.last() shouldBe ReservationIsConfirmedOnSharedTable(
            reservationNumber = 1,
            date = date2,
            qtte = Quantity(3)
        )
    }

    "after one command reservation the event ReservationIsConfirmedOnSharedTable is present for the right customer"{
            // ARRANGE
            val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
            val maitreD2 = MaitreD2(SharedTable(7, DailySeats()))
            val command = ReservationCommand(guestsCount = 1, wishedDate = date1, customerName = "John")

            //ACT
            maitreD2.handle(command)

            //ASSERT
            maitreD2.events.last() shouldBe ReservationIsConfirmedOnSharedTable(
            customerName = "John",
            date = date1,
            qtte = Quantity(3)
        )
        }



})
