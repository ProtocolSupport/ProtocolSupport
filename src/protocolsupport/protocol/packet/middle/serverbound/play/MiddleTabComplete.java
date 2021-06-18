package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleTabComplete extends ServerBoundMiddlePacket {

	protected MiddleTabComplete(MiddlePacketInit init) {
		super(init);
	}

	protected int id;
	protected String string;

	@Override
	protected void write() {
		ServerBoundPacketData tabcomplete = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_TAB_COMPLETE);
		VarNumberCodec.writeVarInt(tabcomplete, id);
		StringCodec.writeVarIntUTF8String(tabcomplete, string);
		codec.writeServerbound(tabcomplete);
	}

}
