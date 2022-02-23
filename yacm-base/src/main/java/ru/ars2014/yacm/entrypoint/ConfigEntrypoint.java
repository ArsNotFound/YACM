package ru.ars2014.yacm.entrypoint;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public interface ConfigEntrypoint {
    void onCommonInitialize(ConfigRegister register);

    @Environment(EnvType.CLIENT)
    void onClientInitialize(ConfigRegister register);

    @Environment(EnvType.SERVER)
    void onServerInitialize(ConfigRegister register);
}
