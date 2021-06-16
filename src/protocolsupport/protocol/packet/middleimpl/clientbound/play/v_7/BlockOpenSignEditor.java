package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

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
		ClientBoundPacketData blockopensigneditor = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_SIGN_EDITOR);
		PositionSerializer.writeLegacyPositionI(blockopensigneditor, position);
		codec.writeClientbound(blockopensigneditor);
	}

}
