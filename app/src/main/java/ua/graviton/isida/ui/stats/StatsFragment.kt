package ua.graviton.isida.ui.stats

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

        viewModel.state.observe(viewLifecycleOwner) { handle(binding, it) }
    }

    private fun handle(binding: FragmentStatsBinding, state: StatsViewState) {
        binding.tvCellMain.text = getString(R.string.CellNum, state.cellNumber)
        binding.tvTemp0.text = state.temp0.toString()
        binding.tvTemp1.text = state.temp1.toString()
        binding.tvTemp2.text = state.temp2.toString()
        binding.tvTemp3.text = state.temp3.toString()
        //binding.tvTemp3.text = state.temp3.toString()
        binding.tvCoTwo.text = state.coTwo.toString()
        binding.tvTimer.text = state.timer.toString()
        binding.tvCount.text = state.count.toString()
        binding.tvFlap.text = state.flap.toString()
    }
}