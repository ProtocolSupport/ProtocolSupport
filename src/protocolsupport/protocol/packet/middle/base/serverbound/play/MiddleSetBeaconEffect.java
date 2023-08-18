package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleSetBeaconEffect extends ServerBoundMiddlePacket {

	protected MiddleSetBeaconEffect(IMiddlePacketInit init) {
		super(init);
	}

	protected Integer primary;
	protected Integer secondary;

	@Override
	protected void write() {
		io.writeServerbound(create(primary, secondary));
	}

	public static ServerBoundPacketData create(Integer primary, Integer secondary) {
		ServerBoundPacketData serializer = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_SET_BEACON_EFFECT);
		OptionalCodec.writeOptional(serializer, primary, VarNumberCodec::writeVarInt);
		OptionalCodec.writeOptional(serializer, secondary, VarNumberCodec::writeVarInt);
		return serializer;
	}

}
