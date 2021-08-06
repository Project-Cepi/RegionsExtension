package world.cepi.region.command.subcommand

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import world.cepi.kepi.command.subcommand.KepiMetaSubcommand
import world.cepi.kepi.messages.sendFormattedMessage
import world.cepi.region.command.RegionCommand
import world.cepi.region.command.regionMetaSet
import world.cepi.region.meta.RegionMeta
import world.cepi.region.meta.RegionMetaCompanion
import kotlin.reflect.full.companionObjectInstance

internal object MetaSubcommand : KepiMetaSubcommand<RegionMeta>(
    RegionMeta::class,
    "meta",
    "meta",
    { instance, name ->
        val region = context[RegionCommand.existingRegion]

        instance.apply(region)

        sender.sendFormattedMessage(Component.text(regionMetaSet), Component.text(name, NamedTextColor.BLUE))
    },

    { clazz, name ->
        val region = context[RegionCommand.existingRegion]

        (clazz.companionObjectInstance as? RegionMetaCompanion)?.unapply(region)

        sender.sendFormattedMessage(Component.text(regionMetaSet), Component.text(name, NamedTextColor.BLUE))
    },
    RegionCommand.existingRegion
)