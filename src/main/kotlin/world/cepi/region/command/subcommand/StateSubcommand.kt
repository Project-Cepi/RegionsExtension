package world.cepi.region.command.subcommand

import world.cepi.kstom.command.arguments.literal
import world.cepi.kstom.command.kommand.Kommand
import world.cepi.region.api.RegionState
import world.cepi.region.command.RegionArguments

object StateSubcommand : Kommand({
    val add by literal

    syntax(RegionArguments.existingRegion, add) {
        val region = (!RegionArguments.existingRegion)
        region.states.add(RegionState.from(region))
    }
}, "state")