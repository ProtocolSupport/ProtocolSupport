package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v1_5_1_6_1_7;

import java.util.ArrayList;
import java.util.Collection;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleEntityDestroy;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.transformer.middlepacketimpl.SupportedVersions;
import protocolsupport.utils.Utils;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
@SupportedVersions({ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_6_4, ProtocolVersion.MINECRAFT_1_6_2, ProtocolVersion.MINECRAFT_1_5_2})
public class EntityDestroy extends MiddleEntityDestroy<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		Collection<PacketData> datas = new ArrayList<PacketData>();
		for (int[] part : Utils.splitArray(entityIds, 120)) {
			PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
			serializer.writeByte(part.length);
			for (int i = 0; i < part.length; i++) {
				serializer.writeInt(part[i]);
			}
			datas.add(new PacketData(ClientBoundPacket.PLAY_ENTITY_DESTROY_ID, serializer));
		}
		return datas;
	}

}
