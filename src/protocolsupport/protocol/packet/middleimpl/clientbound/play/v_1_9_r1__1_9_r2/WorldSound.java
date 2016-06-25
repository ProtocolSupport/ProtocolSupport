package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2;

import java.io.IOException;

import net.minecraft.server.v1_10_R1.SoundEffect;
import net.minecraft.server.v1_10_R1.SoundEffects;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldSound;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class WorldSound extends MiddleWorldSound<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_9_1)) {
			int elytraFlySoundId = SoundEffect.a.a(SoundEffects.aK);
			if (id == elytraFlySoundId) {
				return RecyclableEmptyList.get();
			} else if (id > elytraFlySoundId) {
				id--;
			}
		}
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_WORLD_SOUND_ID, version);
		serializer.writeVarInt(id);
		serializer.writeVarInt(category);
		serializer.writeInt(x);
		serializer.writeInt(y);
		serializer.writeInt(z);
		serializer.writeFloat(volume);
		serializer.writeByte((int) (pitch * 63.5));
		return RecyclableSingletonList.create(serializer);
	}

}
