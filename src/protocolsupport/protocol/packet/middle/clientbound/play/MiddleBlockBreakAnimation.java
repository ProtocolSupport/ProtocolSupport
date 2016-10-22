package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleBlockBreakAnimation<T> extends MiddleBlock<T> {

	protected int entityId;
	protected int stage;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		entityId = serializer.readVarInt();
		super.readFromServerData(serializer);
		stage = serializer.readByte();
	}

}
