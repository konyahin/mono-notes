package xyz.konyahin.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.like
import org.jetbrains.exposed.v1.core.lowerCase
import org.jetbrains.exposed.v1.datetime.timestamp
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update
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
    val isArchived: Boolean,
)

@Serializable
data class CreateNoteDto(
    val content: String,
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
    val isArchived = bool("is_archived")

    fun createNote(dto: CreateNoteDto): CreatedNoteDto = transaction {
        val created = Clock.System.now()
        val id = Notes.insertAndGetId {
            it[Notes.content] = dto.content
            it[Notes.contentLower] = dto.content.lowercase()
            it[Notes.created] = created
            it[Notes.isArchived] = false
        }.value
        CreatedNoteDto(id, created)
    }

    fun getNotes(query: String?, archived: Boolean) = transaction {
        val notes = if (query == null) {
            Notes.selectAll().orderBy(Notes.created).where {
                Notes.isArchived eq archived
            }
        } else {
            Notes.selectAll().orderBy(Notes.created).where {
                (Notes.isArchived eq archived) and (Notes.contentLower like "%${query.lowercase()}%")
            }
        }

        notes.map {
            NoteDto(
                it[Notes.id].value,
                it[Notes.content],
                it[Notes.created],
                it[Notes.isArchived]
            )
        }
    }

    fun archiveNote(id: UUID) = transaction {
        Notes.update({ Notes.id eq id }) {
            it[Notes.isArchived] = true
        }
    }
}

