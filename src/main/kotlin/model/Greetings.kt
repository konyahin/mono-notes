package xyz.konyahin.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object Greetings : UUIDTable("greetings") {
    val body = text("body")
}

@Serializable
data class GreetingRequest(val body: String)

fun addGreeting(request: GreetingRequest) = transaction {
    Greetings.insert {
        it[body] = request.body
    }
}

fun getAllGreetings() = transaction {
    Greetings.selectAll().map { it[Greetings.body] }
}
