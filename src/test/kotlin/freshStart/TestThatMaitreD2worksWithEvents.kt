package freshStart

import freshStart.commands.ReservationCommand
import freshStart.events.ReservationIsProposedOnSharedTable
import io.kotlintest.matchers.collections.shouldContain
import io.kotlintest.specs.StringSpec
import java.time.LocalDate
import java.time.Month

class TestThatMaitreD2worksWithEvents : StringSpec({

    "une commande de réservation " {
        val date1 = LocalDate.of(1990, Month.DECEMBER, 31)
        val maitreD2 = MaitreD2()

        val command = ReservationCommand(guestsCount = 5, wishedDate = date1)

        //ACT
        maitreD2.handle(command)

        //ASSERT
        maitreD2.events shouldContain ReservationIsProposedOnSharedTable(1)
    }

})