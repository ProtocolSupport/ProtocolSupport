package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2_15_16r1_16r2_17r1_17r2;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleStopSound;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class StopSound extends MiddleStopSound {

	public StopSound(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData stopsound = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_STOP_SOUND);
		stopsound.writeByte((source != -1 ? FLAG_SOURCE : 0) | (name != null ? FLAG_NAME : 0));
		if (source != -1) {
			VarNumberCodec.writeVarInt(stopsound, source);
		}
		if (name != null) {
			StringCodec.writeVarIntUTF8String(stopsound, name);
		}
		codec.writeClientbound(stopsound);
	}

}
