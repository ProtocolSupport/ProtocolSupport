package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.id.IdSkipper;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.zplatform.ServerPlatform;

public abstract class MiddleInventoryOpen extends ClientBoundMiddlePacket {

	protected int windowId;
	protected WindowType type;
	protected BaseComponent title;
	protected int slots;
	protected int horseId;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		windowId = serverdata.readUnsignedByte();
		type = WindowType.getById(StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 32));
		title = ChatAPI.fromJSON(StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC));
		slots = serverdata.readUnsignedByte();
		if (type == WindowType.HORSE) {
			horseId = serverdata.readInt();
		}
	}

	@Override
	public boolean postFromServerRead() {
		if (IdSkipper.INVENTORY.getTable(connection.getVersion()).shouldSkip(type)) {
			connection.receivePacket(ServerPlatform.get().getPacketFactory().createInboundInventoryClosePacket());
			return false;
		} else {
			int cacheSlots;
			switch(type) {
				case ANVIL: {
					cacheSlots = 3;
					break;
				}
				case BEACON: {
					cacheSlots = 1;
					break;
				}
				case CRAFTING_TABLE: {
					cacheSlots = 10;
					break;
				}
				case ENCHANT: {
					cacheSlots = 2;
					break;
				}
				default: {
					cacheSlots = slots;
					break;
				}
			}
			cache.setOpenedWindow(type, windowId, cacheSlots);
			return true;
		}
	}

}
