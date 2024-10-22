package sap.ass01.frontend.plugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PluginManager {
    private final Map<String, Object> plugins = new HashMap<>();

    public <T> void registerPlugin(String pluginID, File libFile, Class<T> pluginClass) {
        try {
            var loader = new PluginClassLoader(libFile.getAbsolutePath());
            // Construct the fully qualified class name
            String expectedClassName = "sap.ass01.layered.effects." + pluginID;
            Class<?> loadedClass = loader.loadClass(expectedClassName);
            T plugin = pluginClass.cast(loadedClass.getDeclaredConstructor().newInstance());
            plugins.put(pluginID, plugin);
            System.out.println("Plugin loaded: " + pluginID);
        } catch (Exception e) {
            System.err.println("Error loading plugin: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getPlugin(String pluginID, Class<T> pluginClass) {
        return (T) plugins.get(pluginID);
    }
}