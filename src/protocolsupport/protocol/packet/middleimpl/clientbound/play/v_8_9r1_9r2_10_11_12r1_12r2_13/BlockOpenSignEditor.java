package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13;

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
		ClientBoundPacketData blockopensigneditor = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SIGN_EDITOR);
		PositionSerializer.writeLegacyPositionL(blockopensigneditor, position);
		codec.writeClientbound(blockopensigneditor);
	}

}
