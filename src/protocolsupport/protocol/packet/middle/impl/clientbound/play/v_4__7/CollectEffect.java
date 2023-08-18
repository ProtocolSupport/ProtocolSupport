package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__7;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8.AbstractSoundCollectEffect;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8.WorldSound;

public class CollectEffect extends AbstractSoundCollectEffect implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7 {

	public CollectEffect(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeCollectEffect() {
		ClientBoundPacketData collecteffect = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_COLLECT_EFFECT);
		collecteffect.writeInt(entityId);
		collecteffect.writeInt(collectorId);
		io.writeClientbound(collecteffect);
	}

	@Override
	protected void writeCollectSound(String sound, double x, double y, double z, float volume, float pitch) {
		io.writeClientbound(WorldSound.createCustomSound(version, x, y, z, sound, volume, pitch));
	}

}
