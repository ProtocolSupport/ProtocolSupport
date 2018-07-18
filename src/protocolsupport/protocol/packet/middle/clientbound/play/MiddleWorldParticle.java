package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.ArrayList;

import io.netty.buffer.ByteBuf;
import net.minecraft.server.v1_13_R1.PacketPlayOutWorldParticles;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.id.IdSkipper;
import protocolsupport.protocol.utils.types.Particle;

public abstract class MiddleWorldParticle extends ClientBoundMiddlePacket {

//TODO: structure
//	protected Particle type;
//	protected boolean longdist;
//	protected float x;
//	protected float y;
//	protected float z;
//	protected float offX;
//	protected float offY;
//	protected float offZ;
//	protected float speed;
//	protected int count;
//	protected ArrayList<Object> adddata = new ArrayList<>();
	protected byte[] data;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
//		type = Particle.getById(serverdata.readInt());
//		longdist = serverdata.readBoolean();
//		x = serverdata.readFloat();
//		y = serverdata.readFloat();
//		z = serverdata.readFloat();
//		offX = serverdata.readFloat();
//		offY = serverdata.readFloat();
//		offZ = serverdata.readFloat();
//		speed = serverdata.readFloat();
//		count = serverdata.readInt();
//		adddata.clear();
		data = MiscSerializer.readAllBytes(serverdata);
	}

	@Override
	public boolean postFromServerRead() {
		return true;//!IdSkipper.PARTICLE.getTable(connection.getVersion()).shouldSkip(type);
	}

}
