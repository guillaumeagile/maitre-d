package freshStart

import io.kotest.core.script.test
import io.kotest.core.spec.style.DescribeSpec

class TestThatConfiguration : DescribeSpec({
    context("this outer block is enabled") {
        test("this test is disabled") {
            // test here
        }
    }
    xcontext("this block is disabled") {
        test("disabled by inheritance from the parent") {
            // test here
        }
    }
})
