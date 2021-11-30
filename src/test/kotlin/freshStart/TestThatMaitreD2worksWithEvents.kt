package freshStart

import freshStart.commands.ReservationCommand
import freshStart.events.ReservationIsDeclinedOnSharedTable
import freshStart.events.ReservationIsProposedOnSharedTable
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
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
        maitreD2.events shouldContain ReservationIsProposedOnSharedTable(1)
    }
/*   A REPRENDRE APRES AVOIR FAIT LES TESTS DE LA COMMANDE 'CONFIGURATION' */
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
        maitreD2.events shouldContain ReservationIsProposedOnSharedTable(1)
    }
})
