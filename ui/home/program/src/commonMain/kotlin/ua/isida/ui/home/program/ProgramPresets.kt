package ua.isida.ui.home.program

import ua.isida.data.protocol.packets.TablePacket
import ua.isida.data.protocol.packets.v1.TableDayV1
import ua.isida.data.protocol.packets.v1.TablePacketV1

data class ProgramPreset(
    val id: String,
    val table: TablePacket,
) {
    companion object {
        val ALL = listOf(
            ProgramPreset(
                id = "chicken",
                table = TablePacketV1(
                    days = List(30) { index ->
                        when (index) {
                            in 0..4 -> TableDayV1(spT0 = 37.9f, spT1 = 31.0f, spRh = 60, spFlp = 0, spTr = 1, spCO2 = 2000)
                            in 5..11 -> TableDayV1(spT0 = 37.8f, spT1 = 30.0f, spRh = 58, spFlp = 10, spTr = 1, spCO2 = 2000)
                            in 12..17 -> TableDayV1(spT0 = 37.6f, spT1 = 29.0f, spRh = 55, spFlp = 20, spTr = 1, spCO2 = 2000)
                            in 18..21 -> TableDayV1(spT0 = 37.4f, spT1 = 32.0f, spRh = 65, spFlp = 30, spTr = 0, spCO2 = 2000)
                            else -> TableDayV1(spT0 = 28.0f, spT1 = 20.0f, spRh = 45, spFlp = 90, spTr = 0, spCO2 = 1000)
                        }
                    }
                )
            ),
            ProgramPreset(
                id = "duck",
                table = TablePacketV1(
                    days = List(30) { index ->
                        when (index) {
                            in 0..7 -> TableDayV1(spT0 = 38.0f, spT1 = 33.0f, spRh = 70, spFlp = 0, spTr = 1, spCO2 = 2000)
                            in 8..12 -> TableDayV1(spT0 = 37.5f, spT1 = 31.0f, spRh = 60, spFlp = 10, spTr = 1, spCO2 = 2000)
                            in 13..23 -> TableDayV1(spT0 = 37.2f, spT1 = 30.0f, spRh = 56, spFlp = 20, spTr = 1, spCO2 = 2000)
                            in 24..27 -> TableDayV1(spT0 = 37.0f, spT1 = 32.0f, spRh = 70, spFlp = 30, spTr = 0, spCO2 = 2000)
                            else -> TableDayV1(spT0 = 28.0f, spT1 = 20.0f, spRh = 45, spFlp = 90, spTr = 0, spCO2 = 1000)
                        }
                    }
                )
            ),
            ProgramPreset(
                id = "duck1",
                table = TablePacketV1(
                    days = List(30) { index ->
                        when (index) {
                            in 0..11 -> TableDayV1(spT0 = 37.6f, spT1 = 31.0f, spRh = 58, spFlp = 0, spTr = 1, spCO2 = 2000)
                            in 12..14 -> TableDayV1(spT0 = 37.3f, spT1 = 29.0f, spRh = 53, spFlp = 5, spTr = 1, spCO2 = 2000)
                            in 15..16 -> TableDayV1(spT0 = 37.2f, spT1 = 28.0f, spRh = 47, spFlp = 15, spTr = 0, spCO2 = 2000)
                            in 17..18 -> TableDayV1(spT0 = 37.0f, spT1 = 34.0f, spRh = 80, spFlp = 20, spTr = 0, spCO2 = 2000)
                            else -> TableDayV1(spT0 = 28.0f, spT1 = 20.0f, spRh = 45, spFlp = 90, spTr = 0, spCO2 = 1000)
                        }
                    }
                )
            ),
            ProgramPreset(
                id = "duck2",
                table = TablePacketV1(
                    days = List(30) { index ->
                        when (index) {
                            in 0..5 -> TableDayV1(spT0 = 37.8f, spT1 = 30.0f, spRh = 58, spFlp = 0, spTr = 1, spCO2 = 2000)
                            in 6..11 -> TableDayV1(spT0 = 37.5f, spT1 = 29.0f, spRh = 53, spFlp = 15, spTr = 1, spCO2 = 2000)
                            in 12..25 -> TableDayV1(spT0 = 37.2f, spT1 = 28.8f, spRh = 47, spFlp = 25, spTr = 1, spCO2 = 2000)
                            in 26..27 -> TableDayV1(spT0 = 37.0f, spT1 = 32.0f, spRh = 80, spFlp = 35, spTr = 0, spCO2 = 2000)
                            else -> TableDayV1(spT0 = 28.0f, spT1 = 20.0f, spRh = 45, spFlp = 90, spTr = 0, spCO2 = 1000)
                        }
                    }
                )
            ),
        )
    }
}
