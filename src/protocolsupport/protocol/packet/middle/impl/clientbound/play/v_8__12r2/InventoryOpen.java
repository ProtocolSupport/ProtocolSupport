package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8__12r2;

import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__16r2.AbstractStateIdTrackInventoryOpen;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.window.WindowTypeIdMappingRegistry;
import protocolsupport.protocol.typeremapper.window.WindowTypeIdMappingRegistry.WindowTypeIdMappingTable;
import protocolsupport.protocol.utils.i18n.I18NData;

public class InventoryOpen extends AbstractStateIdTrackInventoryOpen implements
IClientboundMiddlePacketV8,
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2,
IClientboundMiddlePacketV10,
IClientboundMiddlePacketV11,
IClientboundMiddlePacketV12r1,
IClientboundMiddlePacketV12r2 {

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
			windowId,
			(String) windowTypeIdMappingTable.get(windowRemapper.toClientWindowType(type)),
			ChatCodec.serialize(version, I18NData.DEFAULT_LOCALE, new TextComponent(title.toLegacyText(clientCache.getLocale()))),
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
