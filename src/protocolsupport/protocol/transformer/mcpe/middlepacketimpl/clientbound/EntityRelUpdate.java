package protocolsupport.protocol.transformer.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityPlayer;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.MovePlayerPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.MoveEntityPacket;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityRelUpdate extends ClientBoundMiddlePacket<RecyclableCollection<? extends ClientboundPEPacket>> {

	private int entityId;

	@Override
	public boolean needsPlayer() {
		return true;
	}

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		entityId = serializer.readVarInt();
	}

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		Entity entity = ((CraftWorld) player.getWorld()).getHandle().a(entityId);
		if (entity != null) {
			if (entity instanceof EntityPlayer) {
				return RecyclableSingletonList.create(new MovePlayerPacket(
					entityId,
					(float) entity.locX, (float) entity.locY, (float) entity.locZ,
					entity.yaw, ((EntityPlayer) entity).aK, entity.pitch, entity.onGround
				));
			} else {
				return RecyclableSingletonList.create(new MoveEntityPacket(
					entityId,
					(float) entity.locX, (float) entity.locY, (float) entity.locZ,
					entity.yaw, entity.pitch
				));
			}
		}
		return RecyclableEmptyList.get();
	}

}
