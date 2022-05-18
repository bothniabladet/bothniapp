package se.ltu.student

import io.ktor.client.request.*
import io.ktor.server.testing.*
import se.ltu.student.plugins.configureRouting
import kotlin.test.Test

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
        }
    }
}