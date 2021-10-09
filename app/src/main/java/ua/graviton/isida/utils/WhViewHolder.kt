package ua.graviton.isida.utils

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

open class WhViewHolder(parent: ViewGroup, @LayoutRes resId: Int) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(resId, parent, false)
) {
    val context: Context get() = itemView.context
    val resources: Resources get() = itemView.resources
}