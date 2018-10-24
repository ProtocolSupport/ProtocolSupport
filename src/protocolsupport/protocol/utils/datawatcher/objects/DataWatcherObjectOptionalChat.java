package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.datawatcher.ReadableDataWatcherObject;

public class DataWatcherObjectOptionalChat extends ReadableDataWatcherObject<BaseComponent> {

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version, String locale) {
		if (from.readBoolean()) {
			value = ChatAPI.fromJSON(StringSerializer.readString(from, version));
		}
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeBoolean(value != null);
		if (value != null) {
			StringSerializer.writeString(to, version, ChatAPI.toJSON(value));
		}
	}

}
