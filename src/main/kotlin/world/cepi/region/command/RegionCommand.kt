package world.cepi.region.command

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.command.builder.exception.ArgumentSyntaxException
import net.minestom.server.entity.Player
import world.cepi.kepi.command.subcommand.applyHelp
import world.cepi.kepi.messages.sendFormattedTranslatableMessage
import world.cepi.kstom.Manager
import world.cepi.kstom.command.arguments.literal
import world.cepi.kstom.command.arguments.suggest
import world.cepi.kstom.command.kommand.Kommand
import world.cepi.mob.mob.mobEgg
import world.cepi.mob.util.MobUtils
import world.cepi.particle.NoData
import world.cepi.particle.Particle
import world.cepi.particle.ParticleType
import world.cepi.particle.data.OffsetAndSpeed
import world.cepi.particle.renderer.Renderer
import world.cepi.region.api.RegionProvider
import world.cepi.region.command.RegionArguments.existingRegion
import world.cepi.region.command.RegionArguments.list
import world.cepi.region.command.RegionArguments.regionName
import world.cepi.region.command.subcommand.*

object RegionCommand : Kommand({
    // Find some instance as a default value.
    // The default value would be rarely used as if a command is sent by a player then the instance in which the
    // player is in will be used, and if it is sent by a console then the UUID should be specified.
    val world = ArgumentType.UUID("world").map { world ->
        Manager.instance.getInstance(world)?: throw ArgumentSyntaxException("Invalid world", world.toString(), 1)
    }.setDefaultValue { MinecraftServer.getInstanceManager().instances.firstOrNull() }

    val create by literal
    val delete by literal
    val show by literal

    val spawn by literal
    val amount = ArgumentType.Integer("amount").min(1).max(100)

    argumentCallback(regionName) {
        sender.sendFormattedTranslatableMessage("regions", "exists.yes")
    }

    argumentCallback(existingRegion) {
        sender.sendFormattedTranslatableMessage("regions", "exists.no")
    }

    argumentCallback(world) {
        sender.sendFormattedTranslatableMessage("common", "world.none")
    }

    syntax(spawn, existingRegion, amount).onlyPlayers {
        if (!MobUtils.hasMobEgg(sender)) return@onlyPlayers

        val mob = player.mobEgg ?: return@onlyPlayers

        repeat(!amount) {
            mob.spawnMob(player.instance!!, (!existingRegion).selections.random().random())
        }
    }

    syntax(create, regionName, world) {
        val instance = if (sender is Player) {
            (sender as Player).instance!!
        } else {
            context.get(world)
        }

        RegionProvider.createRegion(context.get(regionName), instance)
        sender.sendFormattedTranslatableMessage("regions", "create")
    }

    syntax(delete, existingRegion) {
        RegionProvider.remove(context.get(existingRegion).name)
        sender.sendFormattedTranslatableMessage("regions", "delete")
    }

    syntax(list) {
        val regions = RegionProvider.regions.values
        sender.sendFormattedTranslatableMessage("regions", "list", Component.text(regions.joinToString { it.name }, NamedTextColor.BLUE))
    }

    syntax(show, existingRegion) {
        (!existingRegion).selections.forEach {
            Renderer.fixedRectangle(it.pos1, it.pos2)
                .renderOnce(Particle.particle(ParticleType.FLAME, 1, OffsetAndSpeed(0f, 0f, 0f, 0f), NoData, true), player)
        }
    }

    applyHelp {
        """
            /region create <region name> [<world uuid>]
            Creates a new region.
            /region delete <region name>
            Deletes a region.
            /region selections pos1 [<coordinates>]
            Sets/gets the first position for making a selection.
            /region selections pos2 [<coordinates>]
            Sets/gets the second position for making a selection.
            /region selections <region name> add
            Adds the selected blocks to a given region.
            /region selections <region name> remove <index>
            Removes a selection from the region.
            /region selections <region name> list
            Lists all the selections in a given region.
            /region list
            Lists all of the registered regions.
            /region show <region name>
            Visually show a given region.
         """.trimIndent()
    }

    addSubcommands(SelectionsSubcommand, MetaSubcommand)

}, "region")