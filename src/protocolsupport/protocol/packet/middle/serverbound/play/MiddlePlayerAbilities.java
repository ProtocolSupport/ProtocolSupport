package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddlePlayerAbilities extends ServerBoundMiddlePacket {

	public MiddlePlayerAbilities(ConnectionImpl connection) {
		super(connection);
	}

	protected int flags;
	protected float flySpeed;
	protected float walkSpeed;

	@Override
	public void writeToServer() {
		ServerBoundPacketData abilities = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_ABILITIES);
		abilities.writeByte(flags);
		abilities.writeFloat(flySpeed);
		abilities.writeFloat(walkSpeed);
		codec.read(abilities);
	}

}
