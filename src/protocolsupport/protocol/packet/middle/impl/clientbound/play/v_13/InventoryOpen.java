package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_13;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__16r2.AbstractStateIdTrackInventoryOpen;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.window.WindowTypeIdMappingRegistry;
import protocolsupport.protocol.typeremapper.window.WindowTypeIdMappingRegistry.WindowTypeIdMappingTable;

public class InventoryOpen extends AbstractStateIdTrackInventoryOpen implements IClientboundMiddlePacketV13 {

	public InventoryOpen(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	protected final WindowTypeIdMappingTable windowTypeIdMappingTable = WindowTypeIdMappingRegistry.INSTANCE.getTable(version);

	@Override
	public void write0() {
		ClientBoundPacketData windowopen = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WINDOW_OPEN);
		writeData(
			windowopen,
			windowId,
			(String) windowTypeIdMappingTable.get(windowRemapper.toClientWindowType(type)),
			ChatCodec.serialize(version, clientCache.getLocale(), title),
			windowRemapper.toClientWindowSlots(0)
		);
		io.writeClientbound(windowopen);
	}

	public static void writeData(ClientBoundPacketData to, int windowId, String type, String titleJson, int slots) {
		to.writeByte(windowId);
		StringCodec.writeVarIntUTF8String(to, type);
		StringCodec.writeVarIntUTF8String(to, titleJson);
		to.writeByte(slots);
	}

}
