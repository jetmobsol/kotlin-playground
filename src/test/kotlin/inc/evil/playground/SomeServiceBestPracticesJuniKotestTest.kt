package inc.evil.playground

import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance
import java.time.Instant
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SomeServiceBestPracticesJuniKotestTest {

    private val anotherService = mockk<AnotherService>() //Create Mocks Once
    private val service = SomeService(anotherService)

    @BeforeEach
    fun setUp() {
        clearMocks(anotherService) //very important!
        every { anotherService.performOtherAction() } returns "foo"
    }

    @Nested
    inner class PerformAction {
        @Test
        fun `returns foo`() {
            service.performAction() shouldBe "foo"

            verify(exactly = 1) { anotherService.performOtherAction() }
        }
    }

    @Nested
    inner class GetSomeData {
        @Test
        internal fun `should return person`() {
            service.getSomeData() shouldBe createPerson()
        }

        @Test
        internal fun `when list should return persons`() {
            service.getSomeListData() shouldContain Person("Mike", 26)
        }
    }

    @Nested
    inner class PerformComplexAction {
        @Test
        fun `with no param returns foo`() {
            service.performComplexAction() shouldBe "foo"

            verify(atLeast = 1, atMost = 1) { anotherService.performOtherAction() }
        }

        @Test
        fun `with param returns foo concat times`() {
            service.performComplexAction(1) shouldBe "foofoo"

            verify(atLeast = 1, atMost = 1) { anotherService.performOtherAction() }
        }
    }

    //helper functions with def arguments

    private fun createPerson(name: String = "Mike", age: Int = 26): Person = Person(name, age)
    private fun Int.toInstant(): Instant = Instant.ofEpochSecond(this.toLong())
    private fun Int.toUUID(): UUID = UUID.fromString("00000000-0000-0000-a000-${this.toString().padStart(11, '0')}")


}