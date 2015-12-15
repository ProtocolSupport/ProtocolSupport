package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.storage.LocalStorage;

public abstract class MiddleEntityVelocity<T> extends MiddleEntity<T> {

	protected int motX;
	protected int motY;
	protected int motZ;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		super.readFromServerData(serializer);
		motX = serializer.readShort();
		motY = serializer.readShort();
		motZ = serializer.readShort();
	}

	@Override
	public void handle(LocalStorage storage) {
	}

}
