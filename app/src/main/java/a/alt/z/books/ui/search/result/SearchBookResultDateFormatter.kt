package a.alt.z.books.ui.search.result

import timber.log.Timber
import java.time.LocalDate
import java.time.Year
import java.time.format.DateTimeFormatter

object SearchBookResultDateFormatter {

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 dd일")

    private val yearFormatter = DateTimeFormatter.ofPattern("yyyy년")

    fun format(date: LocalDate): String? {
        if (isOutlier(date)) return null

        return try {
            dateFormatter.format(date)
        } catch (exception: Exception) {
            Timber.e(exception)
            null
        }
    }

    fun format(year: Year): String? {
        if (year.value < 0 || year.value > Year.now().value) return null

        return try {
            yearFormatter.format(year)
        } catch (exception: Exception) {
            Timber.e(exception)
            null
        }
    }

    private fun isOutlier(date: LocalDate): Boolean {
        val from = LocalDate.ofEpochDay(0)
        val now = LocalDate.now()
        return date !in from..now
    }
}