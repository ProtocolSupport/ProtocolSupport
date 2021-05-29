package protocolsupport.protocol.packet.middle.clientbound.play;

import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentMap;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.basic.CustomPayloadTransformerRegistry;
import protocolsupport.protocol.typeremapper.basic.CustomPayloadTransformerRegistry.CustomPayloadTransformer;
import protocolsupport.protocol.typeremapper.basic.CustomPayloadTransformerRegistry.CustomPayloadTransformerTable;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;

public abstract class MiddleCustomPayload extends ClientBoundMiddlePacket {

	protected MiddleCustomPayload(MiddlePacketInit init) {
		super(init);
	}

	protected final ConcurrentMap<Object, Object> custompayloadMetadata = cache.getCustomPayloadTransformerMetdata();

	protected final CustomPayloadTransformerTable custompayloadTable = CustomPayloadTransformerRegistry.INSTANCE.getTable(version);

	protected String tag;
	protected ByteBuf data;

	@Override
	protected void decode(ByteBuf serverdata) {
		tag = StringSerializer.readVarIntUTF8String(serverdata);
		data = serverdata.readSlice(serverdata.readableBytes());
	}

	protected boolean custom;

	@Override
	protected void handle() {
		String legacyTag = getClientTag(tag);
		switch (tag) {
			case LegacyCustomPayloadChannelName.MODERN_REGISTER:
			case LegacyCustomPayloadChannelName.MODERN_UNREGISTER: {
				custom = true;
				StringJoiner resultTagsJoiner = new StringJoiner("\u0000");
				for (String rTag : data.toString(StandardCharsets.UTF_8).split("\u0000")) {
					CustomPayloadTransformer transformer = custompayloadTable.getByServerName(rTag);
					if (transformer != null) {
						resultTagsJoiner.add(transformer.getClientTag());
					} else {
						resultTagsJoiner.add(getClientTag(rTag));
					}
				}
				tag = legacyTag;
				data = Unpooled.wrappedBuffer(resultTagsJoiner.toString().getBytes(StandardCharsets.UTF_8));
				break;
			}
			case LegacyCustomPayloadChannelName.MODERN_BUNGEE: {
				custom = true;
				tag = legacyTag;
				break;
			}
			default: {
				CustomPayloadTransformer transformer = custompayloadTable.getByServerName(tag);
				if (transformer != null) {
					custom = true;
					tag = transformer.getClientTag();
					data = Unpooled.wrappedBuffer(transformer.transformDataClientbound(custompayloadMetadata, MiscSerializer.readAllBytes(data)));
				}
			}
		}
	}

	protected abstract String getClientTag(String tag);

	@Override
	protected void cleanup() {
		custom = false;
	}

}
