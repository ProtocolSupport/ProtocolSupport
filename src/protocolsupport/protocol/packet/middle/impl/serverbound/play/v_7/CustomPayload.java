package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.ProtocolSupport;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV7;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadData;
import protocolsupport.zplatform.ServerPlatform;

public class CustomPayload extends MiddleCustomPayload implements IServerboundMiddlePacketV7 {

	public CustomPayload(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void read(ByteBuf clientdata) {
		tag = StringCodec.readVarIntUTF8String(clientdata, 20);
		data = ArrayCodec.readShortByteArraySlice(clientdata, Short.MAX_VALUE);
	}

	@Override
	protected String getServerTag(String tag) {
		return LegacyCustomPayloadChannelName.fromLegacy(tag);
	}

	@Override
	protected void write() {
		if (custom) {
			super.write();
			return;
		}

		switch (tag) {
			case LegacyCustomPayloadChannelName.LEGACY_BOOK_EDIT: {
				LegacyCustomPayloadData.transformAndWriteBookEdit(io, version, clientCache.getHeldSlot(), data);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_BOOK_SIGN: {
				LegacyCustomPayloadData.transformAndWriteBookSign(io, version, clientCache.getHeldSlot(), data);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_SET_BEACON: {
				LegacyCustomPayloadData.transformAndWriteSetBeaconEffect(io, data);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_NAME_ITEM: {
				LegacyCustomPayloadData.transformAndWriteNameItemDString(io, data);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_TRADE_SELECT: {
				LegacyCustomPayloadData.transformAndWriteTradeSelect(io, data);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_COMMAND_RIGHT_NAME:
			case LegacyCustomPayloadChannelName.LEGACY_COMMAND_TYPO_NAME: {
				LegacyCustomPayloadData.transformAndWriteAdvancedCommandBlockEdit(io, data, false);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_BRAND: {
				tag = LegacyCustomPayloadChannelName.MODERN_BRAND;
				data = LegacyCustomPayloadData.transformBrandDirectToString(data);
				return;
			}
			default: {
				String legacyTag = LegacyCustomPayloadChannelName.fromLegacy(tag);
				if (legacyTag == null) {
					if (ServerPlatform.get().getMiscUtils().isDebugging()) {
						ProtocolSupport.logWarning("Skipping unsuppored legacy custom payload tag message " + legacyTag);
					}
					return;
				}
				tag = legacyTag;
				break;
			}
		}

		super.write();
	}

}
