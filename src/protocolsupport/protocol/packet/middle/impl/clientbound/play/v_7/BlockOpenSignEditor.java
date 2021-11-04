package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_7;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleBlockOpenSignEditor;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;

public class BlockOpenSignEditor extends MiddleBlockOpenSignEditor implements IClientboundMiddlePacketV7 {

	public BlockOpenSignEditor(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData blockopensigneditor = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SIGN_EDITOR);
		PositionCodec.writePositionIII(blockopensigneditor, position);
		io.writeClientbound(blockopensigneditor);
	}

}
