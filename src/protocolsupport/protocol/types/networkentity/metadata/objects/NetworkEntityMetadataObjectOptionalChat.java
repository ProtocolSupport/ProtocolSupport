package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectOptionalChat extends NetworkEntityMetadataObject<BaseComponent> {

	public NetworkEntityMetadataObjectOptionalChat() {
	}

	public NetworkEntityMetadataObjectOptionalChat(BaseComponent component) {
		this.value = component;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeBoolean(value != null);
		if (value != null) {
			StringCodec.writeVarIntUTF8String(to, ChatCodec.serialize(version, locale, value));
		}
	}

}
