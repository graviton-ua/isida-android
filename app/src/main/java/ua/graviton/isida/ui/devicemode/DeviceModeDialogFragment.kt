package ua.graviton.isida.ui.devicemode

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ua.graviton.isida.R
import ua.graviton.isida.data.bl.model.IsidaCommands
import ua.graviton.isida.databinding.DialogDeviceModeBinding
import ua.graviton.isida.ui.MainActivity

@AndroidEntryPoint
class DeviceModeDialogFragment : DialogFragment(R.layout.dialog_device_mode) {
    private val viewModel: DeviceModeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = DialogDeviceModeBinding.bind(view)

        binding.radioButON.setOnClickListener { viewModel.submitAction(DeviceModeAction.SelectMode(IsidaCommands.DeviceMode.ENABLE)) }
        binding.radioButOFF.setOnClickListener { viewModel.submitAction(DeviceModeAction.SelectMode(IsidaCommands.DeviceMode.DISABLE)) }
        binding.radioButTURN.setOnClickListener { viewModel.submitAction(DeviceModeAction.SelectMode(IsidaCommands.DeviceMode.ONLY_ROTATION)) }

        binding.chip1.setOnClickListener { viewModel.submitAction(DeviceModeAction.ToggleExtra(IsidaCommands.DeviceModeExtra.EXTRA_1)) }
        binding.chip2.setOnClickListener { viewModel.submitAction(DeviceModeAction.ToggleExtra(IsidaCommands.DeviceModeExtra.EXTRA_2)) }
        binding.chip3.setOnClickListener { viewModel.submitAction(DeviceModeAction.ToggleExtra(IsidaCommands.DeviceModeExtra.EXTRA_3)) }
        binding.chip4.setOnClickListener { viewModel.submitAction(DeviceModeAction.ToggleExtra(IsidaCommands.DeviceModeExtra.EXTRA_4)) }

        binding.btnSaveStart.setOnClickListener { viewModel.submitAction(DeviceModeAction.ApplyMode) }

        // Handle states
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect { handle(it, binding) }
        }
        // Handle events
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect { handle(it) }
        }
    }

    private fun handle(state: DeviceModeViewState, binding: DialogDeviceModeBinding) = with(binding) {

        rg.check(
            when (state.mode) {
                IsidaCommands.DeviceMode.DISABLE -> R.id.radioButOFF
                IsidaCommands.DeviceMode.ONLY_ROTATION -> R.id.radioButTURN
                IsidaCommands.DeviceMode.ENABLE -> R.id.radioButON
                null -> 0
            }
        )

        chip1.isChecked = state.extras.contains(IsidaCommands.DeviceModeExtra.EXTRA_1)
        chip2.isChecked = state.extras.contains(IsidaCommands.DeviceModeExtra.EXTRA_2)
        chip3.isChecked = state.extras.contains(IsidaCommands.DeviceModeExtra.EXTRA_3)
        chip4.isChecked = state.extras.contains(IsidaCommands.DeviceModeExtra.EXTRA_4)
    }

    private fun handle(event: DeviceModeEvent) = when (event) {
        is DeviceModeEvent.Send -> {
            //(activity as? MainActivity)?.sendCommand(event.command)
            dismiss()
        }
    }
}