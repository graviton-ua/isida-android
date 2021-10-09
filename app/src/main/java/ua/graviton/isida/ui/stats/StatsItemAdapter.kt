package ua.graviton.isida.ui.stats

import android.content.res.ColorStateList
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import ua.graviton.isida.R
import ua.graviton.isida.databinding.ItemStatsItemBinding
import ua.graviton.isida.utils.WhViewHolder

class StatsItemAdapter : ListAdapter<StatsItem, StatsItemAdapter.Holder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(parent)

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(getItem(position))


    class Holder(parent: ViewGroup) : WhViewHolder(parent, R.layout.item_stats_item) {
        private val binding by viewBinding(ItemStatsItemBinding::bind)

        fun bind(item: StatsItem) = with(binding) {
            tvTitle.setText(item.titleResId)
            tvTitle.setTextColor(item.titleColor)
            tvValue.text = item.value?.toString() ?: "--"
        }
    }
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StatsItem>() {
    override fun areItemsTheSame(old: StatsItem, aNew: StatsItem): Boolean = old.titleResId == aNew.titleResId

    override fun areContentsTheSame(old: StatsItem, aNew: StatsItem): Boolean = old == aNew
}