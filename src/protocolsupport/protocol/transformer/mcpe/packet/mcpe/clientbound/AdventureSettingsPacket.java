package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class AdventureSettingsPacket implements ClientboundPEPacket {

	protected int flags;

	public AdventureSettingsPacket(boolean isCreative) {
		flags |= 0x20;
		if (isCreative) {
			flags |= 0x80;
		}
	}

	@Override
	public int getId() {
		return PEPacketIDs.ADVENTURE_SETTINGS_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeInt(flags);
		return this;
	}

}
