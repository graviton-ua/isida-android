package ua.graviton.isida

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Suppress("ObjectPropertyName")
object DataHolder {
    //**----------------------------------------------------------------------------**/
    private val _permit = MutableStateFlow<Byte>(0)
    val permit: StateFlow<Byte> = _permit
    fun setPermit(value: Byte) {
        _permit.value = value
    }

    //**----------------------------------------------------------------------------**/
    private val _temp0 = MutableStateFlow<Short>(0)
    val temp0: StateFlow<Short> = _temp0
    fun setTemp0(value: Short) {
        _temp0.value = value
    }

    //**----------------------------------------------------------------------------**/
    private val _temp1 = MutableStateFlow<Short>(0)
    val temp1: StateFlow<Short> = _temp1
    fun setTemp1(value: Short) {
        _temp1.value = value
    }

    //**----------------------------------------------------------------------------**/
    private val _temp2 = MutableStateFlow<Short>(0)
    val temp2: StateFlow<Short> = _temp2
    fun setTemp2(value: Short) {
        _temp2.value = value
    }

    //**----------------------------------------------------------------------------**/
    private val _temp3 = MutableStateFlow<Short>(0)
    val temp3: StateFlow<Short> = _temp3
    fun setTemp3(value: Short) {
        _temp3.value = value
    }

    //**----------------------------------------------------------------------------**/
    private val _rh = MutableStateFlow<Short>(0)
    val rh: StateFlow<Short> = _rh
    fun setRh(value: Short) {
        _rh.value = value
    }

    //**----------------------------------------------------------------------------**/
    private val _cotwo = MutableStateFlow<Short>(0)
    val cotwo: StateFlow<Short> = _cotwo
    fun setCoTwo(value: Short) {
        _cotwo.value = value
    }

    //**----------------------------------------------------------------------------**/
    private val _timer = MutableStateFlow<Short>(0)
    val timer: StateFlow<Short> = _timer
    fun setTimer(value: Short) {
        _timer.value = value
    }

    //**----------------------------------------------------------------------------**/
    private val _count = MutableStateFlow<Short>(0)
    val count: StateFlow<Short> = _count
    fun setCount(value: Short) {
        _count.value = value
    }

    //**----------------------------------------------------------------------------**/
    private val _flap = MutableStateFlow<Short>(0)
    val flap: StateFlow<Short> = _flap
    fun setFlap(value: Short) {
        _flap.value = value
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