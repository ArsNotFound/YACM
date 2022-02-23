package ru.ars2014.yacmtest;

import ru.ars2014.yacm.impl.ConfigSpec;

public class CommonConfig {
    private static final ConfigSpec.Builder BUILDER = new ConfigSpec.Builder();

    public static final ConfigSpec.IntValue testInt = BUILDER.comment("test").defineInRange("testInt", 1, 1, 100);

    public static final ConfigSpec SPEC = BUILDER.build();
}
