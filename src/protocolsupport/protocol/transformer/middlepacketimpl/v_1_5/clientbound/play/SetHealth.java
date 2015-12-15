package protocolsupport.protocol.transformer.middlepacketimpl.v_1_5.clientbound.play;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.server.v1_8_R3.MathHelper;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleSetHealth;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

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
