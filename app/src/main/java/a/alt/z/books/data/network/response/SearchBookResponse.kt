package a.alt.z.books.data.network.response

import a.alt.z.books.data.network.model.Volume

data class SearchBookResponse(
    val kind: String,
    val totalItems: Int,
    val items: List<Volume>?
)