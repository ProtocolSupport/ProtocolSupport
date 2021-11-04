package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddlePickItem extends ServerBoundMiddlePacket {

	protected MiddlePickItem(IMiddlePacketInit init) {
		super(init);
	}

	protected int slot;

	@Override
	protected void write() {
		io.writeServerbound(create(slot));
	}

	public static ServerBoundPacketData create(int slot) {
		ServerBoundPacketData serializer = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_PICK_ITEM);
		VarNumberCodec.writeVarInt(serializer, slot);
		return serializer;
	}

}
