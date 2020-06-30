package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBossBar;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.UUIDSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChatJson;

public class BossBar extends MiddleBossBar {

	public BossBar(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData bossbar = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_BOSS_BAR);
		UUIDSerializer.writeUUID2L(bossbar, uuid);
		MiscSerializer.writeVarIntEnum(bossbar, action);
		switch (action) {
			case ADD: {
				StringSerializer.writeVarIntUTF8String(bossbar, ChatAPI.toJSON(LegacyChatJson.convert(version, cache.getClientCache().getLocale(), title)));
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
				StringSerializer.writeVarIntUTF8String(bossbar, ChatAPI.toJSON(LegacyChatJson.convert(version, cache.getClientCache().getLocale(), title)));
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
		codec.write(bossbar);
	}

}
