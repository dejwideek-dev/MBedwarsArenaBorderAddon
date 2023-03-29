package pl.dejwideek.arenaborderaddon.events;

import de.marcely.bedwars.api.arena.Arena;
import de.marcely.bedwars.api.arena.RegenerationType;
import de.marcely.bedwars.api.event.arena.RoundStartEvent;
import dev.dejvokep.boostedyaml.YamlDocument;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.dejwideek.arenaborderaddon.ArenaBorderPlugin;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class GameStartEvent implements Listener {

    private static ArenaBorderPlugin plugin;

    public GameStartEvent(ArenaBorderPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGameStart(RoundStartEvent e) {

        YamlDocument config = plugin.config;

        Arena arena = e.getArena();
        RegenerationType regenerationType = arena.getRegenerationType();
        ArrayList<String> disabledArenas = new ArrayList<>();

        for(String s : config.getStringList("disabled-arenas")) {
            disabledArenas.add(s);
        }

        for(String s : disabledArenas) {
            if(!arena.getName().equals(s)) {
                if(regenerationType.equals(RegenerationType.WORLD)) {
                    World world = arena.getGameWorld();

                    double centerLocationX = config
                            .getDouble("border.arenas."
                                    + arena.getName() + ".center-location.x");
                    double centerLocationZ = config
                            .getDouble("border.arenas."
                                    + arena.getName() + ".center-location.z");
                    double borderSize = config
                            .getDouble("border.arenas."
                                    + arena.getName() + ".size");
                    double defaultCenterLocationX = config
                            .getDouble("border.default.center-location.x");
                    double defaultCenterLocationZ = config
                            .getDouble("border.default.center-location.z");
                    double defaultBorderSize = config
                            .getDouble("border.default.size");

                    if(config.getSection("border.arenas."
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
