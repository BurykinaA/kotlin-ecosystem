package org.widgets

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import org.Movie
import org.readMoviesFromCsv


private lateinit var cachedMovies: List<Movie>

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    cachedMovies = readMoviesFromCsv().ifEmpty {
        println("Warning: No movies found in CSV file.")
        listOf()
    }
    println("Loaded movies: ${cachedMovies.map { it.movieName }}")

    install(ContentNegotiation) {
        json()
    }

    routing {
        get("/") {
            call.respondText("Ktor: I am working!!")
        }

        get("/movies/titles") {
            if (cachedMovies.isEmpty()) {
                call.respond(HttpStatusCode.InternalServerError, "Movies data not loaded")
            } else {
                call.respond(cachedMovies.map { it.movieName })
            }
        }

        get("/movies/distributors") {
            call.respond(cachedMovies.map { it.distributor }.distinct())
        }
    }
}