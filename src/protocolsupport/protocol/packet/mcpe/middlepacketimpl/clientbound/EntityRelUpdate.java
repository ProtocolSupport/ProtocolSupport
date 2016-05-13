package protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;

import net.minecraft.server.v1_9_R2.Entity;
import net.minecraft.server.v1_9_R2.EntityPlayer;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.both.MovePlayerPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound.MoveEntityPacket;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.PacketDataSerializer;
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
		Entity entity = ((CraftWorld) player.getWorld()).getHandle().getEntity(entityId);
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
