package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleEntitySound extends ClientBoundMiddlePacket {

	public MiddleEntitySound(ConnectionImpl connection) {
		super(connection);
	}

	protected int id;
	protected int category;
	protected int entityId;
	protected float volume;
	protected float pitch;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		id = VarNumberSerializer.readVarInt(serverdata);
		category = VarNumberSerializer.readVarInt(serverdata);
		entityId = VarNumberSerializer.readVarInt(serverdata);
		volume = serverdata.readFloat();
		pitch = serverdata.readFloat();
	}

}
