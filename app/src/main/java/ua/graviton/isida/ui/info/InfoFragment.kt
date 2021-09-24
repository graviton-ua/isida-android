package ua.graviton.isida.ui.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ua.graviton.isida.R
import ua.graviton.isida.databinding.FragmentInfoBinding

@AndroidEntryPoint
class InfoFragment : Fragment(R.layout.fragment_info) {
    private val viewModel: InfoViewModel by viewModels()

    companion object {
        fun newInstance() = InfoFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentInfoBinding.bind(view)
    }
}