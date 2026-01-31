package ua.graviton.isida.data.protocol.commands

/**
 * Base interface for all commands sent to the ISIDA device.
 */
sealed interface IsidaCommand {
    /** Marker interface for Version 1 commands. */
    interface V1 : IsidaCommand

    // As example for the future changes
    /** Marker interface for Version 2 commands. */
    interface V2 : IsidaCommand
}