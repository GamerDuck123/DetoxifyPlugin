package me.gamerduck.detoxify;

import me.gamerduck.detoxify.api.Config;
import me.gamerduck.detoxify.backend.DetoxifyEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class DetoxifyPlugin extends JavaPlugin {

    private Path PLUGIN_FOLDER = Path.of("plugins/DetoxifyPlugin");
    private Path LIB_FOLDER = PLUGIN_FOLDER.resolve("libs");

//    private BukkitAudiences adventure;
    @Override
    public void onEnable() {
        Config.load(PLUGIN_FOLDER.resolve("values.properties"));
        if (Files.notExists(LIB_FOLDER)) {
            try {
                Files.createDirectories(LIB_FOLDER);

                if (!Files.exists(LIB_FOLDER.resolve("detoxify_quantized.onnx"))) {
                    try (InputStream in = DetoxifyPlugin.class.getClassLoader().getResourceAsStream("detoxify_quantized.onnx")) {
                        if (in == null) {
                            throw new FileNotFoundException("Default values.properties not found in JAR!");
                        }
                        Files.copy(in, LIB_FOLDER.resolve("detoxify_quantized.onnx"));
                    }
                }
                if (!Files.exists(LIB_FOLDER.resolve("tokenizer.json"))) {
                    try (InputStream in = DetoxifyPlugin.class.getClassLoader().getResourceAsStream("tokenizer.json")) {
                        if (in == null) {
                            throw new FileNotFoundException("Default tokenizer.json not found in JAR!");
                        }
                        Files.copy(in, LIB_FOLDER.resolve("tokenizer.json"));
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
//        this.adventure = BukkitAudiences.create(this);
        try {
            Bukkit.getPluginManager().registerEvents(new DetoxifyEvents(this), this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        getCommand("detoxifyreload").setExecutor(new DetoxifyReloadCommand(this));
    }


//    @Override
//    public void onDisable() {
//        if (this.adventure != null) {
//            this.adventure.close();
//            this.adventure = null;
//        }
//    }
//
//
//    @NonNull
//    public BukkitAudiences adventure() {
//        if (this.adventure == null) {
//            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
//        }
//        return this.adventure;
//    }
    @NonNull
    public Path libFolder() {
        return this.LIB_FOLDER;
    }
    @NonNull
    public Path pluginFolder() {
        return this.PLUGIN_FOLDER;
    }

}
