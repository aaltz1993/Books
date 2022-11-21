package a.alt.z.books.util.debug

import timber.log.Timber

class BooksDebugTree : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String {
        return "@Books::${element.className}.${element.lineNumber}"
    }
}