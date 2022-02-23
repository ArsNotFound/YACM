package ru.ars2014.yacm.entrypoint;

import ru.ars2014.yacm.impl.ModConfig;
import ru.ars2014.yacm.api.IConfigSpec;

public final class ConfigRegister {
    private final ModConfig.Type type;
    private final String modID;

    public ConfigRegister(ModConfig.Type type, String modID) {
        this.type = type;
        this.modID = modID;
    }

    public void register(IConfigSpec<?> spec) {
        new ModConfig(type, spec, modID);
    }

    public void register(IConfigSpec<?> spec, String fileName) {
        new ModConfig(type, spec, modID, fileName);
    }
}
