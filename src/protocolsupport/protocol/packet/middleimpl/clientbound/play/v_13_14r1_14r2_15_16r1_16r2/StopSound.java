package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleStopSound;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class StopSound extends MiddleStopSound {

	public StopSound(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData stopsound = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_STOP_SOUND);
		stopsound.writeByte((source != -1 ? FLAG_SOURCE : 0) | (name != null ? FLAG_NAME : 0));
		if (source != -1) {
			VarNumberSerializer.writeVarInt(stopsound, source);
		}
		if (name != null) {
			StringSerializer.writeVarIntUTF8String(stopsound, name);
		}
		codec.writeClientbound(stopsound);
	}

}
