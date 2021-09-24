package ua.graviton.isida.domain.model

data class DataPackage(
    val cellNumber: Int = 0,
    val temp0: Float = 0f,
    val temp1: Float = 0f,
    val temp2: Float = 0f,
    val temp3: Float = 0f,
    val rh: Float = 0f,
    val coTwo: Short = 0,
    val timer: Short = 0,
    val count: Short = 0,
    val flap: Short = 0,
) {
    companion object {

        fun parseFromData(data: ByteArray): DataPackage? {
            if (data.size > 10) {
                val longDataRX = ShortArray(8)
                val dataRX = ShortArray(data.size)
                var i = 0
                // убираем отрицательные значения ------------------------------------------------
                while (i < data.size) {
                    if (data[i] < 0) dataRX[i] = (data[i] + 255).toShort() else dataRX[i] =
                        data[i].toShort()
                    i++
                }
                var index = 0
                // расчитываем 8 byte int16_t начиная с dataRX[1]
                i = 1
                while (i < 17) {
                    longDataRX[index] = (1 + dataRX[i + 1] + dataRX[i] * 256).toShort()
                    index++
                    i += 2
                }

                // -----------ID камеры -----------------------------------------
                //binding.tvCellMain.text = getString(R.string.CellNum, dataRX[0])
                val cellNumber = dataRX[0].toInt()

                // ---------- pvT[0] СУХОЙ --------------------------------------
                //binding.tvTemp0.text = (longDataRX[0].toFloat() / 10).toString()
                val temp0 = longDataRX[0].toFloat() / 10

                var temp1 = 0f
                var temp2 = 0f
                var rh = 0f
                // ---------- pvT[1] ВЛАЖНЫЙ ------------------------------------
                if (longDataRX[4] == 0.toShort()) {
                    //binding.tvTemp1.text = (longDataRX[1].toFloat() / 10).toString()
                    temp1 = longDataRX[1].toFloat() / 10

                    // ---------- pvT[2] КОНТРОЛЬНЫЙ --------------------------------
                    //if (longDataRX[2] > 800) binding.tvTemp2.visibility = View.INVISIBLE
                    //else binding.tvTemp2.text = (longDataRX[2].toFloat() / 10).toString()
                    temp2 = longDataRX[2].toFloat() / 10
                } else {
                    // ------------ pvRH датчик относительной влажности ----------------
                    //binding.tvTemp1.text = (longDataRX[4].toFloat() / 10).toString()
                    //binding.tvTempName1.text = "Отн.Влаж."
                    rh = longDataRX[4].toFloat() / 10

                    // ---------- pvT[2] КОНТРОЛЬНЫЙ --------------------------------
                    //if (longDataRX[1] > 800 && longDataRX[2] > 800) binding.tvTemp2.visibility = View.INVISIBLE
                    //else if (longDataRX[2] > 800) {
                    //    binding.tvTemp2.text = (longDataRX[1].toFloat() / 10).toString()
                    //} else if (longDataRX[1] > 800) {
                    //    binding.tvTemp2.text = (longDataRX[2].toFloat() / 10).toString()
                    //} else {
                    //    binding.tvTemp2.text = (((longDataRX[1] + longDataRX[2]) / 2).toFloat() / 10).toString()
                    //}
                }

                // ---------- pvT[3] СКОРЛУПА --------------------------------------
                //if (longDataRX[3] > 800) binding.tvTemp3.visibility = View.INVISIBLE
                //else binding.tvTemp3.text = (longDataRX[3].toFloat() / 10).toString()
                val temp3 = longDataRX[3].toFloat() / 10

                // ---------- CO2 углекислый газ ------------------------------------
                //if (longDataRX[5] > 0) binding.tvCoTwo.text = longDataRX[2].toString()
                //else binding.tvCoTwo.visibility = View.INVISIBLE
                val coTwo = longDataRX[5]

                val timer = dataRX[18]   // pvTimer значение таймера
                val count = dataRX[19]   // pvCount значение счетчика проходов поворота
                val flap = dataRX[20]    // pvFlap положение заслонки
                return DataPackage(
                    cellNumber = cellNumber,
                    temp0 = temp0,
                    temp1 = temp1,
                    temp2 = temp2,
                    temp3 = temp3,
                    rh = rh,
                    coTwo = coTwo,
                    timer = timer,
                    count = count,
                    flap = flap,
                )
            }
            return null
        }
    }
}