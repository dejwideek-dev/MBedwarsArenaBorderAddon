package pl.dejwideek.arenaborderaddon.configs;
import net.elytrium.java.commons.config.YamlConfig;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ALL")
public class Config extends YamlConfig {

    @Ignore public static Config IMP = new Config();

    @Comment("Arena Border Addon for MBedwars by dejwideek")

    @Create public PERMISSIONS PERMISSIONS;

    @Comment("Permissions")
    public static class PERMISSIONS {
        public String RELOAD = "bw.arenaborder.reload";
    }

    @Create public MESSAGES MESSAGES;

    @Comment({"Messages", "RGB colors are supported. (1.16+ only)"})
    public static class MESSAGES {
        @Comment("Available placeholder: %permission%")
        public String NO_PERMISSION = "&cYou must have permission &4%permission% &cto do this!";

        public String RELOADED = "&eSuccessfully reloaded configuration file!";
    }

    @Create public BORDER BORDER;

    @Comment({"Border configuration", "Per arenas configuration is in arenas.yml"})
    public static class BORDER {
        @Create public DEFAULT DEFAULT;

        public static class DEFAULT {
            @Create public CENTER_LOCATION CENTER_LOCATION;

            public static class CENTER_LOCATION {
                public double X = 0.0;
                public double Z = 0.0;
            }

            public double SIZE = 300.0;
        }
    }

    @Comment("Disabled arenas from border")
    public List<String> DISABLED_ARENAS =
            Arrays.asList("disabledArena1", "disabledArena2");
}
