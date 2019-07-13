package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.NetworkItemStack;

public abstract class MiddleEntityEquipment extends ClientBoundMiddlePacket {

	public MiddleEntityEquipment(ConnectionImpl connection) {
		super(connection);
	}

	protected int entityId;
	protected int slot;
	protected NetworkItemStack itemstack;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		entityId = VarNumberSerializer.readVarInt(serverdata);
		slot = VarNumberSerializer.readVarInt(serverdata);
		itemstack = ItemStackSerializer.readItemStack(serverdata);
	}

}
