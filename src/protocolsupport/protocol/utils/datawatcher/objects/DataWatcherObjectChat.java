package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.ReadableDataWatcherObject;

public class DataWatcherObjectChat extends ReadableDataWatcherObject<BaseComponent> {

	@Override
	public void readFromStream(ByteBuf from) {
		value = ChatAPI.fromJSON(StringSerializer.readString(from, ProtocolVersionsHelper.LATEST_PC));
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		StringSerializer.writeString(to, version, ChatAPI.toJSON(value));
	}

}
