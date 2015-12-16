package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_5;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.server.v1_8_R3.MathHelper;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleSetHealth;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.transformer.middlepacketimpl.SupportedVersions;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
@SupportedVersions({ProtocolVersion.MINECRAFT_1_5_2})
public class SetHealth extends MiddleSetHealth<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeShort(MathHelper.f(health));
		serializer.writeShort(food);
		serializer.writeFloat(saturation);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_UPDATE_HEALTH_ID, serializer));
	}

}
