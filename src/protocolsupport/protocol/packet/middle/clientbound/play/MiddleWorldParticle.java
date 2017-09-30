package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.ArrayList;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.id.IdSkipper;
import protocolsupport.protocol.utils.types.Particle;

public abstract class MiddleWorldParticle extends ClientBoundMiddlePacket {

	protected Particle type;
	protected boolean longdist;
	protected float x;
	protected float y;
	protected float z;
	protected float offX;
	protected float offY;
	protected float offZ;
	protected float speed;
	protected int count;
	protected ArrayList<Integer> adddata = new ArrayList<>();

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		type = Particle.getById(serverdata.readInt());
		longdist = serverdata.readBoolean();
		x = serverdata.readFloat();
		y = serverdata.readFloat();
		z = serverdata.readFloat();
		offX = serverdata.readFloat();
		offY = serverdata.readFloat();
		offZ = serverdata.readFloat();
		speed = serverdata.readFloat();
		count = serverdata.readInt();
		adddata.clear();
		while (serverdata.isReadable()) {
			adddata.add(VarNumberSerializer.readVarInt(serverdata));
		}
	}

	@Override
	public boolean postFromServerRead() {
		return !IdSkipper.PARTICLE.getTable(connection.getVersion()).shouldSkip(type);
	}

}
