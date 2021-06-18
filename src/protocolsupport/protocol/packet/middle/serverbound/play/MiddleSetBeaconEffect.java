package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleSetBeaconEffect extends ServerBoundMiddlePacket {

	protected MiddleSetBeaconEffect(MiddlePacketInit init) {
		super(init);
	}

	protected int primary;
	protected int secondary;

	@Override
	protected void write() {
		codec.writeServerbound(create(primary, secondary));
	}

	public static ServerBoundPacketData create(int primary, int secondary) {
		ServerBoundPacketData serializer = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_SET_BEACON_EFFECT);
		VarNumberCodec.writeVarInt(serializer, primary);
		VarNumberCodec.writeVarInt(serializer, secondary);
		return serializer;
	}

}
