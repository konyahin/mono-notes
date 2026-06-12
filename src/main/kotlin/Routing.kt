package xyz.konyahin

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.http.content.staticResources
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.getOrFail
import xyz.konyahin.model.CreateNoteDto
import xyz.konyahin.model.Notes
import java.util.UUID

fun Application.configureRouting() {
    install(ContentNegotiation) {
        json()
    }
    routing {
        route("/api/notes") {
            get {
                val query = call.request.queryParameters["q"]?.takeIf { it.isNotBlank() }
                val archived = call.request.queryParameters["archived"]?.toBoolean() ?: false
                call.respond(
                    Notes.getNotes(query, archived)
                )
            }

            post {
                val dto = call.receive<CreateNoteDto>()
                val createdNote = Notes.createNote(dto)
                call.respond(HttpStatusCode.Created, createdNote)
            }

            post("/{id}/archive") {
                val id = try {
                    UUID.fromString(call.parameters.getOrFail("id"))
                } catch (e: IllegalArgumentException) {
                    return@post call.respond(HttpStatusCode.BadRequest, "invalid uuid")
                }
                Notes.archiveNote(id)
                call.respond(HttpStatusCode.NoContent)
            }
        }

        staticResources("/", "static") {
            default("index.html")
        }
    }
}
