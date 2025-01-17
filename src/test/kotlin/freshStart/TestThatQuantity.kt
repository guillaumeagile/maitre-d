package freshStart

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class TestThatQuantity : StringSpec({

    "Should not have  a quantity  equal to 0" {
        val quantity = Quantity.create(0)
        quantity.isFailure shouldBe true
    }

    "Should have a quantity  equal to 4" {
        var expected = 4
        val quantity = Quantity.create(expected)
        quantity.isSuccess shouldBe true
        quantity.getOrNull() shouldBe 4
    }

    "Should  not be compared to other types" {
        var expected = 4
        val quantity = Quantity.create(expected)
        quantity.isSuccess shouldBe true
        quantity.getOrNull() shouldNotBe "4"
        quantity.getOrNull() shouldNotBe 4.0
    }

    "Should  have  a quantity 2  equal to a quantity 2" {
        var expected = 2
        val quantity = Quantity.create(expected)
        quantity.getOrNull() shouldBe Quantity(2)
    }

    "Should  have  a quantity 2 not equal to a quantity 3" {
        var expected = 2
        val quantity = Quantity.create(expected)
        quantity.getOrNull() shouldNotBe Quantity(3)
    }

    "should be able to add a quantity to another" {
        val quantity1 = Quantity.create(1).getOrThrow()
        val quantity2 = Quantity.create(2).getOrThrow()
        val expected = Quantity.create(3).getOrThrow()

        quantity1.plus(quantity2) shouldBe expected
        quantity1  + quantity2 shouldBe expected
    }

    "should be compared one to a greater quantity"  {
        val quantity1 = Quantity.create(1).getOrThrow()
        val quantity2 = Quantity.create(2).getOrThrow()

        (quantity2 > quantity1) shouldBe true
        (quantity1 < quantity2) shouldBe true
        (quantity1 == quantity2) shouldBe false
    }

})