package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6;

import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleBlockChangeSingle;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
public class BlockChangeSingle extends MiddleBlockChangeSingle<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeInt(position.getX());
		serializer.writeByte(position.getY());
		serializer.writeInt(position.getZ());
		serializer.writeShort(IdRemapper.BLOCK.getTable(version).getRemap(id >> 4));
		serializer.writeByte(id & 0xF);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_BLOCK_CHANGE_SINGLE_ID, serializer));
	}

}
