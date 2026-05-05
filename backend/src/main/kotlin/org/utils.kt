package org

import com.opencsv.CSVReader
import kotlinx.serialization.Serializable
import java.io.FileReader

@Serializable
data class Movie(
    val id: Int,
    val movieName: String,
    val distributor: String
)

fun readMoviesFromCsv(): List<Movie> {
    val movies = mutableListOf<Movie>()
    try {
        CSVReader(FileReader("/home/alina/Documents/compose-multiplatform/examples/widgets-gallery/backend/src/main/resources/movies.csv")).use { reader ->
            reader.readNext()
            var line: Array<String>?
            while (reader.readNext().also { line = it } != null) {
                line?.let {
                    try {
                        movies.add(Movie(
                            id = it[0].toInt(),
                            movieName = it[1],
                            distributor = it[4]
                        ))
                    } catch (e: Exception) {
                        println("Error parsing line: ${e.message}")
                    }
                }
            }
        }
    } catch (e: Exception) {
        println("Error reading CSV file: ${e.message}")
    }
    return movies
}
