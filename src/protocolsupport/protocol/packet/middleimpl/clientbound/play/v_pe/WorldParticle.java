package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import java.util.Random;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldParticle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.pe.PELevelEvent;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class WorldParticle extends MiddleWorldParticle {

	public WorldParticle(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		//ProtocolVersion version = connection.getVersion();
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		//if (!IdSkipper.PARTICLE.getTable(version).shouldSkip(type)) { TODO FIX
		//	packets.addAll(create(PEDataValues.PARTICLE.getTable(version).getRemap(type.getId()), x, y, z, offX, offY, offZ, (int) speed, count));
		//}
		return packets;
	}

	public static RecyclableArrayList<ClientBoundPacketData> create(int peParticle, float x, float y, float z, float offX, float offY, float offZ, int data, int count) {
		RecyclableArrayList<ClientBoundPacketData> particles = RecyclableArrayList.create();
		Random random = new Random();
		for (int i = 0; i < count; i++) {
			particles.add(PELevelEvent.createPacket(peParticle,
				(float) (x + (offX * random.nextGaussian())), (float) (y + (offY * random.nextGaussian())), (float) (z + (offZ * random.nextGaussian())),
				data));
		}
		return particles;
	}

	public static ClientBoundPacketData create(int peParticle, float x, float y, float z) {
		return create(peParticle, x, y, z, 0, 0, 0, 0, 1).get(0);
	}

}
