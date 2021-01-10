package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddlePickItem extends ServerBoundMiddlePacket {

	public MiddlePickItem(MiddlePacketInit init) {
		super(init);
	}

	protected int slot;

	@Override
	protected void write() {
		codec.writeServerbound(create(slot));
	}

	public static ServerBoundPacketData create(int slot) {
		ServerBoundPacketData serializer = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_PICK_ITEM);
		VarNumberSerializer.writeVarInt(serializer, slot);
		return serializer;
	}

}
