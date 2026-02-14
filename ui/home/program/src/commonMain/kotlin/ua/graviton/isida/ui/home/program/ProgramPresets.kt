package ua.graviton.isida.ui.home.program

import androidx.compose.runtime.Composable
import ua.graviton.isida.data.protocol.packets.TablePacket
import ua.graviton.isida.data.protocol.packets.v1.TableDayV1
import ua.graviton.isida.data.protocol.packets.v1.TablePacketV1

data class ProgramPreset(
    val id: String,
    val name: @Composable () -> String,
    val description: @Composable () -> String,
    val table: TablePacket,
) {
    companion object {
        val ALL = listOf(
            ProgramPreset(
                id = "chicken",
                name = { "Chicken" },
                description = { "Optimized settings for chicken eggs incubation." },
                table = TablePacketV1(
                    days = List(30) { index ->
                        when {
                            index in 1..5 -> TableDayV1(spT0 = 37.9f, spT1 = 31.0f, spRh = 60, spFlp = 0, spTr = 1, spCl = 0)
                            index in 6..12 -> TableDayV1(spT0 = 37.8f, spT1 = 30.0f, spRh = 58, spFlp = 10, spTr = 1, spCl = 0)
                            index in 13..18 -> TableDayV1(spT0 = 37.6f, spT1 = 29.0f, spRh = 55, spFlp = 20, spTr = 1, spCl = 0)
                            index in 19..22 -> TableDayV1(spT0 = 37.4f, spT1 = 32.0f, spRh = 65, spFlp = 30, spTr = 0, spCl = 0)
                            else -> TableDayV1(spT0 = 28.0f, spT1 = 20.0f, spRh = 45, spFlp = 90, spTr = 0, spCl = 0)
                        }
                    }
                )
            ),
            ProgramPreset(
                id = "duck",
                name = { "Duck" },
                description = { "Optimized settings for duck eggs incubation." },
                table = TablePacketV1(
                    days = List(30) { index ->
                        when {
                            index in 1..8 -> TableDayV1(spT0 = 38.0f, spT1 = 33.0f, spRh = 70, spFlp = 0, spTr = 1, spCl = 0)
                            index in 9..13 -> TableDayV1(spT0 = 37.5f, spT1 = 31.0f, spRh = 60, spFlp = 10, spTr = 1, spCl = 1)
                            index in 14..24 -> TableDayV1(spT0 = 37.2f, spT1 = 30.0f, spRh = 56, spFlp = 20, spTr = 1, spCl = 1)
                            index in 25..28 -> TableDayV1(spT0 = 37.0f, spT1 = 32.0f, spRh = 70, spFlp = 30, spTr = 0, spCl = 1)
                            else -> TableDayV1(spT0 = 28.0f, spT1 = 20.0f, spRh = 45, spFlp = 90, spTr = 0, spCl = 0)
                        }
                    }
                )
            ),
            ProgramPreset(
                id = "duck1",
                name = { "Duck1" },
                description = { "Optimized settings for duck1 eggs incubation." },
                table = TablePacketV1(
                    days = List(30) { index ->
                        when {
                            index in 1..12 -> TableDayV1(spT0 = 37.6f, spT1 = 31.0f, spRh = 58, spFlp = 0, spTr = 1, spCl = 0)
                            index in 13..15 -> TableDayV1(spT0 = 37.3f, spT1 = 29.0f, spRh = 53, spFlp = 5, spTr = 1, spCl = 0)
                            index in 16..17 -> TableDayV1(spT0 = 37.2f, spT1 = 28.0f, spRh = 47, spFlp = 15, spTr = 0, spCl = 0)
                            index in 18..19 -> TableDayV1(spT0 = 37.0f, spT1 = 34.0f, spRh = 80, spFlp = 20, spTr = 0, spCl = 0)
                            else -> TableDayV1(spT0 = 28.0f, spT1 = 20.0f, spRh = 45, spFlp = 90, spTr = 0, spCl = 0)
                        }
                    }
                )
            ),
            ProgramPreset(
                id = "duck2",
                name = { "Duck2" },
                description = { "Optimized settings for duck2 eggs incubation." },
                table = TablePacketV1(
                    days = List(30) { index ->
                        when {
                            index in 1..6 -> TableDayV1(spT0 = 37.8f, spT1 = 30.0f, spRh = 58, spFlp = 0, spTr = 1, spCl = 0)
                            index in 7..12 -> TableDayV1(spT0 = 37.5f, spT1 = 29.0f, spRh = 53, spFlp = 15, spTr = 1, spCl = 0)
                            index in 13..26 -> TableDayV1(spT0 = 37.2f, spT1 = 28.8f, spRh = 47, spFlp = 25, spTr = 1, spCl = 0)
                            index in 27..28 -> TableDayV1(spT0 = 37.0f, spT1 = 32.0f, spRh = 80, spFlp = 35, spTr = 0, spCl = 0)
                            else -> TableDayV1(spT0 = 28.0f, spT1 = 20.0f, spRh = 45, spFlp = 90, spTr = 0, spCl = 0)
                        }
                    }
                )
            ),
        )
    }
}
