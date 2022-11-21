package a.alt.z.books.util.extension

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

val Context.inputMethodManager: InputMethodManager get() = getSystemService(InputMethodManager::class.java)

fun EditText.showKeyboard() = context.inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)

fun EditText.hideKeyboard() = context.inputMethodManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)