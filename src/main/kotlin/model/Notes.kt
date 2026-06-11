package xyz.konyahin.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable
import org.jetbrains.exposed.v1.jdbc.insert
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

object Notes : UUIDTable("notes") {
    val content = text("content")

    fun createNote(dto: CreateNoteDto) = transaction {
        Notes.insert {
            it[content] = dto.content
        }
    }

    fun getNotes() = transaction {
        Notes.selectAll().map {
            NoteDto(it[Notes.id].value, it[Notes.content])
        }
    }
}

