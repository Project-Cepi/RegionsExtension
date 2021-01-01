package world.cepi.region.cepiregions.command

import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentDynamicWord
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.command.builder.arguments.ArgumentWord
import net.minestom.server.command.builder.arguments.minecraft.SuggestionType

class RegionCommand : Command("region") {

    init {
        setDefaultExecutor { sender, _ ->
            sender.sendMessage("Usage: /region <create/delete/addblocks/removeblock/list/show>")
        }

        val subcommand = ArgumentWord("subcommand")
            .from("create", "delete", "addblocks", "removeblocks", "list", "show")

        val regionName = ArgumentDynamicWord("regionname", SuggestionType.ASK_SERVER)
            .fromRestrictions { true /* TODO: Validate here */ }



    }

}