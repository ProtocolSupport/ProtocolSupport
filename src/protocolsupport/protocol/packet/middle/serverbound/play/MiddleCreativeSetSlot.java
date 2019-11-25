package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.types.NetworkItemStack;

public abstract class MiddleCreativeSetSlot extends ServerBoundMiddlePacket {

	public MiddleCreativeSetSlot(ConnectionImpl connection) {
		super(connection);
	}

	protected int slot;
	protected NetworkItemStack itemstack;

	@Override
	public void writeToServer() {
		codec.read(create(slot, itemstack));
	}

	public static ServerBoundPacketData create(int slot, NetworkItemStack itemstack) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_CREATIVE_SET_SLOT);
		creator.writeShort(slot);
		ItemStackSerializer.writeItemStack(creator, itemstack);
		return creator;
	}

}
