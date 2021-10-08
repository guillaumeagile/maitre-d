package freshStart

import freshStart.commands.ConfigureCommand
import freshStart.commands.ReservationCommand
import freshStart.events.ReservationIsDeclinedOnSharedTable
import freshStart.events.ReservationIsProposedOnSharedTable
import io.kotlintest.matchers.collections.shouldContain
import io.kotlintest.specs.StringSpec
import java.time.LocalDate
import java.time.Month

class TestThatMaitreD2worksWithEvents : StringSpec({

    "une commande de réservation est acceptée " {
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        val maitreD2 = MaitreD2()

        val command = ReservationCommand(guestsCount = 5, wishedDate = date1)

        //ACT
        maitreD2.handle(command)

        //ASSERT
        maitreD2.events shouldContain ReservationIsProposedOnSharedTable(1)
    }

    "une commande de réservation  est refusée car un nombre de convives supérieur à la capacité" {
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)

        //option1
        //val maitreD2 = MaitreD2(capacitySharedTable = 3)

        //option2
        //val maitreD2 = MaitreD2(SharedTable(3, null))

        //option 3
        val maitreD2 = MaitreD2()
        maitreD2.handle( ConfigureCommand( "Add",  "SharedTable", "3"   ) )
        // ecrire test pour verifier ce handle de ConfigureCommand

        val command = ReservationCommand(guestsCount = 15, wishedDate = date1)

        //ACT
        maitreD2.handle(command)

        //ASSERT
        maitreD2.events shouldContain ReservationIsDeclinedOnSharedTable(1)
    }

})