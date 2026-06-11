package xyz.konyahin

import io.ktor.server.application.Application
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import xyz.konyahin.model.Greetings

fun Application.configureExposed() {
    Database.connect(
        url = environment.config.property("db.url").getString(),
        driver = environment.config.property("db.driver").getString(),
    )

    transaction {
        SchemaUtils.create(Greetings)
    }
}