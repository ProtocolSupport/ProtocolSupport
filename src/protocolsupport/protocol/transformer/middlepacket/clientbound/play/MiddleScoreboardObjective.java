package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleScoreboardObjective<T> extends ClientBoundMiddlePacket<T> {

	protected String name;
	protected int mode;
	protected String value;
	protected String type;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		name = serializer.readString(16);
		mode = serializer.readUnsignedByte();
		if (mode != 1) {
			value = serializer.readString(32);
			type = serializer.readString(32);
		}
	}

}
