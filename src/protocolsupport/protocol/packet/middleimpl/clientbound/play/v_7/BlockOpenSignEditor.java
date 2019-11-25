package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

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
	public void writeToClient() {
		ClientBoundPacketData blockopensigneditor = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_SIGN_EDITOR);
		PositionSerializer.writeLegacyPositionI(blockopensigneditor, position);
		codec.write(blockopensigneditor);
	}

}
