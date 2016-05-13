package protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.util.NumberConversions;

import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.PacketPlayOutMapChunk;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.both.MovePlayerPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePosition;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Position extends MiddlePosition<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		storage.getPEStorage().setPlayerChunkCoords(NumberConversions.floor(x) >> 4, NumberConversions.floor(z) >> 4);
		EntityPlayer eplayer = ((CraftPlayer) player).getHandle();
		eplayer.playerConnection.sendPacket(new PacketPlayOutMapChunk(
			eplayer.world.getChunkAt(NumberConversions.floor(x) >> 4, NumberConversions.floor(z) >> 4), 255
		));
		return RecyclableSingletonList.create(new MovePlayerPacket(player.getEntityId(), (float) x, (float) y, (float) z, yaw, yaw, pitch, false));
	}

}
