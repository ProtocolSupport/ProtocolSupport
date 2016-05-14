package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleEntityEffectAdd<T> extends MiddleEntity<T> {

	protected int effectId;
	protected int amplifier;
	protected int duration;
	protected boolean hideParticles;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		super.readFromServerData(serializer);
		effectId = serializer.readByte();
		amplifier = serializer.readByte();
		duration = serializer.readVarInt();
		hideParticles = serializer.readBoolean();
	}

}
