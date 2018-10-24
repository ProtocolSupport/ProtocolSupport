package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleSetCooldown extends ClientBoundMiddlePacket {

	public MiddleSetCooldown(ConnectionImpl connection) {
		super(connection);
	}

	protected int itemId;
	protected int cooldown;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		itemId = VarNumberSerializer.readVarInt(serverdata);
		cooldown = VarNumberSerializer.readVarInt(serverdata);
	}

}
