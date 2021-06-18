package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBossBar;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class BossBar extends MiddleBossBar {

	public BossBar(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		ClientBoundPacketData bossbar = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_BOSS_BAR);
		UUIDCodec.writeUUID2L(bossbar, uuid);
		MiscDataCodec.writeVarIntEnum(bossbar, action);
		switch (action) {
			case ADD: {
				StringCodec.writeVarIntUTF8String(bossbar, ChatCodec.serialize(version, clientCache.getLocale(), title));
				bossbar.writeFloat(percent);
				VarNumberCodec.writeVarInt(bossbar, color);
				VarNumberCodec.writeVarInt(bossbar, divider);
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
				StringCodec.writeVarIntUTF8String(bossbar, ChatCodec.serialize(version, clientCache.getLocale(), title));
				break;
			}
			case UPDATE_STYLE: {
				VarNumberCodec.writeVarInt(bossbar, color);
				VarNumberCodec.writeVarInt(bossbar, divider);
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
