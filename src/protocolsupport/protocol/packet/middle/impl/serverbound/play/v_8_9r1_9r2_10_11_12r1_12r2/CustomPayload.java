package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.ProtocolSupport;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV9r2;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadData;
import protocolsupport.zplatform.ServerPlatform;

public class CustomPayload extends MiddleCustomPayload implements
IServerboundMiddlePacketV8,
IServerboundMiddlePacketV9r1,
IServerboundMiddlePacketV9r2,
IServerboundMiddlePacketV10,
IServerboundMiddlePacketV11,
IServerboundMiddlePacketV12r1,
IServerboundMiddlePacketV12r2 {

	public CustomPayload(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void read(ByteBuf clientdata) {
		tag = StringCodec.readVarIntUTF8String(clientdata, 20);
		data = MiscDataCodec.readAllBytesSlice(clientdata, Short.MAX_VALUE);
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
				LegacyCustomPayloadData.transformAndWriteNameItemSString(io, data);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_TRADE_SELECT: {
				LegacyCustomPayloadData.transformAndWriteTradeSelect(io, data);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_PICK_ITEM: {
				LegacyCustomPayloadData.transformAndWritePickItem(io, data);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_STRUCTURE_BLOCK: {
				LegacyCustomPayloadData.transformAndWriteStructureBlock(io, data);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_COMMAND_RIGHT_NAME:
			case LegacyCustomPayloadChannelName.LEGACY_COMMAND_TYPO_NAME: {
				LegacyCustomPayloadData.transformAndWriteAdvancedCommandBlockEdit(io, data, true);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_COMMAND_BLOCK_NAME: {
				LegacyCustomPayloadData.transformAndWriteAutoCommandBlockEdit(io, data);
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

		super.write();
	}

}
