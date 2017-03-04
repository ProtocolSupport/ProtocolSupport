package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.types.WindowType;

public abstract class MiddleInventoryOpen extends ClientBoundMiddlePacket {

	protected int windowId;
	protected String invname;
	protected String titleJson;
	protected int slots;
	protected int horseId;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		windowId = serverdata.readUnsignedByte();
		invname = StringSerializer.readString(serverdata, ProtocolVersion.getLatest(), 32);
		titleJson = StringSerializer.readString(serverdata, ProtocolVersion.getLatest());
		slots = serverdata.readUnsignedByte();
		if (invname.equals("EntityHorse")) {
			horseId = serverdata.readInt();
		}
	}

	@Override
	public void handle() {
		cache.setOpenedWindow(WindowType.fromName(invname));
	}

}
