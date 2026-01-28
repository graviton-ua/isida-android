package ua.graviton.isida.data.protocol.commands

sealed interface IsidaCommand {
    interface V1 : IsidaCommand

    // As example for the future changes
    interface V2 : IsidaCommand
}