package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_6;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleBlockOpenSignEditor;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;

public class BlockOpenSignEditor extends MiddleBlockOpenSignEditor implements IClientboundMiddlePacketV6 {

	public BlockOpenSignEditor(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		if (version.isAfter(ProtocolVersion.MINECRAFT_1_6_1)) {
			ClientBoundPacketData blockopensigneditor = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SIGN_EDITOR);
			blockopensigneditor.writeByte(0);
			PositionCodec.writePositionIII(blockopensigneditor, position);
		}
	}

}
