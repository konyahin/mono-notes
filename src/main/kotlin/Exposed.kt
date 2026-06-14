package xyz.konyahin

import io.ktor.server.application.Application
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import xyz.konyahin.model.Notes

fun Application.configureExposed() {
    val dbPath = environment.config.property("db.path").getString()
    Database.connect(
        url = "jdbc:sqlite:$dbPath",
        driver = environment.config.property("db.driver").getString(),
    )

    transaction {
        SchemaUtils.create(Notes)
    }
}