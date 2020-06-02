package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockOpenSignEditor;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;

public class BlockOpenSignEditor extends MiddleBlockOpenSignEditor {

	public BlockOpenSignEditor(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		if (version.isAfter(ProtocolVersion.MINECRAFT_1_6_1)) {
			ClientBoundPacketData blockopensigneditor = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SIGN_EDITOR);
			blockopensigneditor.writeByte(0);
			PositionSerializer.writeLegacyPositionI(blockopensigneditor, position);
		}
	}

}
