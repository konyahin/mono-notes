package xyz.konyahin

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.http.content.staticResources
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import xyz.konyahin.model.CreateNoteDto
import xyz.konyahin.model.NoteDto
import xyz.konyahin.model.Notes

fun Application.configureRouting() {
    install(ContentNegotiation) {
        json()
    }
    routing {
        route("/api/notes") {
            get {
                call.respond(
                    Notes.getNotes()
                )
            }

            post {
                val dto = call.receive<CreateNoteDto>()
                Notes.createNote(dto)
                call.respond(HttpStatusCode.Created)
            }
        }

        staticResources("/", "static") {
            default("index.html")
        }
    }
}
