package a.alt.z.books.data.network.model

import com.google.gson.annotations.SerializedName

/**
 * Volume: A volume represents the data that Google Books hosts about a book or magazine.
 */
data class Volume(
    val id: String,
    val volumeInfo: VolumeInfo
) {

    data class VolumeInfo(
        val title: String,
        val subtitle: String?,
        val authors: List<String>?,
        val publisher: String?,
        val publishedDate: String?,
        @SerializedName("infoLink")
        val url: String?,
        val imageLinks: ImageLinks?
    ) {

        data class ImageLinks(
            val smallThumbnail: String?,
            val thumbnail: String?,
            val small: String?,
            val medium: String?,
            val large: String?,
            val extraLarge: String?
        )
    }
}