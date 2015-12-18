package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class SetGameModePacket implements ClientboundPEPacket {

	protected int gamemode;
	public SetGameModePacket(int gamemode) {
		this.gamemode = gamemode;
	}

	@Override
	public int getId() {
		return PEPacketIDs.GAME_TYPE_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeInt(gamemode);
		return this;
	}

}
