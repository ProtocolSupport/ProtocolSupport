package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockOpenSignEditor;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;

public class BlockOpenSignEditor extends MiddleBlockOpenSignEditor {

	public BlockOpenSignEditor(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData blockopensigneditor = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SIGN_EDITOR);
		PositionSerializer.writeLegacyPositionI(blockopensigneditor, position);
		codec.write(blockopensigneditor);
	}

}
