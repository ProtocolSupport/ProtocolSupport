package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;

public abstract class MiddleEntityLook<T> extends MiddleEntity<T> {

	protected int yaw;
	protected int pitch;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		super.readFromServerData(serializer);
		yaw = serializer.readByte();
		pitch = serializer.readByte();
	}

}
