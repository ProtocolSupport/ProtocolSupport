package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleCombatDeath extends ClientBoundMiddlePacket {

	protected MiddleCombatDeath(IMiddlePacketInit init) {
		super(init);
	}

	protected int playerId;
	protected BaseComponent message;

	@Override
	protected void decode(ByteBuf serverdata) {
		playerId = VarNumberCodec.readVarInt(serverdata);
		message = ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(serverdata), true);
	}

}
