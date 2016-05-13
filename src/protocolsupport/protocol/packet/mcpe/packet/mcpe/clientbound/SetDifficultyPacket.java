package protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.PEPacketIDs;

public class SetDifficultyPacket implements ClientboundPEPacket {

	protected int difficulty;

	public SetDifficultyPacket(int difficulty) {
		this.difficulty = difficulty;
	}

	@Override
	public int getId() {
		return PEPacketIDs.SET_DIFFICULTY_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeInt(difficulty);
		return this;
	}

}
