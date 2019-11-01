package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.storage.netcache.CustomPayloadChannelsCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadData;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class CustomPayload extends ServerBoundMiddlePacket {

	protected final CustomPayloadChannelsCache channelsCache = cache.getChannelsCache();

	public CustomPayload(ConnectionImpl connection) {
		super(connection);
	}

	protected String tag;
	protected ByteBuf data;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		tag = StringSerializer.readVarIntUTF8String(clientdata, 20);
		data = MiscSerializer.readAllBytesSlice(clientdata, Short.MAX_VALUE);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toNative() {
		switch (tag) {
			case LegacyCustomPayloadChannelName.LEGACY_REGISTER: {
				return LegacyCustomPayloadData.transformRegisterUnregister(channelsCache, tag, data, true);
			}
			case LegacyCustomPayloadChannelName.LEGACY_UNREGISTER: {
				return LegacyCustomPayloadData.transformRegisterUnregister(channelsCache, tag, data, false);
			}
			case LegacyCustomPayloadChannelName.LEGACY_BOOK_EDIT: {
				return LegacyCustomPayloadData.transformBookEdit(version, data);
			}
			case LegacyCustomPayloadChannelName.LEGACY_BOOK_SIGN: {
				return LegacyCustomPayloadData.transformBookSign(version, data);
			}
			case LegacyCustomPayloadChannelName.LEGACY_SET_BEACON: {
				return LegacyCustomPayloadData.transformSetBeaconEffect(data);
			}
			case LegacyCustomPayloadChannelName.LEGACY_NAME_ITEM: {
				return LegacyCustomPayloadData.transformNameItemSString(data);
			}
			case LegacyCustomPayloadChannelName.LEGACY_TRADE_SELECT: {
				return LegacyCustomPayloadData.transformTradeSelect(data);
			}
			case LegacyCustomPayloadChannelName.LEGACY_PICK_ITEM: {
				return LegacyCustomPayloadData.transformPickItem(data);
			}
			case LegacyCustomPayloadChannelName.LEGACY_STRUCTURE_BLOCK: {
				return LegacyCustomPayloadData.transformStructureBlock(data);
			}
			case LegacyCustomPayloadChannelName.LEGACY_COMMAND_RIGHT_NAME:
			case LegacyCustomPayloadChannelName.LEGACY_COMMAND_TYPO_NAME: {
				return LegacyCustomPayloadData.transformAdvancedCommandBlockEdit(data, true);
			}
			case LegacyCustomPayloadChannelName.LEGACY_COMMAND_BLOCK_NAME: {
				return LegacyCustomPayloadData.transformAutoCommandBlockEdit(data);
			}
			default: {
				return LegacyCustomPayloadData.transformCustomPayload(tag, data);
			}
		}
	}

}
