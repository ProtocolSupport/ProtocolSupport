package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.serializer.chat.ChatSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.window.WindowTypeIdMappingRegistry;
import protocolsupport.protocol.typeremapper.window.WindowTypeIdMappingRegistry.WindowTypeIdMappingTable;

public class InventoryOpen extends MiddleInventoryOpen {

	public InventoryOpen(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	protected final WindowTypeIdMappingTable windowTypeIdMappingTable = WindowTypeIdMappingRegistry.INSTANCE.getTable(version);

	@Override
	protected void writeToClient0() {
		ClientBoundPacketData windowopen = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_WINDOW_OPEN);
		VarNumberSerializer.writeVarInt(windowopen, windowId);
		VarNumberSerializer.writeVarInt(windowopen, ((Number) windowTypeIdMappingTable.get(windowRemapper.toClientWindowType(type))).intValue());
		StringSerializer.writeVarIntUTF8String(windowopen, ChatSerializer.serialize(version, clientCache.getLocale(), title));
		codec.writeClientbound(windowopen);
	}

}
