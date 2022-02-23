package ru.ars2014.yacm;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.fabricmc.loader.api.metadata.ModMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ars2014.yacm.entrypoint.ConfigEntrypoint;
import ru.ars2014.yacm.entrypoint.ConfigRegister;
import ru.ars2014.yacm.impl.ConfigTracker;
import ru.ars2014.yacm.impl.ModConfig;

import java.nio.file.Path;

public class YACM implements PreLaunchEntrypoint {
    public static final String MOD_ID = "yacm";
    public static final String MOD_NAME = "YACM";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    // TODO: Implement synchronization
//    public static final Identifier CONFIG_SYNC_S2C_PACKET = new Identifier(MOD_ID, "sync_config");
//    public static final Identifier REQUEST_SYNC_C2S_PACKET = new Identifier(MOD_ID, "request_config");

    @Override
    public void onPreLaunch() {
        LOGGER.info("Hello from YACM!");
        FabricLoader.getInstance().getEntrypointContainers("yacm", ConfigEntrypoint.class).forEach(entrypoint -> {
            ModMetadata metadata = entrypoint.getProvider().getMetadata();
            String modID = metadata.getId();

            ConfigRegister commonRegister = new ConfigRegister(ModConfig.Type.COMMON, modID);
            ConfigRegister clientRegister = new ConfigRegister(ModConfig.Type.CLIENT, modID);
            ConfigRegister serverRegister = new ConfigRegister(ModConfig.Type.SERVER, modID);

            try {
                ConfigEntrypoint configEntrypoint = entrypoint.getEntrypoint();
                configEntrypoint.onCommonInitialize(commonRegister);
                if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                    configEntrypoint.onClientInitialize(clientRegister);
                } else {
                    configEntrypoint.onServerInitialize(serverRegister);
                }
            } catch (Throwable e) {
                LOGGER.error("Mod {} provides a broken implementation of ConfigEntrypoint", modID, e);
                throw e;
            }
        });

        Path configBasePath = FabricLoader.getInstance().getConfigDir();
        Path serverBasePath = FabricLoader.getInstance().getGameDir().resolve("world").resolve("serverconfig");
        ConfigTracker.INSTANCE.loadConfigs(ModConfig.Type.COMMON, configBasePath);
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ConfigTracker.INSTANCE.loadConfigs(ModConfig.Type.CLIENT, configBasePath);
        } else {
            ConfigTracker.INSTANCE.loadConfigs(ModConfig.Type.SERVER, serverBasePath);
        }
    }
}
