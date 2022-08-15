package inc.evil.playground

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.time.Instant
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension::class)
internal class SomeServiceBestPracticesJunitTest {

    private lateinit var anotherService: AnotherService //Create Mocks Once
    private lateinit var service: SomeService

    @BeforeEach
    fun setUp() {
        anotherService = mock(AnotherService::class.java)
        service  = SomeService(anotherService)
    }

    @Nested
    inner class PerformAction {
        @BeforeEach
        internal fun setUp() {
            `when`(anotherService.performOtherAction()).thenReturn("foo")
        }

        @Test
        fun `returns foo`() {
            Assertions.assertThat(service.performAction()).isEqualTo("foo")

            verify(anotherService).performOtherAction()
        }
    }

    @Nested
    inner class GetSomeData {
        @Test
        internal fun `should return person`() {
            Assertions.assertThat(service.getSomeData()).isEqualTo(createPerson())
        }

        @Test
        internal fun `when list should return persons`() {
            Assertions.assertThat(service.getSomeListData()).containsExactly(Person("Mike", 26))
        }
    }

    @Nested
    inner class PerformComplexAction {
        @BeforeEach
        internal fun setUp() {
            `when`(anotherService.performOtherAction()).thenReturn("foo")
        }

        @Test
        fun `with no param returns foo`() {
            Assertions.assertThat(service.performComplexAction()).isEqualTo("foo")

            verify(anotherService).performOtherAction()
        }

        @Test
        fun `with param returns foo concat times`() {
            Assertions.assertThat(service.performComplexAction(1)).isEqualTo("foofoo")

            verify(anotherService).performOtherAction()
        }
    }

    //helper functions with def arguments

    private fun createPerson(name: String = "Mike", age: Int = 26): Person = Person(name, age)
    private fun Int.toInstant(): Instant = Instant.ofEpochSecond(this.toLong())
    private fun Int.toUUID(): UUID = UUID.fromString("00000000-0000-0000-a000-${this.toString().padStart(11, '0')}")


}