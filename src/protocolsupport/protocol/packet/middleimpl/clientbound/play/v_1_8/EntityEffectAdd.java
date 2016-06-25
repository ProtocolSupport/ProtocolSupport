package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEffectAdd;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.protocol.typeskipper.id.IdSkipper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityEffectAdd extends MiddleEntityEffectAdd<RecyclableCollection<PacketData>>  {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		if (IdSkipper.EFFECT.getTable(version).shouldSkip(effectId)) {
			return RecyclableEmptyList.get();
		}
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_ENTITY_EFFECT_ADD_ID, version);
		serializer.writeVarInt(entityId);
		serializer.writeByte(effectId);
		serializer.writeByte(amplifier);
		serializer.writeVarInt(duration);
		serializer.writeBoolean(hideParticles);
		return RecyclableSingletonList.create(serializer);
	}

}
