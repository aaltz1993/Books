package a.alt.z.books.util.extension

import android.content.Context
import android.util.TypedValue

fun Context.pixelOf(dp: Int) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics)