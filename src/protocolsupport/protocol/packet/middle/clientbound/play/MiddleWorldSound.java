package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleWorldSound extends ClientBoundMiddlePacket {

	public MiddleWorldSound(ConnectionImpl connection) {
		super(connection);
	}

	protected int id;
	protected int category;
	protected int x;
	protected int y;
	protected int z;
	protected float volume;
	protected float pitch;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		id = VarNumberSerializer.readVarInt(serverdata);
		category = VarNumberSerializer.readVarInt(serverdata);
		x = serverdata.readInt();
		y = serverdata.readInt();
		z = serverdata.readInt();
		volume = serverdata.readFloat();
		pitch = serverdata.readFloat();
	}

}
