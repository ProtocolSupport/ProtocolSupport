package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.storage.SharedStorage.WindowType;

public abstract class MiddleInventoryOpen<T> extends ClientBoundMiddlePacket<T> {

	protected int windowId;
	protected String invname;
	protected String titleJson;
	protected int slots;
	protected int horseId;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		windowId = serializer.readUnsignedByte();
		invname = serializer.readString(32);
		titleJson = serializer.readString();
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
