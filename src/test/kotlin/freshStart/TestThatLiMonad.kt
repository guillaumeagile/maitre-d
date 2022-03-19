package freshStart

import arrow.core.Option
import arrow.core.none
import freshStart.monads.Li
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class TestThatLiMonad : StringSpec({

    "Should return none if list is empty" {
        val input: List<Int> = emptyList()
        val sut =  Li<List<Int>>(input)
        val actual =  sut.flatMap()
        actual shouldBe none()

    }
})