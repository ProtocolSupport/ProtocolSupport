package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7__1_8__1_9_r1__1_9_r2__1_10__1_11;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleExplosion;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Explosion extends MiddleExplosion<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_EXPLOSION_ID, version);
		serializer.writeFloat(x);
		serializer.writeFloat(y);
		serializer.writeFloat(z);
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
		return RecyclableSingletonList.create(serializer);
	}

}
