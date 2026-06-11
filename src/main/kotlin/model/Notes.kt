package xyz.konyahin.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.v1.core.StdOutSqlLogger
import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable
import org.jetbrains.exposed.v1.core.like
import org.jetbrains.exposed.v1.core.lowerCase
import org.jetbrains.exposed.v1.datetime.timestamp
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import xyz.konyahin.utils.UUIDSerializer
import java.util.UUID
import kotlin.time.Clock
import kotlin.time.Instant

@Serializable
data class NoteDto(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val content: String,
    val created: Instant,
)

@Serializable
data class CreateNoteDto(
    val content: String,
)

@Serializable
data class SearchNoteDto(
    val search: String,
)

@Serializable
data class CreatedNoteDto(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val created: Instant,
)

object Notes : UUIDTable("notes") {
    val content = text("content")
    // because SQLite can't make case insensitive search
    val contentLower = text("content_lower")
    val created = timestamp("created_at")

    fun createNote(dto: CreateNoteDto): CreatedNoteDto = transaction {
        val created = Clock.System.now()
        val id = Notes.insertAndGetId {
            it[content] = dto.content
            it[contentLower] = dto.content.lowercase()
            it[Notes.created] = created
        }.value
        CreatedNoteDto(id, created)
    }

    fun getNotes(query: String?) = transaction {
        val notes = if (query == null) {
            Notes.selectAll()
        } else {
            Notes.selectAll().where {
                Notes.contentLower like "%${query.lowercase()}%"
            }
        }

        notes.map {
            NoteDto(
                it[Notes.id].value,
                it[content],
                it[created],
            )
        }
    }
}

