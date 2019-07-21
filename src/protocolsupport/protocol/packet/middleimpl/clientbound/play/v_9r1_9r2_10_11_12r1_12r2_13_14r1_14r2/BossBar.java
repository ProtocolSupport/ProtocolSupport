package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBossBar;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChatJson;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BossBar extends MiddleBossBar {

	public BossBar(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_BOSS_BAR);
		MiscSerializer.writeUUID(serializer, uuid);
		MiscSerializer.writeVarIntEnum(serializer, action);
		switch (action) {
			case ADD: {
				StringSerializer.writeString(serializer, version, ChatAPI.toJSON(LegacyChatJson.convert(version, cache.getAttributesCache().getLocale(), title)));
				serializer.writeFloat(percent);
				VarNumberSerializer.writeVarInt(serializer, color);
				VarNumberSerializer.writeVarInt(serializer, divider);
				serializer.writeByte(flags);
				break;
			}
			case REMOVE: {
				break;
			}
			case UPDATE_PERCENT: {
				serializer.writeFloat(percent);
				break;
			}
			case UPDATE_TITLE: {
				StringSerializer.writeString(serializer, version, ChatAPI.toJSON(LegacyChatJson.convert(version, cache.getAttributesCache().getLocale(), title)));
				break;
			}
			case UPDATE_STYLE: {
				VarNumberSerializer.writeVarInt(serializer, color);
				VarNumberSerializer.writeVarInt(serializer, divider);
				break;
			}
			case UPDATE_FLAGS: {
				serializer.writeByte(flags);
				break;
			}
		}
		return RecyclableSingletonList.create(serializer);
	}

}
