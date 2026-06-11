package xyz.konyahin.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import xyz.konyahin.utils.UUIDSerializer
import java.util.UUID

@Serializable
data class NoteDto(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val content: String,
)

@Serializable
data class CreateNoteDto(
    val content: String,
)

@Serializable
data class CreatedNoteDto(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
)

object Notes : UUIDTable("notes") {
    val content = text("content")

    fun createNote(dto: CreateNoteDto): CreatedNoteDto = transaction {
        val id = Notes.insertAndGetId {
            it[content] = dto.content
        }.value
        CreatedNoteDto(id)
    }

    fun getNotes() = transaction {
        Notes.selectAll().map {
            NoteDto(it[Notes.id].value, it[Notes.content])
        }
    }
}

