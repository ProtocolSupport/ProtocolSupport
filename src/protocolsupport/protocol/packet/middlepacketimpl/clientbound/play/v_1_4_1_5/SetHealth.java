package protocolsupport.protocol.packet.middlepacketimpl.clientbound.play.v_1_4_1_5;

import net.minecraft.server.v1_9_R1.MathHelper;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middlepacket.clientbound.play.MiddleSetHealth;
import protocolsupport.protocol.packet.middlepacketimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SetHealth extends MiddleSetHealth<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_UPDATE_HEALTH_ID, version);
		serializer.writeShort(MathHelper.f(health));
		serializer.writeShort(food);
		serializer.writeFloat(saturation);
		return RecyclableSingletonList.create(serializer);
	}

}
