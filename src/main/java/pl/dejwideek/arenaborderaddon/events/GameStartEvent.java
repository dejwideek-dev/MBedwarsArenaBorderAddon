package pl.dejwideek.arenaborderaddon.events;

import de.marcely.bedwars.api.arena.Arena;
import de.marcely.bedwars.api.arena.RegenerationType;
import de.marcely.bedwars.api.event.arena.RoundStartEvent;
import dev.dejvokep.boostedyaml.YamlDocument;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.dejwideek.arenaborderaddon.ArenaBorderPlugin;
import pl.dejwideek.arenaborderaddon.configs.Config;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class GameStartEvent implements Listener {

    private static ArenaBorderPlugin plugin;

    public GameStartEvent(ArenaBorderPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGameStart(RoundStartEvent e) {

        Config config = plugin.config;
        YamlDocument arenasConfig = plugin.arenasConfig;

        Arena arena = e.getArena();
        RegenerationType regenerationType = arena.getRegenerationType();
        ArrayList<String> disabledArenas = new ArrayList<>();

        for(String s : config.DISABLED_ARENAS) {
            disabledArenas.add(s);
        }

        for(String s : disabledArenas) {
            if(!arena.getName().equals(s)) {
                if(regenerationType.equals(RegenerationType.WORLD)) {
                    World world = arena.getGameWorld();

                    double centerLocationX = arenasConfig
                            .getDouble("arenas."
                                    + arena.getName() + ".center-location.x");
                    double centerLocationZ = arenasConfig
                            .getDouble("arenas."
                                    + arena.getName() + ".center-location.z");
                    double borderSize = arenasConfig
                            .getDouble("arenas."
                                    + arena.getName() + ".size");
                    double defaultCenterLocationX = config.BORDER.DEFAULT.CENTER_LOCATION.X;
                    double defaultCenterLocationZ = config.BORDER.DEFAULT.CENTER_LOCATION.Z;
                    double defaultBorderSize = config.BORDER.DEFAULT.SIZE;

                    if(arenasConfig.getSection("arenas."
                            + arena.getName()) != null) {
                        world.getWorldBorder().setCenter(centerLocationX, centerLocationZ);
                        world.getWorldBorder().setSize(borderSize);
                    }
                    else {
                        world.getWorldBorder().setCenter(defaultCenterLocationX,
                                defaultCenterLocationZ);
                        world.getWorldBorder().setSize(defaultBorderSize);
                    }
                }
                else return;
            }
            else return;
        }
        return;
    }
}
