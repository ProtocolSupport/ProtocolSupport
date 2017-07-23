package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleExplosion;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PELevelEvent;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Explosion extends MiddleExplosion {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		packets.add(create(version, x, y , z, radius, blocks));
		packets.add(EntityVelocity.create(version, cache.getSelfPlayerEntityId(), pMotX, pMotY, pMotZ));
		packets.add(WorldParticle.create(PELevelEvent.PARTICLE_HUGE_EXPLOSION_SEED, x, y, z));
		return packets;
	}
	
	public static ClientBoundPacketData create(ProtocolVersion version, float x, float y, float z, float radius, Position[] blocks) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.EXPLODE, version);
		MiscSerializer.writeLFloat(serializer, x);
		MiscSerializer.writeLFloat(serializer, y);
		MiscSerializer.writeLFloat(serializer, z);
		MiscSerializer.writeLFloat(serializer, radius);
		VarNumberSerializer.writeVarInt(serializer, blocks.length);
		for (Position b : blocks) {
			PositionSerializer.writePEPosition(serializer, b);
		}
		return serializer;
	}


}
