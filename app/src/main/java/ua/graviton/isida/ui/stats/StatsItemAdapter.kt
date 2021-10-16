package ua.graviton.isida.ui.stats

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
            val valueColor = resources.getColorStateList(item.valueColor ?: R.color.black, itemView.context.theme)
            tvTitle.setTextColor(valueColor)
            val value = when (val v = item.value) {
                is StatsItem.Value.Float -> {
                    val value = v.value?.let { String.format("%.1f", it) }
                    if (v.target == null) value ?: "--" else value?.let { it + "  [${v.target}]" } ?: "--"
                }
                is StatsItem.Value.Int -> {
                    val value = v.value?.toString()
                    if (v.target == null) value ?: "--" else value?.let { it + "  [${v.target}]" } ?: "--"
                }
                is StatsItem.Value.Text -> {
                    if (v.target == null) v.value ?: "--" else v.value?.let { it + "  [${v.target}]" } ?: "--"
                }
                null -> "--"
            }
            tvValue.text = value
            itemView.setBackgroundResource(item.backgroundColor ?: android.R.color.transparent)
        }
    }
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StatsItem>() {
    override fun areItemsTheSame(old: StatsItem, aNew: StatsItem): Boolean = old.titleResId == aNew.titleResId

    override fun areContentsTheSame(old: StatsItem, aNew: StatsItem): Boolean = old == aNew
}