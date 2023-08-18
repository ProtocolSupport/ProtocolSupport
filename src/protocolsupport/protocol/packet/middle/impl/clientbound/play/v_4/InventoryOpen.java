package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__16r2.AbstractStateIdTrackInventoryOpen;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyChat;
import protocolsupport.protocol.typeremapper.window.WindowTypeIdMappingRegistry;
import protocolsupport.protocol.typeremapper.window.WindowTypeIdMappingRegistry.WindowTypeIdMappingTable;

public class InventoryOpen extends AbstractStateIdTrackInventoryOpen implements IClientboundMiddlePacketV4 {

	public InventoryOpen(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	protected final WindowTypeIdMappingTable windowTypeIdMappingTable = WindowTypeIdMappingRegistry.INSTANCE.getTable(version);

	@Override
	protected void write0() {
		ClientBoundPacketData windowopen = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WINDOW_OPEN);
		windowopen.writeByte(windowId);
		windowopen.writeByte(((Number) windowTypeIdMappingTable.get(windowRemapper.toClientWindowType(type))).intValue());
		StringCodec.writeShortUTF16BEString(windowopen, LegacyChat.clampLegacyText(title.toLegacyText(clientCache.getLocale()), 32));
		windowopen.writeByte(windowRemapper.toClientWindowSlots(0));
		io.writeClientbound(windowopen);
	}

}
