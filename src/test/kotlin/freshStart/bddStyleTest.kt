package freshStart

import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec

class MyTests : BehaviorSpec({
    given("a broomstick") {
        `when`("I sit on it") {
            then("I should be able to fly") {
                // test code
                "".length shouldBe 0
            }
        }
        `when`("I throw it away") {
            then("it should come back") {
                // test code
            }
        }
    }
})