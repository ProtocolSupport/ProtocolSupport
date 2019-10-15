package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockOpenSignEditor;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockOpenSignEditor extends MiddleBlockOpenSignEditor {

	public BlockOpenSignEditor(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		if (version == ProtocolVersion.MINECRAFT_1_6_1) {
			return RecyclableEmptyList.get();
		}
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SIGN_EDITOR);
		serializer.writeByte(0);
		PositionSerializer.writeLegacyPositionI(serializer, position);
		return RecyclableSingletonList.create(serializer);
	}

}
