package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6;

import io.netty.buffer.ByteBuf;
import protocolsupport.ProtocolSupport;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadData;
import protocolsupport.zplatform.ServerPlatform;

public class CustomPayload extends MiddleCustomPayload {

	public CustomPayload(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void readClientData(ByteBuf clientdata) {
		tag = StringSerializer.readShortUTF16BEString(clientdata, 20);
		data = ArraySerializer.readShortByteArraySlice(clientdata, Short.MAX_VALUE);
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
				LegacyCustomPayloadData.transformAndWriteBookEdit(codec, version, data);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_BOOK_SIGN: {
				LegacyCustomPayloadData.transformAndWriteBookSign(codec, version, data);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_SET_BEACON: {
				LegacyCustomPayloadData.transformAndWriteSetBeaconEffect(codec, data);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_NAME_ITEM: {
				LegacyCustomPayloadData.transformAndWriteNameItemDString(codec, data);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_TRADE_SELECT: {
				LegacyCustomPayloadData.transformAndWriteTradeSelect(codec, data);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_COMMAND_RIGHT_NAME:
			case LegacyCustomPayloadChannelName.LEGACY_COMMAND_TYPO_NAME: {
				LegacyCustomPayloadData.transformAndWriteBasicCommandBlockEdit(codec, data);
				return;
			}
			case LegacyCustomPayloadChannelName.LEGACY_BRAND: {
				tag = LegacyCustomPayloadChannelName.MODERN_BRAND;
				data = LegacyCustomPayloadData.transformBrandDirectToString(data);
				break;
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
