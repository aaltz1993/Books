package a.alt.z.books.model

import java.time.LocalDate
import java.time.Year

data class Book(
    val id: String,
    val title: String,
    val authors: List<String>,
    val publisher: String?,
    val publishedYear: Year?,
    val publishedDate: LocalDate?,
    val url: String?,
    val imageUrl: String?
)
