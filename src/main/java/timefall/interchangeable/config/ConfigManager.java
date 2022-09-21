package timefall.interchangeable.config;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.loader.api.FabricLoader;
import timefall.interchangeable.Interchangeable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
    public static final Path FABRIC_CONFIG_DIR = FabricLoader.getInstance().getConfigDir();
    public static ConfigurationObject CONFIG_FILE;
    public static boolean SUCCESSFULLY_LOADED_CONFIG;

    public static void interpretConfigFile() {
        String configInQuestion = "interchangeable.json";
        //Make config if it doesn't exist
        createConfigFile(configInQuestion);
        //Read config and store into appropriate configuration object
        readConfigFile(configInQuestion);
    }

    public static void createConfigFile(String configToCreate) {
        // Create the file in memory
        File configFile = FABRIC_CONFIG_DIR.resolve(configToCreate).toFile();
        // If it doesn't exist in disk
        if (!configFile.exists()) {
            //noinspection ResultOfMethodCallIgnored
            configFile.getParentFile().mkdirs();
            try {
                String configFileText = getConfigFileText(configToCreate);

                FileWriter configFileWriter = new FileWriter(configFile);

                configFileWriter.write(configFileText);
                configFileWriter.close();

                //noinspection ResultOfMethodCallIgnored
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void readConfigFile(String configToRead) {
        // Create the variable to contain the config file in memory
        File configFile = FABRIC_CONFIG_DIR.resolve(configToRead).toFile();
        if (configFile.exists()) {
            // Create the Gson instance
            try {
                // Create a reader for the configuration file
                Reader configReader = Files.newBufferedReader(configFile.toPath());
                // Create an object from the config file
                testSuccessfulSettingsConfig(configReader, configFile);
            } catch (IOException e) {
                Interchangeable.LOGGER.error(Interchangeable.MOD_NAME_LOG_ID + " IO error when reading configuration file:");
                e.printStackTrace();
                System.exit(-1);
            } catch (JsonSyntaxException jsonSyntaxException) {
                Interchangeable.LOGGER.error(Interchangeable.MOD_NAME_LOG_ID + " JSON syntax error when reading configuration file (" + configFile.getName() + "):");
                jsonSyntaxException.printStackTrace();
                System.exit(-1);
            } catch (JsonParseException jsonParseException) {
                Interchangeable.LOGGER.error(Interchangeable.MOD_NAME_LOG_ID + " JSON parse error when reading configuration file (" + configFile.getName() + "):");
                jsonParseException.printStackTrace();
                System.exit(-1);
            }
        } else Interchangeable.LOGGER.error("No configuration file found for Interchangeable Mod in: " + configFile.getPath());
    }

    public static String getConfigFileText(String configToCreate) {
        return """
                {
                  "EquivalenceClasses": {
                    [],
                    []
                  }
                }
                """;
    }

    public static void testSuccessfulSettingsConfig(Reader configReader, File configFile) {
        CONFIG_FILE = new Gson().fromJson(configReader, ConfigurationObject.class);
        if (CONFIG_FILE.getEquivalenceClasses() == null) {
            Interchangeable.LOGGER.error(Interchangeable.MOD_NAME_LOG_ID + " EquivalenceClasses does not exist or is malformed in the configuration file: " + configFile.getName());
        } else {
            SUCCESSFULLY_LOADED_CONFIG = true;
        }
    }
}
