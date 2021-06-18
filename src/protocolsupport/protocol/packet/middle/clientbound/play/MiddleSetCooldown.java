package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleSetCooldown extends ClientBoundMiddlePacket {

	protected MiddleSetCooldown(MiddlePacketInit init) {
		super(init);
	}

	protected int itemId;
	protected int cooldown;

	@Override
	protected void decode(ByteBuf serverdata) {
		itemId = VarNumberCodec.readVarInt(serverdata);
		cooldown = VarNumberCodec.readVarInt(serverdata);
	}

}
