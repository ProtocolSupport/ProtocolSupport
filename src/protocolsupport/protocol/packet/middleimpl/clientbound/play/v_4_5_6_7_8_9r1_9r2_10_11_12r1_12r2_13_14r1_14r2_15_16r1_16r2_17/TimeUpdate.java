package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTimeUpdate;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class TimeUpdate extends MiddleTimeUpdate {

	public TimeUpdate(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData timeupdate = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_UPDATE_TIME);
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_5_2)) {
			timeOfDay = Math.abs(timeOfDay);
		}
		timeupdate.writeLong(worldAge);
		timeupdate.writeLong(timeOfDay);
		codec.writeClientbound(timeupdate);
	}

}
