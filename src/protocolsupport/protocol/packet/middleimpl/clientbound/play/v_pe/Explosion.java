package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleExplosion;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PELevelEvent;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Explosion extends MiddleExplosion {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		packets.add(create(version, x, y , z, radius, blocks));
		packets.add(EntityVelocity.create(version, cache.getSelfPlayerEntityId(), pMotX, pMotY, pMotZ));
		packets.add(WorldParticle.create(PELevelEvent.PARTICLE_HUGE_EXPLOSION_SEED, x, y, z));
		return packets;
	}

	//TODO: Send chuck again. It seems too many updates make chunks disappear or something..
	public static ClientBoundPacketData create(ProtocolVersion version, float x, float y, float z, float radius, Position[] blocks) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.EXPLODE, version);
		serializer.writeFloatLE(x);
		serializer.writeFloatLE(y);
		serializer.writeFloatLE(z);
		serializer.writeFloatLE(radius * 100);
		VarNumberSerializer.writeVarInt(serializer, blocks.length);
		for (Position b : blocks) {
			VarNumberSerializer.writeSVarInt(serializer, b.getX());
			VarNumberSerializer.writeSVarInt(serializer, b.getY());
			VarNumberSerializer.writeSVarInt(serializer, b.getZ());
		}
		return serializer;
	}


}
