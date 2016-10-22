package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleEntityHeadRotation<T> extends MiddleEntity<T> {

	protected byte headRot;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		super.readFromServerData(serializer);
		headRot = serializer.readByte();
	}

}
