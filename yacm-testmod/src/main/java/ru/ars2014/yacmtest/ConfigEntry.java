package ru.ars2014.yacmtest;

import ru.ars2014.yacm.entrypoint.ConfigEntrypoint;
import ru.ars2014.yacm.entrypoint.ConfigRegister;

public class ConfigEntry implements ConfigEntrypoint {
    @Override
    public void onCommonInitialize(ConfigRegister register) {
        register.register(CommonConfig.SPEC);
    }

    @Override
    public void onClientInitialize(ConfigRegister register) {

    }

    @Override
    public void onServerInitialize(ConfigRegister register) {

    }
}
