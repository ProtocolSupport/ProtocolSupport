package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBossBar;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.UUIDSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.serializer.chat.ChatSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class BossBar extends MiddleBossBar {

	public BossBar(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		ClientBoundPacketData bossbar = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_BOSS_BAR);
		UUIDSerializer.writeUUID2L(bossbar, uuid);
		MiscSerializer.writeVarIntEnum(bossbar, action);
		switch (action) {
			case ADD: {
				StringSerializer.writeVarIntUTF8String(bossbar, ChatSerializer.serialize(version, clientCache.getLocale(), title));
				bossbar.writeFloat(percent);
				VarNumberSerializer.writeVarInt(bossbar, color);
				VarNumberSerializer.writeVarInt(bossbar, divider);
				bossbar.writeByte(flags);
				break;
			}
			case REMOVE: {
				break;
			}
			case UPDATE_PERCENT: {
				bossbar.writeFloat(percent);
				break;
			}
			case UPDATE_TITLE: {
				StringSerializer.writeVarIntUTF8String(bossbar, ChatSerializer.serialize(version, clientCache.getLocale(), title));
				break;
			}
			case UPDATE_STYLE: {
				VarNumberSerializer.writeVarInt(bossbar, color);
				VarNumberSerializer.writeVarInt(bossbar, divider);
				break;
			}
			case UPDATE_FLAGS: {
				bossbar.writeByte(flags);
				break;
			}
		}
		codec.writeClientbound(bossbar);
	}

}
