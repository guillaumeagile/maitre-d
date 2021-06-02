package freshStart

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import java.util.*

class TestThatQuantity : StringSpec({

    "Should not have  a quantity  equal to 0" {
        val quantity = Quantity.create(0, 0)
        quantity.isFailure shouldBe true
    }

    "Should have a quantity  equal to 4" {
        var expected = 4
        val quantity = Quantity.create(expected, 5)
        quantity.isSuccess shouldBe true
        quantity.getOrNull() shouldBe 4
    }

    "Should  not be compared to other types" {
        var expected = 4
        val quantity = Quantity.create(expected, 5)
        quantity.isSuccess shouldBe true
        quantity.getOrNull() shouldNotBe "4"
        quantity.getOrNull() shouldNotBe 4.0
    }

    "Should  have  a quantity 2  equal to a quantity 2" {
        var expected = 2
        val quantity = Quantity.create(expected, 2)
        quantity.getOrNull() shouldBe Quantity(2)
    }

    "Should  have  a quantity 2 not equal to a quantity 3" {
        var expected = 2
        val quantity = Quantity.create(expected, 3)
        quantity.getOrNull() shouldNotBe Quantity(3)
    }

})