package xyz.konyahin

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.http.content.staticResources
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import xyz.konyahin.model.GreetingRequest
import xyz.konyahin.model.addGreeting
import xyz.konyahin.model.getAllGreetings

fun Application.configureRouting() {
    install(ContentNegotiation) {
        json()
    }
    routing {
        route("/api/greetings") {
            get {
                call.respond(
                    getAllGreetings()
                )
            }

            post {
                val request = call.receive<GreetingRequest>()
                addGreeting(request)
                call.respond(HttpStatusCode.Created)
            }
        }

        staticResources("/", "static") {
            default("index.html")
        }
    }
}
