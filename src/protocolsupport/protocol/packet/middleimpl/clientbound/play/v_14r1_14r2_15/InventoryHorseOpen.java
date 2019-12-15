package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryHorseOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class InventoryHorseOpen extends MiddleInventoryHorseOpen {

	public InventoryHorseOpen(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData windowhorseopen = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WINDOW_HORSE_OPEN);
		windowhorseopen.writeByte(windowId);
		VarNumberSerializer.writeVarInt(windowhorseopen, slots);
		windowhorseopen.writeInt(entityId);
		codec.write(windowhorseopen);
	}

}
