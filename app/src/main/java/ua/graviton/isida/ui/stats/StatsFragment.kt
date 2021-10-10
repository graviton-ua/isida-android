package ua.graviton.isida.ui.stats

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ua.graviton.isida.R
import ua.graviton.isida.databinding.FragmentStatsBinding

@AndroidEntryPoint
class StatsFragment : Fragment(R.layout.fragment_stats) {
    private val viewModel: StatsViewModel by viewModels()

    companion object {
        fun newInstance() = StatsFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentStatsBinding.bind(view)

        val adapter = StatsItemAdapter()
        binding.list.setHasFixedSize(true)
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = adapter

        viewModel.state.observe(viewLifecycleOwner) {
            handle(binding, adapter, it)
        }
    }

    private fun handle(
        binding: FragmentStatsBinding,
        adapter: StatsItemAdapter,
        state: StatsViewState
    ) {
        binding.tvCellMain.text = getString(R.string.CellNum, state.titleDeviceId?.toString() ?: "--")
        binding.tvCellMain.setBackgroundResource(state.titleDeviceBackgroundColor ?: android.R.color.transparent)
        adapter.submitList(state.items)
    }
}