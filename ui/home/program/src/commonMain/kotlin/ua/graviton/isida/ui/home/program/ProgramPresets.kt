package ua.graviton.isida.ui.home.program

import ua.graviton.isida.data.protocol.packets.TablePacket
import ua.graviton.isida.data.protocol.packets.v1.TableDayV1
import ua.graviton.isida.data.protocol.packets.v1.TablePacketV1

data class ProgramPreset(
    val id: String,
    val name: String,
    val description: String,
    val table: TablePacket,
) {
    companion object {
        val ALL = listOf(
            ProgramPreset(
                id = "universal",
                name = "Universal",
                description = "General purpose incubation settings suitable for most common birds.",
                table = TablePacketV1(
                    days = List(30) { 
                        TableDayV1(spT0 = 37.8f, spT1 = 28.5f, spRh = 55, spFlp = 0, spTr = 1, spCl = 0)
                    }
                )
            ),
            ProgramPreset(
                id = "chicken",
                name = "Chicken",
                description = "Optimized settings for chicken eggs incubation.",
                table = TablePacketV1(
                    days = List(30) { index ->
                        when {
                            index < 18 -> TableDayV1(spT0 = 37.8f, spT1 = 28.5f, spRh = 55, spFlp = 0, spTr = 1, spCl = 0)
                            else -> TableDayV1(spT0 = 37.5f, spT1 = 29.0f, spRh = 65, spFlp = 1, spTr = 0, spCl = 1)
                        }
                    }
                )
            ),
            ProgramPreset(
                id = "straus",
                name = "Straus",
                description = "Optimized settings for straus eggs incubation.",
                table = TablePacketV1(
                    days = List(30) { index ->
                        when {
                            index < 18 -> TableDayV1(spT0 = 2f, spT1 = 28.5f, spRh = 55, spFlp = 0, spTr = 1, spCl = 0)
                            else -> TableDayV1(spT0 = 1f, spT1 = 29.0f, spRh = 65, spFlp = 1, spTr = 0, spCl = 1)
                        }
                    }
                )
            ),
        )
    }
}
