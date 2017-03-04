package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.utils.types.Position;

public abstract class MiddleWorldEvent extends ClientBoundMiddlePacket {

	protected int effectId;
	protected Position position;
	protected int data;
	protected boolean disableRelative;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		effectId = serializer.readInt();
		position = serializer.readPosition();
		data = serializer.readInt();
		disableRelative = serializer.readBoolean();
	}

}
