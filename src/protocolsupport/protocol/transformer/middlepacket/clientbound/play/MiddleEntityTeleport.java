package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;

public abstract class MiddleEntityTeleport<T> extends MiddleEntity<T> {

	protected int x;
	protected int y;
	protected int z;
	protected byte yaw;
	protected byte pitch;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		super.readFromServerData(serializer);
		x = serializer.readInt();
		y = serializer.readInt();
		z = serializer.readInt();
		yaw = serializer.readByte();
		pitch = serializer.readByte();
	}

}
