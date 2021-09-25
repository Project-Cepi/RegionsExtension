package world.cepi.region.command

import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.command.builder.exception.ArgumentSyntaxException
import world.cepi.kstom.command.arguments.literal
import world.cepi.kstom.command.arguments.suggest
import world.cepi.region.api.RegionProvider

object RegionArguments {

    val list by literal

    val regionName = ArgumentType.Word("name").map { name ->
        val region = RegionProvider[name]
        if(region != null) throw ArgumentSyntaxException("Region exists", name, 1)
        name
    }

    val existingRegion = ArgumentType.Word("region").map { name ->
        RegionProvider[name]?: throw ArgumentSyntaxException("Invalid region", name, 1)
    }.suggest {
        RegionProvider.regions.values
            .map { it.name }
    }

}