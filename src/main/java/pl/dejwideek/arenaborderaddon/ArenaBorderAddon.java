package pl.dejwideek.arenaborderaddon;

import co.aikar.commands.PaperCommandManager;
import de.marcely.bedwars.api.BedwarsAddon;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import pl.dejwideek.arenaborderaddon.commands.ReloadCmd;
import pl.dejwideek.arenaborderaddon.events.GameStartEvent;

@SuppressWarnings("ALL")
public class ArenaBorderAddon extends BedwarsAddon {
    private static ArenaBorderPlugin plugin;

    public ArenaBorderAddon(ArenaBorderPlugin plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return plugin.getName();
    }

    public void registerCommands() {
        PaperCommandManager manager =
                new PaperCommandManager(plugin);

        manager.registerCommand(new ReloadCmd(plugin));
    }

    public void registerEvents() {
        PluginManager manager = Bukkit.getPluginManager();

        manager.registerEvents(new GameStartEvent(plugin), plugin);
    }
}
