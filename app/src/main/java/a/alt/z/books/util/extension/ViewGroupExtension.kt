package a.alt.z.books.util.extension

import android.view.LayoutInflater
import android.view.ViewGroup

val ViewGroup.layoutInflater: LayoutInflater get() = LayoutInflater.from(context)