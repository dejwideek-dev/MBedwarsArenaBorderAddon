package pl.dejwideek.arenaborderaddon.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Description;
import dev.dejvokep.boostedyaml.YamlDocument;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import pl.dejwideek.arenaborderaddon.ArenaBorderPlugin;
import pl.dejwideek.arenaborderaddon.color.ColorAPI;
import pl.dejwideek.arenaborderaddon.configs.Config;

import java.io.IOException;

@SuppressWarnings("ALL")
public class ReloadCmd extends BaseCommand {

    private static ArenaBorderPlugin plugin;

    public ReloadCmd(ArenaBorderPlugin plugin) {
        this.plugin = plugin;
    }

    @CommandAlias("arenaborderreload|abreload")
    @Description("Reload config file")
    public void reload(CommandSender commandSender) {
        if(commandSender instanceof Player) {
            Player p = (Player) commandSender;
            Config config = plugin.config;
            YamlDocument arenasConfig = plugin.arenasConfig;
            ColorAPI colorApi = new ColorAPI();

            String permission = config.PERMISSIONS.RELOAD;
            String reloadedMsg = config.MESSAGES.RELOADED;
            String noPermsMsg = config.MESSAGES.NO_PERMISSION;

            if(p.hasPermission(permission)) {
                plugin.reloadConfig();
                try {
                    arenasConfig.reload();
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }

                p.sendMessage(colorApi.process(reloadedMsg));
                plugin.getLogger().info("Reloaded configuration file!");
            }
            else {
                p.sendMessage(colorApi.process(noPermsMsg
                        .replaceAll("%permission%", permission)));
            }
        }
        if(commandSender instanceof ConsoleCommandSender) {
            YamlDocument arenasConfig = plugin.arenasConfig;

            plugin.reloadConfig();
            try {
                arenasConfig.reload();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }

            plugin.getLogger().info("Reloaded configuration file!");
        }
        return;
    }
}
