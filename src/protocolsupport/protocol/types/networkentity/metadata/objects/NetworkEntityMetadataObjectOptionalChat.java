package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.chat.ChatSerializer;
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
			StringSerializer.writeVarIntUTF8String(to, ChatSerializer.serialize(version, locale, value));
		}
	}

}
