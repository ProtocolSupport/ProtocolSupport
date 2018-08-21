package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleSetHealth extends ClientBoundMiddlePacket {

	public MiddleSetHealth(ConnectionImpl connection) {
		super(connection);
	}

	protected float health;
	protected int food;
	protected float saturation;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		health = serverdata.readFloat();
		food = VarNumberSerializer.readVarInt(serverdata);
		saturation = serverdata.readFloat();
	}

}
