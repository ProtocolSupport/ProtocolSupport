package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClose;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.window.WindowCache;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.EnumSkippingTable;
import protocolsupport.protocol.typeremapper.window.AbstractWindowsRemapper;
import protocolsupport.protocol.typeremapper.window.WindowRemapper;
import protocolsupport.protocol.typeremapper.window.WindowsRemappersRegistry;
import protocolsupport.protocol.types.WindowType;

public abstract class MiddleInventoryOpen extends ClientBoundMiddlePacket {

	protected final WindowCache windowCache = cache.getWindowCache();

	protected final EnumSkippingTable<WindowType> windowSkipper = GenericIdSkipper.INVENTORY.getTable(version);
	protected final AbstractWindowsRemapper windowsRemapper = WindowsRemappersRegistry.get(version);

	public MiddleInventoryOpen(MiddlePacketInit init) {
		super(init);
	}

	protected byte windowId;
	protected WindowType type;
	protected BaseComponent title;

	protected WindowRemapper windowRemapper;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		windowId = (byte) VarNumberSerializer.readVarInt(serverdata);
		type = MiscSerializer.readVarIntEnum(serverdata, WindowType.CONSTANT_LOOKUP);
		title = ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(serverdata), true);
	}

	@Override
	protected void writeToClient() {
		if (windowSkipper.isSet(type)) {
			codec.readAndComplete(MiddleInventoryClose.create(windowId));
		} else {
			windowRemapper = windowsRemapper.get(type, 0);
			windowCache.setOpenedWindow(windowId, type, windowRemapper);
			writeToClient0();
		}
	}

	protected abstract void writeToClient0();

}
