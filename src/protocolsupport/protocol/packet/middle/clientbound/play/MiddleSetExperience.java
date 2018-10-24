package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleSetExperience extends ClientBoundMiddlePacket {

	public MiddleSetExperience(ConnectionImpl connection) {
		super(connection);
	}

	protected float exp;
	protected int level;
	protected int totalExp;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		exp = serverdata.readFloat();
		level = VarNumberSerializer.readVarInt(serverdata);
		totalExp = VarNumberSerializer.readVarInt(serverdata);
	}

}
