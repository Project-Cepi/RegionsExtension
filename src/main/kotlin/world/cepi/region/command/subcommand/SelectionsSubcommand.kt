package world.cepi.region.command.subcommand

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.coordinate.Vec
import net.minestom.server.utils.location.RelativeVec
import world.cepi.kepi.messages.sendFormattedTranslatableMessage
import world.cepi.kstom.command.arguments.literal
import world.cepi.kstom.command.kommand.Kommand
import world.cepi.region.Selection
import world.cepi.region.command.*
import java.text.DecimalFormat
import java.util.*

object SelectionsSubcommand : Kommand({

    val numberFormat = DecimalFormat("0.#")

    val index = ArgumentType.Integer("index").min(0)
    val position = ArgumentType.RelativeBlockPosition("position")
        .setDefaultValue { RelativeVec(Vec.ZERO, RelativeVec.CoordinateType.RELATIVE, true, true, true) }

    val add by literal
    val remove by literal
    val pos1 by literal
    val pos2 by literal

    onlyPlayers()

    playerCallbackFailMessage = { sender ->
        sender.sendFormattedTranslatableMessage("common", "command.only_players")
    }

    // TODO: Translations
    syntax(pos1, position) {
        Selection.pos1(player, context[position].from(player))

        player.sendFormattedTranslatableMessage("regions", "position.set.1")
    }

    syntax(pos2, position) {
        Selection.pos2(player, context[position].from(player))

        player.sendFormattedTranslatableMessage("regions", "position.set.2")
    }

    syntax(RegionArguments.existingRegion, add) {
        Selection.selections as MutableMap

        if (!Selection.selections.contains(player.uuid)) {
            sender.sendFormattedTranslatableMessage("regions", "selection.none")
        } else {
            val region = context.get(RegionArguments.existingRegion)

            region.addSelection(Selection.selections[player.uuid]!!)
            Selection.selections.remove(player.uuid)

            sender.sendFormattedTranslatableMessage("regions", "selection.add")
        }
    }

    syntax(RegionArguments.existingRegion, remove, index) {
        val region = context.get(RegionArguments.existingRegion)
        val currentIndex = context.get(index)

        if (currentIndex >= 0 && currentIndex < region.selections.size) {
            region.removeSelection(region.selections[currentIndex])
            sender.sendFormattedTranslatableMessage("regions", "selection.remove")
        } else {
            sender.sendFormattedTranslatableMessage("regions", "selection.none")
        }
    }

    syntax(RegionArguments.existingRegion, RegionArguments.list) {
        val region = context.get(RegionArguments.existingRegion)

        if (region.selections.isEmpty()) {
            sender.sendMessage("No selections found")
            return@syntax
        }

        region.selections.forEachIndexed { index, selection ->

            val pos1Array = arrayOf(
                selection.pos1.x(),
                selection.pos1.y(),
                selection.pos1.z()
            )

            val pos1String = pos1Array.joinToString(", ") { numberFormat.format(it) }

            val pos2Array = arrayOf(
                selection.pos2.x(),
                selection.pos2.y(),
                selection.pos2.z()
            )

            val pos2String = pos2Array.joinToString(", ") { numberFormat.format(it) }

            sender.sendMessage(
                Component.text(index + 1, NamedTextColor.GRAY)
                    .append(Component.text(": ", NamedTextColor.GRAY))
                    .append(Component.text("($pos1String)", TextColor.color(94, 173, 101))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to teleport!")))
                        .clickEvent(ClickEvent.runCommand("/tp ${pos1Array.joinToString(" ")}")))
                    .append(Component.text(", ", NamedTextColor.GRAY))
                    .append(Component.text("($pos2String)", TextColor.color(94, 173, 170))
                        .clickEvent(ClickEvent.runCommand("/tp ${pos2Array.joinToString(" ")}")))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to teleport!")))
            )
        }
    }

}, "selections")