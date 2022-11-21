package a.alt.z.books.di

import a.alt.z.books.BuildConfig
import a.alt.z.books.data.network.GoogleBooksNetworkService
import a.alt.z.books.util.connectivity.NetworkConnectivityManager
import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides @Singleton
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            }
        }
        .build()

    @Provides @Singleton
    fun providesConverterFactory(): Converter.Factory = GsonConverterFactory.create()

    @Provides @Singleton
    fun providesGoogleBooksService(
        client: OkHttpClient,
        converterFactory: Converter.Factory
    ): GoogleBooksNetworkService = GoogleBooksNetworkService.create(client, converterFactory)

    @Provides @Singleton
    fun providesConnectivityManager(
        @ApplicationContext context: Context
    ): ConnectivityManager = context.getSystemService(ConnectivityManager::class.java)

    @Provides @Singleton
    fun providesNetworkConnectivityManager(
        connectivityManager: ConnectivityManager
    ): NetworkConnectivityManager = NetworkConnectivityManager(connectivityManager)
}