package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleInventoryOpen<T> extends ClientBoundMiddlePacket<T> {

	protected int windowId;
	protected String invname;
	protected String titleJson;
	protected int slots;
	protected int horseId;

	@Override
	public boolean needsPlayer() {
		return true;
	}

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		windowId = serializer.readUnsignedByte();
		invname = serializer.readString(32);
		titleJson = serializer.readString(Short.MAX_VALUE);
		slots = serializer.readUnsignedByte();
		if (invname.equals("EntityHorse")) {
			horseId = serializer.readInt();
		}
	}

}
