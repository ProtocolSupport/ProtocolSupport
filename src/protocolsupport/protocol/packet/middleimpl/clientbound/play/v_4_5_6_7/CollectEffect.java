package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractSoundCollectEffect;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.WorldCustomSound;

public class CollectEffect extends AbstractSoundCollectEffect {

	public CollectEffect(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeCollectEffect() {
		ClientBoundPacketData collecteffect = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_COLLECT_EFFECT);
		collecteffect.writeInt(entityId);
		collecteffect.writeInt(collectorId);
		codec.write(collecteffect);
	}

	@Override
	protected void writeCollectSound(String sound, double x, double y, double z, float volume, float pitch) {
		codec.write(WorldCustomSound.create(version, x, y, z, sound, volume, pitch));
	}

}
