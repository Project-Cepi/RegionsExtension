package world.cepi.region.cepiregions.command

import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentDynamicWord
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.command.builder.arguments.ArgumentWord
import net.minestom.server.command.builder.arguments.minecraft.SuggestionType
import world.cepi.region.RegionProvider

class RegionCommand(private val provider: RegionProvider) : Command("region") {

    init {
        setDefaultExecutor { sender, _ ->
            sender.sendMessage("Usage:"
                          + "\n  /region create <pool name> <region name>"
                          + "\n   Creates a new region in a given regionpool."
                          + "\n  /region delete <pool name> <region name>"
                          + "\n   Deletes a region in a given regionpool."
                          + "\n  /region wand"
                          + "\n   Gets the item that is used to make block"
                          + "\n   selections."
                          + "\n  /region addBlocks <pool name> <region name>"
                          + "\n   Adds the selected blocks to the region in the"
                          + "\b   given regionpool."
                          + "\n  /region removeBlocks <pool name> <region name>"
                          + "\n   Removes the selected blocks from the region in"
                          + "\n   the given regionpool."
                          + "\n  /region list <pool name>"
                          + "\n   Lists all the regions in the given regionpool."
                          + "\n  /region show <pool name> <region name>"
                          + "\n   Visually show the region in the given regionpool."
                          + "\n  /region createPool <pool name>"
                          + "\n   Creates a new regionpool."
                          + "\n  /region deletePool <pool name>"
                          + "\n   Deletes a regionpool."
            )
        }

        val createSubcommand = ArgumentWord("createSubcommand").from("create")
        val deleteSubcommand = ArgumentWord("deleteSubcommand").from("delete")
        val addBlocksSubcommand = ArgumentWord("addBlocksSubcommand").from("addBlocks")
        val removeBlocksSubcommand = ArgumentWord("removeBlocksSubcommand").from("removeBlocks")
        val listSubcommand = ArgumentWord("listSubcommand").from("list")
        val showSubcommand = ArgumentWord("showSubcommand").from("show")

        val regionName = ArgumentDynamicWord("regionName", SuggestionType.ASK_SERVER)
            .fromRestrictions { true /* TODO: Validate here */ }

        addSyntax({sender, args ->



        }, createSubcommand, regionName)

    }

}