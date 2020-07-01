package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractSoundCollectEffect;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.WorldCustomSound;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class CollectEffect extends AbstractSoundCollectEffect {

	public CollectEffect(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeCollectEffect() {
		ClientBoundPacketData collecteffect = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_COLLECT_EFFECT);
		VarNumberSerializer.writeVarInt(collecteffect, entityId);
		VarNumberSerializer.writeVarInt(collecteffect, collectorId);
		codec.write(collecteffect);
	}

	@Override
	protected void writeCollectSound(String sound, double x, double y, double z, float volume, float pitch) {
		codec.write(WorldCustomSound.create(version, x, y, z, sound, volume, pitch));
	}

}
