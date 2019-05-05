package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.EnumSkippingTable;
import protocolsupport.protocol.types.WindowType;
import protocolsupport.zplatform.ServerPlatform;

public abstract class MiddleInventoryOpen extends ClientBoundMiddlePacket {

	protected final EnumSkippingTable<WindowType> typeSkipper = GenericIdSkipper.INVENTORY.getTable(version);

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
		if (typeSkipper.shouldSkip(type)) {
			connection.receivePacket(ServerPlatform.get().getPacketFactory().createInboundInventoryClosePacket());
			return false;
		} else {
			return true;
		}
	}

}
