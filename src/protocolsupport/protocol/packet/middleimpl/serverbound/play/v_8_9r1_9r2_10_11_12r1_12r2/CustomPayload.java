package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.abs_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2.AbstractCustomPayload;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class CustomPayload extends AbstractCustomPayload {

	public CustomPayload(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		ProtocolVersion version = connection.getVersion();
		tag = StringSerializer.readString(clientdata, version, 20);
		data = MiscSerializer.readAllBytesSlice(clientdata, Short.MAX_VALUE);
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		switch (tag) {
			case LegacyCustomPayloadChannelName.LEGACY_REGISTER: {
				return transformRegisterUnregister(true);
			}
			case LegacyCustomPayloadChannelName.LEGACY_UNREGISTER: {
				return transformRegisterUnregister(false);
			}
			case LegacyCustomPayloadChannelName.LEGACY_BOOK_EDIT: {
				return transformBookEdit();
			}
			case LegacyCustomPayloadChannelName.LEGACY_BOOK_SIGN: {
				return transformBookSign();
			}
			case LegacyCustomPayloadChannelName.LEGACY_SET_BEACON: {
				return transformSetBeaconEffect();
			}
			case LegacyCustomPayloadChannelName.LEGACY_NAME_ITEM: {
				return transformNameItem();
			}
			case LegacyCustomPayloadChannelName.LEGACY_PICK_ITEM: {
				return transformPickItem();
			}
			case LegacyCustomPayloadChannelName.LEGACY_STRUCTURE_BLOCK: {
				return transformStructureBlock();
			}
			case LegacyCustomPayloadChannelName.LEGACY_TRADE_SELECT: {
				return transformTradeSelect();
			}
			case LegacyCustomPayloadChannelName.LEGACY_COMMAND_RIGHT_NAME:
			case LegacyCustomPayloadChannelName.LEGACY_COMMAND_TYPO_NAME:
			case LegacyCustomPayloadChannelName.LEGACY_COMMAND_BLOCK_NAME: {
				//TODO: implement
				return RecyclableEmptyList.get();
			}
			default: {
				return transformCustomPayload();
			}
		}
	}

}
