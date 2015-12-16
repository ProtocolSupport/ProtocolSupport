package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v1_5_1_6_1_7;

import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleEntityEffectAdd;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.transformer.middlepacketimpl.SupportedVersions;
import protocolsupport.protocol.typeskipper.id.IdSkipper;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
@SupportedVersions({ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_6_4, ProtocolVersion.MINECRAFT_1_6_2, ProtocolVersion.MINECRAFT_1_5_2})
public class EntityEffectAdd extends MiddleEntityEffectAdd<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		if (IdSkipper.EFFECT.getTable(version).shouldSkip(effectId)) {
			return Collections.emptyList();
		}
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeInt(entityId);
		serializer.writeByte(effectId);
		serializer.writeByte(amplifier);
		serializer.writeShort(duration);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_ENTITY_EFFECT_ADD_ID, serializer));
	}

}
