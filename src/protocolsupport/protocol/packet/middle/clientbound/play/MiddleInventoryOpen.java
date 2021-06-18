package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClose;
import protocolsupport.protocol.storage.netcache.window.WindowCache;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.EnumSkippingTable;
import protocolsupport.protocol.typeremapper.window.WindowRemapper;
import protocolsupport.protocol.typeremapper.window.WindowsRemapper;
import protocolsupport.protocol.typeremapper.window.WindowsRemappersRegistry;
import protocolsupport.protocol.types.WindowType;

public abstract class MiddleInventoryOpen extends ClientBoundMiddlePacket {

	protected final WindowCache windowCache = cache.getWindowCache();

	protected final EnumSkippingTable<WindowType> windowSkipper = GenericIdSkipper.INVENTORY.getTable(version);
	protected final WindowsRemapper windowsRemapper = WindowsRemappersRegistry.get(version);

	protected MiddleInventoryOpen(MiddlePacketInit init) {
		super(init);
	}

	protected byte windowId;
	protected WindowType type;
	protected BaseComponent title;

	protected WindowRemapper windowRemapper;

	@Override
	protected void decode(ByteBuf serverdata) {
		windowId = (byte) VarNumberCodec.readVarInt(serverdata);
		type = MiscDataCodec.readVarIntEnum(serverdata, WindowType.CONSTANT_LOOKUP);
		title = ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(serverdata), true);
	}

	@Override
	protected void write() {
		if (windowSkipper.isSet(type)) {
			codec.writeServerboundAndFlush(MiddleInventoryClose.create(windowId));
		} else {
			windowRemapper = windowsRemapper.get(type, 0);
			windowCache.setOpenedWindow(windowId, type, 0, windowRemapper);
			writeToClient0();
		}
	}

	protected abstract void writeToClient0();

}
