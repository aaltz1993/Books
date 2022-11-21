package a.alt.z.books

import a.alt.z.books.model.Book
import a.alt.z.books.model.BookSearchHistory
import java.time.LocalDate
import java.time.Year

object TestData {

    val book1 = Book(
        "id-1",
        "IT CookBook, 자바 프로그래밍 for Beginner",
        listOf("우재남"),
        "한빛아카데미(주)",
        null,
        LocalDate.of(2018, 11, 10),
        "https://www.googleapis.com/books/v1/volumes/zJ_yDwAAQBAJ",
        "http://books.google.com/books/content?id=zJ_yDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
    )

    val book2 = Book(
        "id-2",
        "Java 9 모듈 프로그래밍",
        listOf("코시크 코타갈"),
        "한빛미디어",
        null,
        LocalDate.of(2018, 5, 10),
        "https://www.googleapis.com/books/v1/volumes/iZdaDwAAQBAJ",
        "http://books.google.com/books/content?id=iZdaDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
    )

    val book3 = Book(
        "id-3",
        "Java Cookbook",
        listOf("Ian F. Darwin"),
        "O'Reilly Media, Inc.",
        Year.of(2001),
        null,
        "https://www.googleapis.com/books/v1/volumes/l_juVVLMMS4C",
        "http://books.google.com/books/content?id=l_juVVLMMS4C&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
    )

    val allBooks = listOf(book1, book2, book3)

    val searchHistory1 = BookSearchHistory("java")

    val searchHistory2 = BookSearchHistory("kotlin")

    val searchHistory3 = BookSearchHistory("android")

    val searchHistories = listOf(searchHistory1, searchHistory2, searchHistory3)
}