package protocolsupport.protocol.packet.middle.base.serverbound.play;

import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentMap;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.ProtocolSupport;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.typeremapper.basic.CustomPayloadTransformerRegistry;
import protocolsupport.protocol.typeremapper.basic.CustomPayloadTransformerRegistry.CustomPayloadTransformer;
import protocolsupport.protocol.typeremapper.basic.CustomPayloadTransformerRegistry.CustomPayloadTransformerTable;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;
import protocolsupport.zplatform.ServerPlatform;

public abstract class MiddleCustomPayload extends ServerBoundMiddlePacket {

	protected MiddleCustomPayload(IMiddlePacketInit init) {
		super(init);
	}

	protected final ConcurrentMap<Object, Object> custompayloadMetadata = cache.getCustomPayloadTransformerMetdata();

	protected final CustomPayloadTransformerTable custompayloadTable = CustomPayloadTransformerRegistry.INSTANCE.getTable(version);


	protected String tag;
	protected ByteBuf data;

	protected boolean custom;

	@Override
	protected void handle() {
		String modernTag = getServerTag(tag);
		if (modernTag != null) {
			switch (modernTag) {
				case LegacyCustomPayloadChannelName.MODERN_REGISTER:
				case LegacyCustomPayloadChannelName.MODERN_UNREGISTER: {
					custom = true;
					StringJoiner resultTagsJoiner = new StringJoiner("\u0000");
					for (String rTag : data.toString(StandardCharsets.UTF_8).split("\u0000")) {
						CustomPayloadTransformer transformer = custompayloadTable.getByClientName(rTag);
						if (transformer != null) {
							resultTagsJoiner.add(transformer.getServerTag());
						} else {
							String rServerTag = getServerTag(rTag);
							if (rServerTag != null) {
								resultTagsJoiner.add(rServerTag);
							} else if (ServerPlatform.get().getMiscUtils().isDebugging()) {
								ProtocolSupport.logWarning("Skipping unsuppored legacy custom payload tag register " + rTag);
							}
						}
					}
					if (resultTagsJoiner.length() == 0) {
						throw MiddlePacketCancelException.INSTANCE;
					}
					tag = modernTag;
					data = Unpooled.wrappedBuffer(resultTagsJoiner.toString().getBytes(StandardCharsets.UTF_8));
					break;
				}
				case LegacyCustomPayloadChannelName.MODERN_BUNGEE:  {
					custom = true;
					tag = modernTag;
					break;
				}
				default: {
					CustomPayloadTransformer transformer = custompayloadTable.getByClientName(tag);
					if (transformer != null) {
						custom = true;
						tag = transformer.getServerTag();
						data = Unpooled.wrappedBuffer(transformer.transformDataServerbound(custompayloadMetadata, MiscDataCodec.readAllBytes(data)));
					}
					break;
				}
			}
		}
	}

	protected abstract String getServerTag(String tag);

	@Override
	protected void write() {
		io.writeServerbound(create(tag, data));
	}

	@Override
	protected void cleanup() {
		custom = false;
	}

	public static ServerBoundPacketData create(String tag) {
		ServerBoundPacketData serializer = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_CUSTOM_PAYLOAD);
		StringCodec.writeVarIntUTF8String(serializer, tag);
		return serializer;
	}

	public static ServerBoundPacketData create(String tag, byte[] data) {
		ServerBoundPacketData serializer = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_CUSTOM_PAYLOAD);
		StringCodec.writeVarIntUTF8String(serializer, tag);
		serializer.writeBytes(data);
		return serializer;
	}

	public static ServerBoundPacketData create(String tag, ByteBuf data) {
		ServerBoundPacketData serializer = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_CUSTOM_PAYLOAD);
		StringCodec.writeVarIntUTF8String(serializer, tag);
		serializer.writeBytes(data);
		return serializer;
	}

}
