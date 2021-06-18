package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleCombatDeath extends ClientBoundMiddlePacket {

	protected MiddleCombatDeath(MiddlePacketInit init) {
		super(init);
	}

	protected int playerId;
	protected int killerId;
	protected BaseComponent message;

	@Override
	protected void decode(ByteBuf serverdata) {
		playerId = VarNumberCodec.readVarInt(serverdata);
		killerId = serverdata.readInt();
		message = ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(serverdata), true);
	}

}
