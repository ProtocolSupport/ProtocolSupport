package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddlePlayerAbilities extends ServerBoundMiddlePacket {

	public MiddlePlayerAbilities(MiddlePacketInit init) {
		super(init);
	}

	protected int flags;

	@Override
	protected void writeToServer() {
		ServerBoundPacketData abilities = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_ABILITIES);
		abilities.writeByte(flags);
		codec.read(abilities);
	}

}
