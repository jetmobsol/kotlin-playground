package inc.evil.playground

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SomeServiceSimpleTest {

    private lateinit var service: SomeService

    @BeforeEach
    fun setUp() {
        service = SomeService(anotherService = AnotherService())
    }

    @Test
    fun performAction() {
        Assertions.assertThat(service.performAction()).isEqualTo("foo")
    }
}