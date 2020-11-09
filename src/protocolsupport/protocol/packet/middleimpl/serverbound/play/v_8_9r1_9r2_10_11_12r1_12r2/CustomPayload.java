package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.ProtocolSupport;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadData;
import protocolsupport.zplatform.ServerPlatform;

public class CustomPayload extends MiddleCustomPayload {

	public CustomPayload(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void readClientData(ByteBuf clientdata) {
		tag = StringSerializer.readVarIntUTF8String(clientdata, 20);
		data = MiscSerializer.readAllBytesSlice(clientdata, Short.MAX_VALUE);
	}

	@Override
	protected String getServerTag(String tag) {
		return LegacyCustomPayloadChannelName.fromLegacy(tag);
	}

	@Override
	protected void writeToServer() {
		if (custom) {
			super.writeToServer();
			return;
		}

		switch (tag) {
			case LegacyCustomPayloadChannelName.LEGACY_BOOK_EDIT: {
				LegacyCustomPayloadData.transformAndWriteBookEdit(codec, version, clientCache.getHeldSlot(), data);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_BOOK_SIGN: {
				LegacyCustomPayloadData.transformAndWriteBookSign(codec, version, clientCache.getHeldSlot(), data);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_SET_BEACON: {
				LegacyCustomPayloadData.transformAndWriteSetBeaconEffect(codec, data);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_NAME_ITEM: {
				LegacyCustomPayloadData.transformAndWriteNameItemSString(codec, data);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_TRADE_SELECT: {
				LegacyCustomPayloadData.transformAndWriteTradeSelect(codec, data);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_PICK_ITEM: {
				LegacyCustomPayloadData.transformAndWritePickItem(codec, data);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_STRUCTURE_BLOCK: {
				LegacyCustomPayloadData.transformAndWriteStructureBlock(codec, data);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_COMMAND_RIGHT_NAME:
			case LegacyCustomPayloadChannelName.LEGACY_COMMAND_TYPO_NAME: {
				LegacyCustomPayloadData.transformAndWriteAdvancedCommandBlockEdit(codec, data, true);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_COMMAND_BLOCK_NAME: {
				LegacyCustomPayloadData.transformAndWriteAutoCommandBlockEdit(codec, data);
				return;
			}
			default: {
				String legacyTag = LegacyCustomPayloadChannelName.fromLegacy(tag);
				if (legacyTag == null) {
					if (ServerPlatform.get().getMiscUtils().isDebugging()) {
						ProtocolSupport.logWarning("Skipping unsuppored legacy custom payload tag " + legacyTag);
					}
					return;
				}
				tag = legacyTag;
				break;
			}
		}

		super.writeToServer();
	}

}
