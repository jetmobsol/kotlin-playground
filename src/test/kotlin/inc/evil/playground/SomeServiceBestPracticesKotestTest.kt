package inc.evil.playground

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.mockk.*
import java.time.Instant
import java.util.*

class SomeServiceBestPracticesKotestTest : ShouldSpec({

    val anotherService = mockk<AnotherService>() //Create Mocks Once
    val service = SomeService(anotherService)

    fun createPerson(name: String = "Mike", age: Int = 26): Person = Person(name, age)
    fun Int.toInstant(): Instant = Instant.ofEpochSecond(this.toLong())
    fun Int.toUUID(): UUID = UUID.fromString("00000000-0000-0000-a000-${this.toString().padStart(11, '0')}")

    beforeTest(){
        clearMocks(anotherService) //very important!
        every { anotherService.performOtherAction() } returns "foo"
    }

    context("performAction") {
        should("return foo") {
            service.performAction() shouldBe "foo"

            verify(exactly = 1) { anotherService.performOtherAction() }
        }
    }

    context("performComplexAction") {
        should("return foo when no param") {
            service.performComplexAction() shouldBe "foo"

            verify(exactly = 1) { anotherService.performOtherAction() }
        }

        should("return foo concat times when param") {
            service.performComplexAction(1) shouldBe "foofoo"

            verify(exactly = 1) { anotherService.performOtherAction() }
        }
    }

    context("getSomeData") {
        should("return person") {
            service.getSomeData() shouldBe createPerson()
        }
    }

    context("getSomeListData"){
        should("return persons") {
            service.getSomeListData() shouldContain createPerson()
        }
    }

})