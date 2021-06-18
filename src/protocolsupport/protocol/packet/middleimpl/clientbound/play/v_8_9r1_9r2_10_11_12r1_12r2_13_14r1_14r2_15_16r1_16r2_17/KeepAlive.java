package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleKeepAlive;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class KeepAlive extends MiddleKeepAlive {

	public KeepAlive(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData keepalive = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_KEEP_ALIVE);
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_12_1)) {
			VarNumberCodec.writeVarInt(keepalive, keepAliveId);
		} else {
			keepalive.writeLong(keepAliveId);
		}
		codec.writeClientbound(keepalive);
	}

}
