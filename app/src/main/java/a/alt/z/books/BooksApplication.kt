package a.alt.z.books

import a.alt.z.books.util.debug.BooksDebugTree
import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*
import timber.log.Timber

@HiltAndroidApp
class BooksApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(BooksDebugTree())
        }
    }
}