package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5;

import net.minecraft.server.v1_10_R1.MathHelper;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetHealth;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

import java.util.UUID;

public class SetHealth extends MiddleSetHealth<RecyclableCollection<PacketData>> {

	@Override
	public boolean needsPlayer() {
		return true;
	}

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_UPDATE_HEALTH_ID, version);
		Integer hp = MathHelper.f(health);
		UUID uuid = player.getUniqueId();
		Double maxHealth = storage.getMaxHealth(uuid);
		if ((maxHealth != null && !maxHealth.equals(player.getMaxHealth())) ||
				maxHealth == null) {
			storage.addMaxHealth(uuid, maxHealth = player.getMaxHealth());
		}
		if (maxHealth > 20) {
			hp = MathHelper.f(health / (player.getMaxHealth() / 20));
		}
		serializer.writeShort(hp);
		serializer.writeShort(food);
		serializer.writeFloat(saturation);
		return RecyclableSingletonList.create(serializer);
	}

}
