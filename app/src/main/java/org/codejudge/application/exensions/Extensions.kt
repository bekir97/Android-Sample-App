package org.codejudge.application.exensions

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import org.codejudge.application.R

@BindingAdapter("imageUrl")
fun setImageUrl(view: ImageView, imageUrl:String?) {
    Glide.with(view.context)
        .load(imageUrl)
        .placeholder(R.drawable.ic_action_placeholder)
        .into(view)
}

@BindingAdapter("booleanToString")
fun setImageUrl(view: TextView, boolean: Boolean?) {
    boolean?.let {
        if (boolean){
           view.text = "Yes"
       }else {
           view.text = "No"
        }
       }
}