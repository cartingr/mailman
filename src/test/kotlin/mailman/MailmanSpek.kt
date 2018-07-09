package mailman

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.greaterThan
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
object MailmanSpek : Spek({
    describe("Just making sure I've got 'spek' set up correctly") {
        on("Checking that I can make a spek test") {
            assertThat(3, greaterThan(2))
        }
    }
})