package a.alt.z.books.ui.search

import a.alt.z.books.ui.search.result.SearchBookResultDateFormatter
import org.junit.Assert.*

import org.junit.Test
import java.time.LocalDate

class SearchBookResultDateFormatterTest {

    @Test
    fun formatDateToString_Succeeded_ReturnExpected() {
        val expectedFormatString = "2022년 4월 28일"

        val date = LocalDate.of(2022, 4, 28)

        assertEquals(expectedFormatString, SearchBookResultDateFormatter.format(date))
    }

    @Test
    fun formatDateToString_DateIsOutlier_ReturnNull() {
        println(LocalDate.ofEpochDay(0))

        assertNull(SearchBookResultDateFormatter.format(LocalDate.MIN))

        assertNull(SearchBookResultDateFormatter.format(LocalDate.MAX))

        assertNull(SearchBookResultDateFormatter.format(LocalDate.of(20022, 4, 28)))
    }
}