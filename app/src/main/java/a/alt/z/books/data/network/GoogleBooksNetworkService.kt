package a.alt.z.books.data.network

import a.alt.z.books.data.network.response.SearchBookResponse
import androidx.annotation.IntRange
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksNetworkService {

    /**
     * @param projection Restrict information returned to a set of selected fields. full or lite
     */
    @GET("volumes")
    suspend fun searchBook(
        @Query("q") query: String,
        @Query("startIndex") startIndex: Int = START_INDEX,
        @IntRange(from = 0, to = 40) @Query("maxResults") perPage: Int = PER_PAGE,
        @Query("projection") projection: String = PROJECTION,
        @Query("key") apiKey: String = API_KEY
    ) : SearchBookResponse

    companion object {
        private const val START_INDEX = 0
        private const val PER_PAGE = 30
        private const val PROJECTION = "lite"
        private const val API_KEY = "AIzaSyDP84f-sLyH_87SvB76vWohIZ-M0vNyLqQ"

        private const val BASE_URL = "https://www.googleapis.com/books/v1/"

        fun create(client: OkHttpClient, converterFactory: Converter.Factory): GoogleBooksNetworkService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(converterFactory)
                .build()
                .create(GoogleBooksNetworkService::class.java)
        }
    }
}