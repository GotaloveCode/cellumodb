
package com.martiniriga.moviedb.adapters

import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.martiniriga.moviedb.utils.Constants
import com.martiniriga.moviedb.utils.PreferenceHelper
import com.martiniriga.moviedb.utils.PreferenceHelper.get


@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}


@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    val prefs = PreferenceHelper.defaultPrefs(view.context)
    val imageurlStr:String? = prefs[Constants.imageUrl]
    var baseimageUrl = ""
    imageurlStr?.let{ baseimageUrl = it }
    val fullImageUrl = baseimageUrl + imageUrl
    Log.d("fullImage Url",fullImageUrl)

    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(fullImageUrl)
            .into(view)
    }
}
