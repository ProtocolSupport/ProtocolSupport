package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockOpenSignEditor;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;

public class BlockOpenSignEditor extends MiddleBlockOpenSignEditor {

	public BlockOpenSignEditor(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		if (version.isAfter(ProtocolVersion.MINECRAFT_1_6_1)) {
			ClientBoundPacketData blockopensigneditor = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SIGN_EDITOR);
			blockopensigneditor.writeByte(0);
			PositionSerializer.writeLegacyPositionI(blockopensigneditor, position);
		}
	}

}
