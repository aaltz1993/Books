package a.alt.z.books.ui

import a.alt.z.books.util.connectivity.NetworkConnectivityManager
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    networkConnectivityManager: NetworkConnectivityManager
) : ViewModel() {

    val isNetworkConnected = networkConnectivityManager.isConnected
}