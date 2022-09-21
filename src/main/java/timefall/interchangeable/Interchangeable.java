package timefall.interchangeable;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import timefall.interchangeable.config.ConfigManager;

public class Interchangeable implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("interchangeable");
    public static final String MOD_NAME_LOG_ID = "[Interchangeable Mod]";

    @Override
    public void onInitialize() {
        ConfigManager.interpretConfigFile();
    }
}
