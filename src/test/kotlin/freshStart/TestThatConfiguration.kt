package freshStart

import freshStart.commands.ConfigureCommand
import io.kotest.core.script.test
import io.kotest.core.spec.style.DescribeSpec

class TestThatConfiguration : DescribeSpec({
    xcontext("ecrire des tests pour verifier le handle de ConfigureCommand") {
        test("this test is disabled") {
            val maitreD2 = MaitreD2(SharedTable(3, DailySeats()))
            maitreD2.handle(ConfigureCommand("Add", "SharedTable", "3"))
            // 
        }
    }
})
