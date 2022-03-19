package freshStart

import arrow.core.none
import freshStart.monads.Panaché
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class TestThatLiMonad : StringSpec({

    "Should return none if list is empty" {
        val input: List<Int> = emptyList()
        val sut =  Panaché<List<Int>>(input)
        val actual =  sut.flatMap()
        actual shouldBe none()

    }
})