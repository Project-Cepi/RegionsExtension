package world.cepi.region.command

import net.minestom.server.MinecraftServer
import net.minestom.server.chat.ChatColor
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.event.player.PlayerDisconnectEvent
import net.minestom.server.utils.BlockPosition
import world.cepi.kstom.command.arguments.asSubcommand
import world.cepi.kstom.command.addSyntax
import world.cepi.kstom.command.setArgumentCallback
import world.cepi.region.RegionProvider
import world.cepi.region.Selection
import java.util.*

class RegionCommand(val provider: RegionProvider) : Command("region") {

    private val selectedPositions = HashMap<CommandSender, Selection>()

    init {
        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerDisconnectEvent::class.java) {
            selectedPositions.remove(it.player)
        }

        val create = "create".asSubcommand()
        val delete = "delete".asSubcommand()

        val pos1 = "pos1".asSubcommand()
        val pos2 = "pos2".asSubcommand()

        val blocks = "blocks".asSubcommand()
        val add = "add".asSubcommand()
        val remove = "remove".asSubcommand()

        val list = "list".asSubcommand()

        val show = "show".asSubcommand()

        val pool = "pool".asSubcommand()

        val poolName = ArgumentType.DynamicWord("poolName").fromRestrictions { provider.pools.any { pool -> pool.name == it } }

        setArgumentCallback(poolName) { sender, exception ->
            sender.sendMessage("${ChatColor.RED}Region pool doesn't exist in implementation ${provider.implementationName}: ${exception.input}")
        }

        val regionName = ArgumentType.DynamicWord("regionName").fromRestrictions { input -> provider.pools.map { it.regions }.flatten().any { it.name == input } }
        val newRegionName = ArgumentType.String("newRegionName")

        // TODO kotlin """
        setDefaultExecutor { sender, args ->
            sender.sendMessage("Usage:"
                    + "\n  /$name create <pool name> <region name>"
                    + "\n   Creates a new region in a given regionpool."
                    + "\n  /$name delete <pool name> <region name>"
                    + "\n   Deletes a region in a given regionpool."
                    + "\n  /$name pos1 [<coordinates>]"
                    + "\n   Sets/gets the first position for making a selection."
                    + "\n  /$name pos2 [<coordinates>]"
                    + "\n   Sets/gets the second position for making a selection."
                    + "\n  /$name addblocks <pool name> <region name> [<world uuid>]"
                    + "\n   Adds the selected blocks to the region in the"
                    + "\b   given regionpool."
                    + "\n  /$name removeblocks <pool name> <region name> [<world uuid>]"
                    + "\n   Removes the selected blocks from the region in"
                    + "\n   the given regionpool."
                    + "\n  /$name list [<pool name>]"
                    + "\n   Lists all the regions in the given regionpool,"
                    + "\n   or all the pools if argument omitted."
                    + "\n  /$name show <pool name> <region name>"
                    + "\n   Visually show the region in the given regionpool."
                    + "\n  /$name createpool <pool name>"
                    + "\n   Creates a new regionpool."
                    + "\n  /$name deletepool <pool name>"
                    + "\n   Deletes a regionpool."

                    +  "\nImplementation: ${provider.implementationName} RegionAPI: ${provider.version}"
            )
        }

        addSyntax(create, poolName, newRegionName) { sender, args ->
            val poolObect = provider[args.get(poolName)]
        }

        addSyntax(delete, poolName, regionName) { sender, args ->

        }

        addSyntax(pos1) { sender ->

        }

        addSyntax(pos2) { sender ->

        }

        addSyntax(blocks, add) { sender, args ->

        }

        addSyntax(blocks, remove) { sender, args ->

        }

        addSyntax(pool, create) { sender, args ->

        }

        addSyntax(pool, delete) { sender, args ->

        }

        addSyntax(list) { sender ->

        }

        addSyntax(show) { sender ->

        }

    }
}

//class OldRegionCommand(private val provider: RegionProvider) : CommandProcessor {
//
//    private val selectedPositions = HashMap<CommandSender, Selection>()
//
//    override fun process(sender: CommandSender, command: String, args: Array<out String>): Boolean {
//        if (args[0] == "create") {
//            if (args.size != 3) {
//                sender.sendMessage("Usage: /$commandName create <pool name> <region name>")
//                return true
//            }
//
//            val pool = provider[args[1]]
//
//            if (pool == null) {
//                sender.sendMessage(
//                    "${ChatColor.RED}Region pool doesn't exist in implementation " +
//                            "${provider.implementationName}: ${args[1]}"
//                )
//                return true
//            }
//
//            if (pool[args[2]] != null) {
//                sender.sendMessage("${ChatColor.RED}Region already exists in pool ${pool.name}: ${args[1]}")
//                return true
//            }
//
//            val region: Region
//
//            try {
//                region = pool.createRegion(args[2])
//            } catch (e: Exception) {
//                sender.sendMessage(
//                    "${ChatColor.RED}Error while attempting to create region in pool ${pool.name}: ${e.message}"
//                )
//                return true
//            }
//
//            sender.sendMessage("Region created in pool ${pool.name}: ${region.name}")
//            return true
//        }
//        else if (args[0] == "delete") {
//            if (args.size != 3) {
//                sender.sendMessage("Usage: /$commandName delete <pool name> <region name>")
//                return true
//            }
//
//            val pool = provider[args[1]]
//
//            if (pool == null) {
//                sender.sendMessage(
//                    "${ChatColor.RED}Region pool doesn't exist in implementation " +
//                        "${provider.implementationName}: ${args[1]}"
//                )
//                return true
//            }
//
//            val region = pool[args[2]]
//
//            if (region == null) {
//                sender.sendMessage("${ChatColor.RED}Region doesn't exist in pool ${pool.name}: ${args[2]}")
//                return true
//            }
//
//            try {
//                pool.remove(region)
//            } catch (e: Exception) {
//                sender.sendMessage(
//                    "${ChatColor.RED}Error while attempting to delete region in pool ${pool.name}: ${e.message}"
//                )
//                return true
//            }
//
//            sender.sendMessage("Region deleted from pool ${pool.name}: ${region.name}")
//            return true
//        }
//        else if (args[0] == "pos1" || args[1] == "pos2") {
//            val pos = if (args[0] == "pos1") 1 else 2
//            val position: BlockPosition
//
//            if (args.size == 1) {
//                if (sender !is Player) {
//                    sender.sendMessage("${ChatColor.RED}Only players can use interactive coordinates." +
//                            "\nUse /$commandName pos$pos <coordinates>, instead.")
//                    return true
//                }
//
//                val world = sender.instance
//                if (world == null) {
//                    sender.sendMessage("")
//                    return true
//                }
//
//                val iterator = BlockIterator(sender, 100)
//                var result: BlockPosition? = null
//
//                while (iterator.hasNext()) {
//                    val next = iterator.next()
//                    if (!Block.fromStateId(world.getBlockStateId(next)).isAir) {
//                        result = next
//                        break
//                    }
//                }
//
//                if (result == null) {
//                    sender.sendMessage("${ChatColor.RED}No block in sight")
//                    return true
//                }
//
//                position = result
//
//            }
//            else {
//                if (args.size != 4) {
//                    sender.sendMessage("Usage: /$commandName pos$pos [<x> <y> <z>]")
//                    return true
//                }
//
//                val x: Int
//                val y: Int
//                val z: Int
//
//                try {
//                    x = Integer.parseInt(args[1])
//                    y = Integer.parseInt(args[2])
//                    z = Integer.parseInt(args[3])
//                } catch (e: NumberFormatException) {
//                    sender.sendMessage("${ChatColor.RED}Invalid coordinates: ${args[1]} ${args[2]} ${args[3]}")
//                    return true
//                }
//
//                position = BlockPosition(x, y, z)
//
//                // TODO: Support for both absolutes and relatives
//                return true
//            }
//
//            val selection = selectedPositions.getOrDefault(sender, Selection())
//            if (pos == 1) selection.pos1 = position
//            else selection.pos2 = position
//            sender.sendMessage("Selected pos$pos (${position.x}, ${position.y}, ${position.z})")
//            if (selection.pos1 != null && selection.pos2 != null) {
//                val width = abs(selection.pos1!!.x - selection.pos2!!.x)
//                val height = abs(selection.pos1!!.y - selection.pos2!!.y)
//                val depth = abs(selection.pos1!!.z - selection.pos2!!.z)
//                sender.sendMessage("Selection size: ${width * height * depth} blocks")
//            }
//        }
//        else if (args[0] == "addblocks" || args[0] == "removeblocks") {
//            if (args.size != 3 && args.size != 4) {
//                sender.sendMessage("Usage: /$commandName ${args[0]} <pool name> <region name> [<world uuid>]")
//                return true
//            }
//
//            val pool = provider[args[1]]
//
//            if (pool == null) {
//                sender.sendMessage(
//                    "${ChatColor.RED}Region pool doesn't exist in implementation " +
//                            "${provider.implementationName}: ${args[1]}"
//                )
//                return true
//            }
//
//            val region = pool[args[2]]
//
//            if (region == null) {
//                sender.sendMessage("${ChatColor.RED}Region doesn't exist in pool ${pool.name}: ${args[2]}")
//                return true
//            }
//
//            val selection = selectedPositions[sender]
//
//            if (selection?.pos1 == null || selection.pos2 == null) {
//                sender.sendMessage("${ChatColor.RED}Please make a selection first, using /$commandName pos1/pos2.")
//                return true
//            }
//
//            var world: Instance? = null
//
//            if (args.size == 4) {
//                for (w in MinecraftServer.getInstanceManager().instances) {
//                    if (w.uniqueId.toString() == args[3]) {
//                        world = w
//                        break
//                    }
//                }
//
//                if (world == null) {
//                    sender.sendMessage("${ChatColor.RED}World '${args[3]}' could not be found.")
//                    return true
//                }
//            }
//            else if (sender is Entity && sender.instance != null) {
//                world = sender.instance!!
//            }
//            else {
//                sender.sendMessage("${ChatColor.RED}Could not find out the world were you are in. " +
//                        "Please use /$commandName ${args[0]} <world uuid>")
//                return true
//            }
//
//            if (args[0] == "addblocks") {
//                val result = region.addBlocks(selection.pos1!!, selection.pos2!!, world)
//                sender.sendMessage("$result block${if (result != 1) "s" else ""} " +
//                        "were added to region ${region.name} in pool ${pool.name}.")
//            }
//            else if (args[0] == "removeblocks") {
//                val result = region.removeBlocks(selection.pos1!!, selection.pos2!!, world)
//                sender.sendMessage("$result block${if (result != 1) "s" else ""} " +
//                        "were removed from region ${region.name} in pool ${pool.name}.")
//            }
//        }
//        else if (args[0] == "list") {
//            if (args.size == 1) {
//                val pools = provider.pools
//
//                var list = ""
//
//                for (pool in pools) {
//                    list = list + ", " + pool.name
//                }
//
//                list = list.replaceFirst(", ", "")
//
//                sender.sendMessage(
//                    "Total of ${pools.size} region pool${if (pools.size == 1) "s" else ""} in implementation " +
//                            "${provider.implementationName}: $list"
//                )
//                return true
//            }
//            else if (args.size == 2) {
//                val pool = provider[args[1]]
//
//                if (pool == null) {
//                    sender.sendMessage(
//                        "${ChatColor.RED}Region pool doesn't exist in implementation " +
//                                "${provider.implementationName}: ${args[1]}"
//                    )
//                    return true
//                }
//
//                var list = ""
//
//                for (region in pool.regions) {
//                    list = list + ", " + pool.name
//                }
//
//                list = list.replaceFirst(", ", "")
//
//                sender.sendMessage(
//                    "Total of ${pool.size} region${if (pool.size == 1) "s" else ""} in pool " +
//                            "${pool.name}: $list"
//                )
//                return true
//            }
//            else {
//                sender.sendMessage("Usage: /$commandName list or /$commandName list <pool name>")
//                return true
//            }
//        }
//        else if (args[0] == "show") {
//            if (sender !is Player) {
//                sender.sendMessage("${ChatColor.RED}This subcommand is player-only.")
//                return true
//            }
//
//            if (args.size != 3 && args.size != 4) {
//                sender.sendMessage("Usage: /$commandName ${args[0]} <pool name> <region name> [<range>]")
//                return true
//            }
//
//            val pool = provider[args[1]]
//
//            if (pool == null) {
//                sender.sendMessage(
//                    "${ChatColor.RED}Region pool doesn't exist in implementation " +
//                            "${provider.implementationName}: ${args[1]}"
//                )
//                return true
//            }
//
//            val region = pool[args[2]]
//
//            if (region == null) {
//                sender.sendMessage("${ChatColor.RED}Region doesn't exist in pool ${pool.name}: ${args[2]}")
//                return true
//            }
//
//            val range: Int
//
//            if (args.size == 4) {
//                try {
//                    range = Integer.parseInt(args[3])
//                } catch (e: NumberFormatException) {
//                    sender.sendMessage("${ChatColor.RED}Invalid number ${args[3]}.")
//                    return true
//                }
//
//                if (range < 1) {
//                    sender.sendMessage("${ChatColor.RED}Range must be at least one.")
//                    return true
//                }
//            }
//            else range = 10
//
//            val world = sender.instance
//
//            if (world == null) {
//                sender.sendMessage("${ChatColor.RED}Your world could not be determined.")
//                return true
//            }
//
//            // ??? Huh? !!!! Wha?
//            val chunkX: Int = sender.chunk?.chunkX!!
//            val chunkZ: Int = sender.chunk?.chunkZ!!
//
//            TODO("Not implemented yet")
//
//            for (x in -range .. range) {
//                for (z in -range .. range) {
//                    val iterator = region.iterateChunk(chunkX + x, chunkZ + z, world)
//
//                    // TODO: Highlight all the blocks that are produced by the iterator.
//                }
//            }
//
//            return true
//        }
//        else if (args[0] == "createpool") {
//            if (args.size != 2) {
//                sender.sendMessage("Usage: /$commandName createpool <pool name>")
//                return true
//            }
//
//            if (provider[args[1]] != null) {
//                sender.sendMessage(
//                    "${ChatColor.RED}Region pool already exists in implementation " +
//                            "${provider.implementationName}: ${args[1]}"
//                )
//                return true
//            }
//
//            val pool: RegionPool
//
//            try {
//                pool = provider.createPool(args[1])
//            } catch (e: Exception) {
//                sender.sendMessage(
//                    "${ChatColor.RED}Error while attempting to create region pool in implementation " +
//                            "${provider.implementationName}: ${e.message}"
//                )
//                return true
//            }
//
//            sender.sendMessage(
//                "Region pool created in implementation " +
//                    "${provider.implementationName}: ${pool.name}"
//            )
//            return true
//        }
//        else if (args[0] == "deletepool") {
//            if (args.size != 2) {
//                sender.sendMessage("Usage: /$commandName deletepool <pool name>")
//                return true
//            }
//
//            val pool = provider[args[1]]
//
//            if (pool == null) {
//                sender.sendMessage(
//                    "${ChatColor.RED}Region pool doesn't exist in implementation " +
//                            "${provider.implementationName}: ${args[1]}"
//                )
//                return true
//            }
//
//            try {
//                provider.removePool(pool)
//            } catch (e: Exception) {
//                sender.sendMessage(
//                    "${ChatColor.RED}Error while attempting to delete region pool from implementation " +
//                            "${provider.implementationName}: ${e.message}"
//                )
//                return true
//            }
//
//            sender.sendMessage(
//                "Region pool deleted from implementation " +
//                        "${provider.implementationName}: ${pool.name}"
//            )
//            return true
//        }
//
//        return true
//    }
//
//}