package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleWorldEvent;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
public class WorldEvent extends MiddleWorldEvent<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) throws IOException {
		if (effectId == 2001) {
			data = IdRemapper.BLOCK.getTable(version).getRemap(data & 0xFFF);
		}
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeInt(effectId);
		serializer.writeInt(position.getX());
		serializer.writeByte(position.getY());
		serializer.writeInt(position.getZ());
		serializer.writeInt(data);
		serializer.writeBoolean(disableRelative);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_WORLD_EVENT_ID, serializer));
	}

}
