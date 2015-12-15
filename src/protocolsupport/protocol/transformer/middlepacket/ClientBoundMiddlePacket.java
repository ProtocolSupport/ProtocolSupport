package protocolsupport.protocol.transformer.middlepacket;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.storage.LocalStorage;

public abstract class ClientBoundMiddlePacket<T> extends HasPlayer {

	public abstract void readFromServerData(PacketDataSerializer serializer) throws IOException;

	public abstract void handle(LocalStorage storage);

	public abstract T toData(ProtocolVersion version) throws IOException;

}
