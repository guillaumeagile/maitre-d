package freshStart

import freshStart.commands.ConfigureCommand
import freshStart.events.Event
import io.kotest.core.script.test
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class TestThatReplayOnSharedTable : StringSpec({

    "rename me!!!" {
        val events: Collection<Event> = setOf<Event>()
        val actual = SharedTable.replayOn(listEvents = events, initialSize = 4)
        val expected = SharedTable(size = 4, dailySeatsOverallReservations = DailySeats())
        actual shouldBe expected
    }
})