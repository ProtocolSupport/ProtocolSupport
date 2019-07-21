package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.WindowType;

public abstract class MiddleInventoryOpen extends ClientBoundMiddlePacket {

	public MiddleInventoryOpen(ConnectionImpl connection) {
		super(connection);
	}

	protected int windowId;
	protected WindowType type;
	protected BaseComponent title;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		windowId = VarNumberSerializer.readVarInt(serverdata);
		type = MiscSerializer.readVarIntEnum(serverdata, WindowType.CONSTANT_LOOKUP);
		title = ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(serverdata));
	}

	@Override
	public boolean postFromServerRead() {
		cache.getWindowCache().setOpenedWindow(windowId, type);
		return true;
	}

}
