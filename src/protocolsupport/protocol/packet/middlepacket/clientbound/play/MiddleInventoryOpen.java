package protocolsupport.protocol.packet.middlepacket.clientbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.ClientBoundMiddlePacket;
import protocolsupport.protocol.storage.SharedStorage.WindowType;

public abstract class MiddleInventoryOpen<T> extends ClientBoundMiddlePacket<T> {

	protected int windowId;
	protected String invname;
	protected String titleJson;
	protected int slots;
	protected int horseId;

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

	@Override
	public void handle() {
		sharedstorage.setOpenedWindow(WindowType.fromName(invname));
	}

}
