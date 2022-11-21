package a.alt.z.books.model

data class BookSearchResults(
    val query: String,
    val totalCount: Int,
    val data: List<Book>
)