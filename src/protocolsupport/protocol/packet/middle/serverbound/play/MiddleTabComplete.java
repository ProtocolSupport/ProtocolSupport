package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleTabComplete extends ServerBoundMiddlePacket {

	protected MiddleTabComplete(MiddlePacketInit init) {
		super(init);
	}

	protected int id;
	protected String string;

	@Override
	protected void write() {
		ServerBoundPacketData tabcomplete = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_TAB_COMPLETE);
		VarNumberSerializer.writeVarInt(tabcomplete, id);
		StringSerializer.writeVarIntUTF8String(tabcomplete, string);
		codec.writeServerbound(tabcomplete);
	}

}
