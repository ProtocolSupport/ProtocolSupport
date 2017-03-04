package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleEntityEffectAdd extends MiddleEntity {

	protected int effectId;
	protected int amplifier;
	protected int duration;
	protected boolean hideParticles;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		super.readFromServerData(serializer);
		effectId = serializer.readByte();
		amplifier = serializer.readByte();
		duration = serializer.readVarInt();
		hideParticles = serializer.readBoolean();
	}

}
