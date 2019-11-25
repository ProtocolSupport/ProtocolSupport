package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.types.UsedHand;

public abstract class MiddleAnimation extends ServerBoundMiddlePacket {

	public MiddleAnimation(ConnectionImpl connection) {
		super(connection);
	}

	protected UsedHand hand;

	@Override
	public void writeToServer() {
		codec.read(create(hand));
	}

	public static ServerBoundPacketData create(UsedHand hand) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_ANIMATION);
		MiscSerializer.writeVarIntEnum(creator, hand);
		return creator;
	}

}
