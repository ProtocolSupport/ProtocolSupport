package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleSetBeaconEffect extends ServerBoundMiddlePacket {

	protected MiddleSetBeaconEffect(IMiddlePacketInit init) {
		super(init);
	}

	protected int primary;
	protected int secondary;

	@Override
	protected void write() {
		io.writeServerbound(create(primary, secondary));
	}

	public static ServerBoundPacketData create(int primary, int secondary) {
		ServerBoundPacketData serializer = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_SET_BEACON_EFFECT);
		VarNumberCodec.writeVarInt(serializer, primary);
		VarNumberCodec.writeVarInt(serializer, secondary);
		return serializer;
	}

}
