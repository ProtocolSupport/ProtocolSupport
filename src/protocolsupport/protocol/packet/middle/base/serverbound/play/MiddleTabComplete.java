package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleTabComplete extends ServerBoundMiddlePacket {

	protected MiddleTabComplete(IMiddlePacketInit init) {
		super(init);
	}

	protected int id;
	protected String string;

	@Override
	protected void write() {
		ServerBoundPacketData tabcomplete = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_TAB_COMPLETE);
		VarNumberCodec.writeVarInt(tabcomplete, id);
		StringCodec.writeVarIntUTF8String(tabcomplete, string);
		io.writeServerbound(tabcomplete);
	}

}
