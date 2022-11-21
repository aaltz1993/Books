package a.alt.z.books.ui

import a.alt.z.books.R
import a.alt.z.books.databinding.ActivityMainBinding
import a.alt.z.books.ui.search.SearchBookFragment
import a.alt.z.books.util.view_binding.viewBinding
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.root_layout, SearchBookFragment())
            }
        }

        setupObservers()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isNetworkConnected.collect { isConnected ->
                        if (!isConnected) {
                            Snackbar
                                .make(binding.root, R.string.network_error_message, Snackbar.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }
}