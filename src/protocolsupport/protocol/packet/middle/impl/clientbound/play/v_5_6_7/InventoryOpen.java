package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_5_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2.AbstractStateIdTrackInventoryOpen;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyChat;
import protocolsupport.protocol.typeremapper.window.WindowTypeIdMappingRegistry;
import protocolsupport.protocol.typeremapper.window.WindowTypeIdMappingRegistry.WindowTypeIdMappingTable;

public class InventoryOpen extends AbstractStateIdTrackInventoryOpen implements
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7 {

	public InventoryOpen(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	protected final WindowTypeIdMappingTable windowTypeIdMappingTable = WindowTypeIdMappingRegistry.INSTANCE.getTable(version);

	@Override
	protected void write0() {
		ClientBoundPacketData windowopen = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WINDOW_OPEN);
		writeData(
			windowopen,
			version,
			windowId,
			((Number) windowTypeIdMappingTable.get(windowRemapper.toClientWindowType(type))).intValue(),
			title.toLegacyText(clientCache.getLocale()),
			windowRemapper.toClientWindowSlots(0)
		);
		io.writeClientbound(windowopen);
	}

	public static void writeData(ClientBoundPacketData to, ProtocolVersion version, int windowId, int type, String title, int slots) {
		to.writeByte(windowId);
		to.writeByte(type);
		StringCodec.writeString(to, version, LegacyChat.clampLegacyText(title, 32));
		to.writeByte(slots);
		to.writeBoolean(true); //use provided title
	}

}
