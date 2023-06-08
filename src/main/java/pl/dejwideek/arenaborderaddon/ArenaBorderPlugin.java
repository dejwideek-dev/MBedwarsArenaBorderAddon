package pl.dejwideek.arenaborderaddon;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import dev.dejvokep.boostedyaml.spigot.SpigotSerializer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.dejwideek.arenaborderaddon.configs.Config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@SuppressWarnings("ALL")
public class ArenaBorderPlugin extends JavaPlugin {

    private static File configFile;
    private static Path directory;
    public static Config config = Config.IMP;
    public YamlDocument arenasConfig;

    public void onEnable() {
        if(!mbwCheck()) return;
        if(!registerAddon()) return;

        this.directory = new ArenaBorderAddon(this)
                .getDataFolder().toPath();
        this.configFile = directory.resolve("config.yml").toFile();

        reloadConfig();
        loadCustomConfigs();
        new ArenaBorderAddon(this).registerCommands();
        new ArenaBorderAddon(this).registerEvents();
    }

    public void reloadConfig() {
        config.reload(configFile);
    }

    private void loadCustomConfigs() {
        try {
            arenasConfig = YamlDocument.create(
                    new File(directory.toFile(), "arenas.yml"),
                    getResource("arenas.yml"),
                    GeneralSettings.builder().setSerializer(
                            SpigotSerializer.getInstance()).build(),
                    LoaderSettings.DEFAULT,
                    DumperSettings.DEFAULT,
                    UpdaterSettings.DEFAULT);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean mbwCheck() {
        if(Bukkit.getPluginManager().getPlugin("MBedwars") != null) {
            final int supportedAPIVersion = 16;

            try {
                Class apiClass = Class.forName("de.marcely.bedwars.api.BedwarsAPI");
                int apiVersion = (int) apiClass.getMethod("getAPIVersion").invoke(null);

                if (apiVersion < supportedAPIVersion)
                    throw new IllegalStateException();
            } catch (Exception e) {
                this.getLogger().warning("Your MBedwars version is not supported. Supported version: 5.1.1 or higher!");
                Bukkit.getPluginManager().disablePlugin(this);
                return false;
            }
        }
        else return false;
        return true;
    }

    private boolean registerAddon() {
        ArenaBorderAddon addon = new ArenaBorderAddon(this);

        if(!addon.register()) {
            this.getLogger().warning("It seems like this addon has already been loaded. Please delete duplicates and try again.");
            Bukkit.getPluginManager().disablePlugin(this);
            return false;
        }
        return true;
    }
}
