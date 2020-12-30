package world.cepi.region;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface Region {

    /**
     * @return The unique name of this region.
     */
    @NotNull
    String getName();

    /**
     * @return The {@link RegionPool} in which this region
     * resided in. Or null, if this region has been removed.
     */
    @Nullable
    RegionPool getPool();

    /**
     * @return An unmodifiable collection of worlds (Minestom {@link Instance}s),
     * that contain at least some part of this region.
     */
    @NotNull
    Collection<Instance> getWorlds();

    /**
     * @return True, if this region contains at least one block.
     * (Has any size.) False otherwise.
     */
    boolean isDefined();

    /**
     * @return The volume of this region in blocks.
     */
    int getVolume();

    /**
     * Creates a modifiable collection of all the players that are
     * currently inside this region.
     *
     * @return Collection of players inside this region
     */
    @NotNull
    Collection<Player> getPlayers();

    /**
     * Creates a modifiable collection of all the entities that are
     * currently inside this region.
     *
     * @return Collection of entities inside this region
     */
    @NotNull
    Collection<Entity> getEntities();

    /**
     * Creates a modifiable collection of all the entities which type
     * matches at least one of the given types, and that are currently
     * inside this region.
     *
     * @param types The given entity types. Null not allowed as an member.
     *
     * @return Collection of entities inside this region with a given type
     */
    @NotNull
    Collection<Entity> getEntities(@NotNull EntityType... types);

}
