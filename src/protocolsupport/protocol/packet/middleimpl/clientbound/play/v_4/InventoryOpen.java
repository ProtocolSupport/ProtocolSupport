package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyChat;
import protocolsupport.protocol.typeremapper.legacy.LegacyWindowType;
import protocolsupport.protocol.typeremapper.legacy.LegacyWindowType.LegacyWindowData;

public class InventoryOpen extends MiddleInventoryOpen {

	public InventoryOpen(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void writeToClient0() {
		LegacyWindowData wdata = LegacyWindowType.getData(windowRemapper.toClientWindowType(type));
		ClientBoundPacketData windowopen = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WINDOW_OPEN);
		windowopen.writeByte(windowId);
		windowopen.writeByte(wdata.getIntId());
		StringSerializer.writeShortUTF16BEString(windowopen, LegacyChat.clampLegacyText(title.toLegacyText(clientCache.getLocale()), 32));
		windowopen.writeByte(windowRemapper.toClientSlots(0));
		codec.write(windowopen);
	}

}
