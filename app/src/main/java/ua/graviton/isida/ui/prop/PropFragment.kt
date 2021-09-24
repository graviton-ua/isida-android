package ua.graviton.isida.ui.prop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ua.graviton.isida.R
import ua.graviton.isida.databinding.FragmentPropBinding

@AndroidEntryPoint
class PropFragment : Fragment(R.layout.fragment_prop) {
    private val viewModel: PropViewModel by viewModels()

    companion object {
        fun newInstance() = PropFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPropBinding.bind(view)
    }
}