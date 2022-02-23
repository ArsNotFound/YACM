package ru.ars2014.yacm.impl;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.toml.TomlFormat;
import ru.ars2014.yacm.api.IConfigSpec;

import java.io.ByteArrayInputStream;
import java.nio.file.Path;
import java.util.Locale;

public class ModConfig {
    private final Type type;
    private final IConfigSpec<?> spec;
    private final String fileName;
    private final String modID;
    private final ConfigFileTypeHandler configHandler;
    private CommentedConfig configData;

    public ModConfig(final Type type, final IConfigSpec<?> spec, final String modID, final String fileName) {
        this.type = type;
        this.spec = spec;
        this.fileName = fileName;
        this.modID = modID;
        this.configHandler = ConfigFileTypeHandler.TOML;
        ConfigTracker.INSTANCE.trackConfig(this);
    }

    public ModConfig(final Type type, final IConfigSpec<?> spec, final String modID) {
        this(type, spec, modID, defaultConfigName(type, modID));
    }

    private static String defaultConfigName(Type type, String modId) {
        // config file name would be "forge-client.toml" and "forge-server.toml"
        return String.format(Locale.ROOT, "%s-%s.toml", modId, type.extension());
    }

    public Type getType() {
        return type;
    }

    public String getFileName() {
        return fileName;
    }

    public ConfigFileTypeHandler getHandler() {
        return configHandler;
    }

    @SuppressWarnings("unchecked")
    public <T extends IConfigSpec<T>> IConfigSpec<T> getSpec() {
        return (IConfigSpec<T>) spec;
    }

    public String getModId() {
        return modID;
    }

    public CommentedConfig getConfigData() {
        return this.configData;
    }

    void setConfigData(final CommentedConfig configData) {
        this.configData = configData;
        this.spec.acceptConfig(this.configData);
    }

//    void fireEvent(final IConfigEvent configEvent) {
//        this.container.dispatchConfigEvent(configEvent);
//    }

    public void save() {
        ((CommentedFileConfig) this.configData).save();
    }

    public Path getFullPath() {
        return ((CommentedFileConfig) this.configData).getNioPath();
    }

    public void acceptSyncedConfig(byte[] bytes) {
        setConfigData(TomlFormat.instance().createParser().parse(new ByteArrayInputStream(bytes)));
    }

    public enum Type {
        /**
         * Common mod config for configuration that needs to be loaded on both environments.
         * Loaded on both servers and clients.
         * Stored in the global config directory.
         * Not synced.
         * Suffix is "-common" by default.
         */
        COMMON,
        /**
         * Client config is for configuration affecting the ONLY client state such as graphical options.
         * Only loaded on the client side.
         * Stored in the global config directory.
         * Not synced.
         * Suffix is "-client" by default.
         */
        CLIENT,
        /**
         * Server type config is configuration that is associated with a server instance.
         * Only loaded during server startup.
         * Stored in a server/save specific "serverconfig" directory.
         * Synced to clients during connection. - TODO
         * Suffix is "-server" by default.
         */
        SERVER;

        public String extension() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
