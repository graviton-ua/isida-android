package ua.graviton.isida.ui.report

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ua.graviton.isida.R
import ua.graviton.isida.databinding.FragmentReportBinding

@AndroidEntryPoint
class ReportFragment : Fragment(R.layout.fragment_report) {
    private val viewModel: ReportViewModel by viewModels()

    companion object {
        fun newInstance() = ReportFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentReportBinding.bind(view)
    }
}