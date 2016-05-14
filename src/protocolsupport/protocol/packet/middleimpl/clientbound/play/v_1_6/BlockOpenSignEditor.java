package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_6;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockOpenSignEditor;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockOpenSignEditor extends MiddleBlockOpenSignEditor<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		if (version == ProtocolVersion.MINECRAFT_1_6_1) {
			return RecyclableEmptyList.get();
		}
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_SIGN_EDITOR_ID, version);
		serializer.writeByte(0);
		serializer.writeLegacyPositionI(position);
		return RecyclableSingletonList.create(serializer);
	}

}
