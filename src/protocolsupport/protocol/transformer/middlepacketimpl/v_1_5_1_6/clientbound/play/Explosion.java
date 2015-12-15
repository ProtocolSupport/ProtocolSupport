package protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play;

import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleExplosion;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class Explosion extends MiddleExplosion<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeDouble(x);
		serializer.writeDouble(y);
		serializer.writeDouble(z);
		serializer.writeFloat(radius);
		serializer.writeInt(blocks.length);
		for (AffectedBlock block : blocks) {
			serializer.writeByte(block.offX);
			serializer.writeByte(block.offY);
			serializer.writeByte(block.offZ);
		}
		serializer.writeFloat(pMotX);
		serializer.writeFloat(pMotY);
		serializer.writeFloat(pMotZ);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_EXPLOSION_ID, serializer));
	}

}
