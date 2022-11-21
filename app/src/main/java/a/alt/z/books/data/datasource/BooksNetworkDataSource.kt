package a.alt.z.books.data.datasource

import a.alt.z.books.data.network.GoogleBooksNetworkService
import a.alt.z.books.data.network.model.Volume
import a.alt.z.books.model.Book
import a.alt.z.books.model.BookSearchResults
import java.time.LocalDate
import java.time.Year
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

interface BooksNetworkDataSource {

    suspend fun searchBook(query: String, index: Int) : BookSearchResults
}

@Singleton
class DefaultBooksNetworkDataSource @Inject constructor(
    private val service: GoogleBooksNetworkService
) : BooksNetworkDataSource {

    override suspend fun searchBook(query: String, index: Int) : BookSearchResults {
        val response = service.searchBook(query, index)

        return BookSearchResults(
            query,
            response.totalItems,
            response.items?.map(::transform).orEmpty()
        )
    }

    private fun transform(volume: Volume) = with(volume) {
        val publishedDate = try {
            LocalDate.parse(volumeInfo.publishedDate, DateTimeFormatter.ISO_LOCAL_DATE)
        } catch(exception: Exception) {
            null
        }

        val publishedYear = try {
            Year.parse(volumeInfo.publishedDate)
        } catch(exception: Exception) {
            publishedDate?.year?.let { Year.of(it) }
        }

        Book(
            id,
            volumeInfo.title,
            volumeInfo.authors.orEmpty(),
            volumeInfo.publisher,
            publishedYear,
            publishedDate,
            volumeInfo.url,
            volumeInfo.imageLinks?.run { extraLarge ?: large ?: medium ?: small ?: thumbnail ?: smallThumbnail }
        )
    }
}