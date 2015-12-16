package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.storage.LocalStorage;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleSetHealth<T> extends ClientBoundMiddlePacket<T> {

	protected float health;
	protected int food;
	protected float saturation;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		health = serializer.readFloat();
		food = serializer.readVarInt();
		saturation = serializer.readFloat();
	}

	@Override
	public void handle(LocalStorage storage) {
	}

}
