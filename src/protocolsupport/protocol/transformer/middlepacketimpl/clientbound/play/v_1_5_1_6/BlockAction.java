package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_5_1_6;

import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleBlockAction;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class BlockAction extends MiddleBlockAction<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeInt(position.getX());
		serializer.writeShort(position.getY());
		serializer.writeInt(position.getZ());
		serializer.writeByte(info1);
		serializer.writeByte(info2);
		serializer.writeShort(type);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_BLOCK_ACTION_ID, serializer));
	}

}
