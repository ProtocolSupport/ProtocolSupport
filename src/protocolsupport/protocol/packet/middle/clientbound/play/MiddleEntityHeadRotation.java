package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleEntityHeadRotation<T> extends MiddleEntity<T> {

	protected byte headRot;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		super.readFromServerData(serializer);
		headRot = serializer.readByte();
	}

}
