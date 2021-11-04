package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleNameItem extends ServerBoundMiddlePacket {

	protected MiddleNameItem(IMiddlePacketInit init) {
		super(init);
	}

	protected String name;

	@Override
	protected void write() {
		io.writeServerbound(create(name));
	}

	public static ServerBoundPacketData create(String name) {
		ServerBoundPacketData serializer = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_NAME_ITEM);
		StringCodec.writeVarIntUTF8String(serializer, name);
		return serializer;
	}

}
