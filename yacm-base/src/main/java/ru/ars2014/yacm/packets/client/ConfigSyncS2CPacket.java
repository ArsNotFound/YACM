package ru.ars2014.yacm.packets.client;

import net.minecraft.util.Identifier;
import ru.ars2014.yacm.packets.IPacket;

import static ru.ars2014.yacm.YACM.MOD_ID;

public class ConfigSyncS2CPacket implements IPacket {
    public static final Identifier CONFIG_SYNC_S2C_PACKET = new Identifier(MOD_ID, "sync_config");

    @Override
    public void register() {

    }
}
