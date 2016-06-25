package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;
import java.util.ArrayList;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleWorldParticle<T> extends ClientBoundMiddlePacket<T> {

	protected int type;
	protected boolean longdist;
	protected float x;
	protected float y;
	protected float z;
	protected float offX;
	protected float offY;
	protected float offZ;
	protected float speed;
	protected int count;
	protected ArrayList<Integer> adddata = new ArrayList<Integer>();

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		type = serializer.readInt();
		longdist = serializer.readBoolean();
		x = serializer.readFloat();
		y = serializer.readFloat();
		z = serializer.readFloat();
		offX = serializer.readFloat();
		offY = serializer.readFloat();
		offZ = serializer.readFloat();
		speed = serializer.readFloat();
		count = serializer.readInt();
		adddata.clear();
		while (serializer.isReadable()) {
			adddata.add(serializer.readVarInt());
		}
	}

}
