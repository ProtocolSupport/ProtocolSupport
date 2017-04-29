package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleWorldSound extends ClientBoundMiddlePacket {

	protected int id;
	protected int category;
	protected String parrotedEntityType;
	protected int x;
	protected int y;
	protected int z;
	protected float volume;
	protected float pitch;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		category = VarNumberSerializer.readVarInt(serverdata);
		id = VarNumberSerializer.readVarInt(serverdata);
		parrotedEntityType = StringSerializer.readString(serverdata, ProtocolVersion.getLatest(ProtocolType.PC));
		x = serverdata.readInt();
		y = serverdata.readInt();
		z = serverdata.readInt();
		volume = serverdata.readFloat();
		pitch = serverdata.readFloat();
	}

}
