package ua.graviton.isida.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.graviton.isida.data.bl.model.DataPackageDto

@Suppress("ObjectPropertyName")
object DataHolder {
    //**----------------------------------------------------------------------------**/
    private val _latestData = MutableStateFlow<DataPackageDto?>(null)
    val latestData: StateFlow<DataPackageDto?> = _latestData
    fun putData(data: DataPackageDto) {
        _latestData.value = data
    }

    //**----------------------------------------------------------------------------**/
    private val _permit = MutableStateFlow<Byte>(0)
    val permit: StateFlow<Byte> = _permit
    fun setPermit(value: Byte) {
        _permit.value = value
    }
}

/*
//---------- Подписка --------------
lifecycleScope.launchWhenStarted {
    DataHolder.dry1.collect {
        tvDry1.setText(it.toString())
    }
}
//---------- Чтение --------------
val dry1Value = DataHolder.dry1.value
//---------- Запись --------------
DataHolder.setDry1(0.4f)*/