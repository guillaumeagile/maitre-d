package freshStart

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec


class DumbTest : StringSpec({

    "fetchUser should return name and id" {
        val user = fetchUser("sam")
        user.name shouldBe "sam"
        user.id shouldBe "123"
    }
})

fun fetchUser(s: String): User {
    return User("sam", "123")
}

class User(name: String, id: String) {

    val name: String = name
    val id: String = id
}
