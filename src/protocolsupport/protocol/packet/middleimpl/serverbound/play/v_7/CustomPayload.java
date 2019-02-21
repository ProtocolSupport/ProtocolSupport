package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadData;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class CustomPayload extends ServerBoundMiddlePacket {

	public CustomPayload(ConnectionImpl connection) {
		super(connection);
	}

	protected String tag;
	protected ByteBuf data;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		tag = StringSerializer.readString(clientdata, version, 20);
		data = ArraySerializer.readShortByteArraySlice(clientdata, Short.MAX_VALUE);
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		switch (tag) {
			case LegacyCustomPayloadChannelName.LEGACY_REGISTER: {
				return LegacyCustomPayloadData.transformRegisterUnregister(cache.getChannelsCache(), tag, data, true);
			}
			case LegacyCustomPayloadChannelName.LEGACY_UNREGISTER: {
				return LegacyCustomPayloadData.transformRegisterUnregister(cache.getChannelsCache(), tag, data, false);
			}
			case LegacyCustomPayloadChannelName.LEGACY_BOOK_EDIT: {
				return LegacyCustomPayloadData.transformBookEdit(version, cache.getAttributesCache().getLocale(), data);
			}
			case LegacyCustomPayloadChannelName.LEGACY_BOOK_SIGN: {
				return LegacyCustomPayloadData.transformBookSign(version, cache.getAttributesCache().getLocale(), data);
			}
			case LegacyCustomPayloadChannelName.LEGACY_SET_BEACON: {
				return LegacyCustomPayloadData.transformSetBeaconEffect(data);
			}
			case LegacyCustomPayloadChannelName.LEGACY_NAME_ITEM: {
				return LegacyCustomPayloadData.transformNameItemDString(data);
			}
			case LegacyCustomPayloadChannelName.LEGACY_TRADE_SELECT: {
				return LegacyCustomPayloadData.transformTradeSelect(data);
			}
			case LegacyCustomPayloadChannelName.LEGACY_COMMAND_RIGHT_NAME:
			case LegacyCustomPayloadChannelName.LEGACY_COMMAND_TYPO_NAME: {
				return LegacyCustomPayloadData.transformAdvancedCommandBlockEdit(version, data, false);
			}
			default: {
				return LegacyCustomPayloadData.transformCustomPayload(tag, data);
			}
		}
	}

}
