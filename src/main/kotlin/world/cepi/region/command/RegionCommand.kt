package world.cepi.region.command

import net.minestom.server.chat.ChatColor
import net.minestom.server.command.CommandProcessor
import net.minestom.server.command.CommandSender
import net.minestom.server.entity.Player
import world.cepi.region.Region
import world.cepi.region.RegionPool
import world.cepi.region.RegionProvider
import world.cepi.region.cepiregions.CepiRegion
import world.cepi.region.cepiregions.CepiRegionProvider

class RegionCommand(private val provider: RegionProvider) : CommandProcessor {

    override fun getCommandName(): String {
        return "region"
    }

    override fun getAliases(): Array<String>? {
        return null
    }

    override fun hasAccess(player: Player): Boolean {
        return true
    }

    override fun enableWritingTracking(): Boolean {
        return true
    }

    override fun process(sender: CommandSender, command: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage("Usage:"
                    + "\n  /region create <pool name> <region name>"
                    + "\n   Creates a new region in a given regionpool."
                    + "\n  /region delete <pool name> <region name>"
                    + "\n   Deletes a region in a given regionpool."
                    + "\n  /region wand"
                    + "\n   Gets the item that is used to make block"
                    + "\n   selections."
                    + "\n  /region addblocks <pool name> <region name>"
                    + "\n   Adds the selected blocks to the region in the"
                    + "\b   given regionpool."
                    + "\n  /region removeblocks <pool name> <region name>"
                    + "\n   Removes the selected blocks from the region in"
                    + "\n   the given regionpool."
                    + "\n  /region list [<pool name>]"
                    + "\n   Lists all the regions in the given regionpool,"
                    + "\n   or all the pools if argument omitted."
                    + "\n  /region show <pool name> <region name>"
                    + "\n   Visually show the region in the given regionpool."
                    + "\n  /region createpool <pool name>"
                    + "\n   Creates a new regionpool."
                    + "\n  /region deletepool <pool name>"
                    + "\n   Deletes a regionpool."

                +  "\nImplementation: ${provider.getImplementationName()} RegionAPI: ${provider.getVersion()}"
            )
            return true
        }

        if (args[0] == "create") {
            if (args.size != 3) {
                sender.sendMessage("Usage: /region create <pool name> <region name>")
                return true
            }

            val pool = provider.getPool(args[1])

            if (pool == null) {
                sender.sendMessage(
                    "${ChatColor.RED}Region pool doesn't exist in implementation " +
                            "${provider.getImplementationName()}: ${args[1]}"
                )
                return true
            }

            if (pool.getRegion(args[2]) != null) {
                sender.sendMessage("${ChatColor.RED}Region already exists in pool ${pool.getName()}: ${args[1]}")
                return true
            }

            val region: Region

            try {
                region = pool.createRegion(args[2])
            } catch (e: Exception) {
                sender.sendMessage(
                    "${ChatColor.RED}Error while attempting to create region in pool ${pool.getName()}: ${e.message}"
                )
                return true
            }

            sender.sendMessage("Region created in pool ${pool.getName()}: ${region.getName()}")
            return true
        }
        else if (args[0] == "delete") {
            if (args.size != 3) {
                sender.sendMessage("Usage: /region delete <pool name> <region name>")
                return true
            }

            val pool = provider.getPool(args[1])

            if (pool == null) {
                sender.sendMessage(
                    "${ChatColor.RED}Region pool doesn't exist in implementation " +
                        "${provider.getImplementationName()}: ${args[1]}"
                )
                return true
            }

            val region = pool.getRegion(args[2])

            if (region == null) {
                sender.sendMessage("${ChatColor.RED}Region doesn't exist in pool ${pool.getName()}: ${args[2]}")
                return true
            }

            try {
                pool.remove(region)
            } catch (e: Exception) {
                sender.sendMessage(
                    "${ChatColor.RED}Error while attempting to delete region in pool ${pool.getName()}: ${e.message}"
                )
                return true
            }

            sender.sendMessage("Region deleted from pool ${pool.getName()}: ${region.getName()}")
            return true
        }
        else if (args[0] == "wand") {
            // TODO: Implement
        }
        else if (args[0] == "addblocks") {
            // TODO: Implement
        }
        else if (args[0] == "removeblocks") {
            // TODO: Implement
        }
        else if (args[0] == "list") {
            if (args.size == 1) {
                val pools = provider.getPools()

                var list = ""

                for (pool in pools) {
                    list = list + ", " + pool.getName()
                }

                list = list.replaceFirst(", ", "")

                sender.sendMessage(
                    "Total of ${pools.size} region pool${if (pools.size == 1) "s" else ""} in implementation " +
                            "${provider.getImplementationName()}: $list"
                )
                return true
            }
            else if (args.size == 2) {
                val pool = provider.getPool(args[1])

                if (pool == null) {
                    sender.sendMessage(
                        "${ChatColor.RED}Region pool doesn't exist in implementation " +
                                "${provider.getImplementationName()}: ${args[1]}"
                    )
                    return true
                }

                var list = ""

                for (pool in pools) {
                    list = list + ", " + pool.getName()
                }

                list = list.replaceFirst(", ", "")

                sender.sendMessage(
                    "Total of ${pools.size} region${if (pools.size == 1) "s" else ""} in pool " +
                            "${pool.getName()}: $list"
                )
                return true
            }
            else {
                sender.sendMessage("Usage: /region list or /region list <pool name>")
                return true
            }
        }
        else if (args[0] == "show") {
            // TODO: Implement
        }
        else if (args[0] == "createpool") {
            if (args.size != 1) {
                sender.sendMessage("Usage: /region createpool <pool name>")
                return true
            }

            if (provider.getPool(args[1]) != null) {
                sender.sendMessage(
                    "${ChatColor.RED}Region pool already exists in implementation " +
                            "${provider.getImplementationName()}: ${args[1]}"
                )
                return true
            }

            val pool: RegionPool

            try {
                pool = provider.createPool(args[1])
            } catch (e: Exception) {
                sender.sendMessage(
                    "${ChatColor.RED}Error while attempting to create region pool in implementation " +
                            "${provider.getImplementationName()}: ${e.message}"
                )
                return true
            }

            sender.sendMessage(
                "Region pool created in implementation " +
                    "${provider.getImplementationName()}: ${pool.getName()}"
            )
            return true
        }
        else if (args[0] == "deletepool") {
            if (args.size != 1) {
                sender.sendMessage("Usage: /region createpool <pool name>")
                return true
            }

            val pool = provider.getPool(args[1])

            if (pool == null) {
                sender.sendMessage(
                    "${ChatColor.RED}Region pool doesn't exist in implementation " +
                            "${provider.getImplementationName()}: ${args[1]}"
                )
                return true
            }

            try {
                provider.removePool(pool)
            } catch (e: Exception) {
                sender.sendMessage(
                    "${ChatColor.RED}Error while attempting to delete region pool from implementation " +
                            "${provider.getImplementationName()}: ${e.message}"
                )
                return true
            }

            sender.sendMessage(
                "Region pool deleted from implementation " +
                        "${provider.getImplementationName()}: ${pool.getName()}"
            )
            return true
        }

        return true
    }

}