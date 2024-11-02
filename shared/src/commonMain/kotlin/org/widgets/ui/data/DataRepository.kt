package org.widgets.ui.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

val httpClient = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
        })
    }
}

suspend fun fetchMoviesTitles(): List<String> {
    return httpClient.get("http://localhost:8080/movies/titles").body()
}

suspend fun fetchDistributors(): List<String> {
    return httpClient.get("http://localhost:8080/movies/distributors").body()
}