package protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.util.NumberConversions;

import net.minecraft.server.v1_9_R2.BlockPosition;
import net.minecraft.server.v1_9_R2.EntityPlayer;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound.ContainerOpenPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound.SetBlocksPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound.SetBlocksPacket.UpdateBlockRecord;
import protocolsupport.protocol.packet.mcpe.utils.InventoryUtils;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class InventoryOpen extends MiddleInventoryOpen<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public boolean needsPlayer() {
		return true;
	}

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		EntityPlayer eplayer = ((CraftPlayer) player).getHandle();
		int x = NumberConversions.floor(eplayer.locX);
		int z = NumberConversions.floor(eplayer.locZ);
		int type = InventoryUtils.getType(invname);
		if (type != -1) {
			RecyclableArrayList<ClientboundPEPacket> packets = RecyclableArrayList.create();
			storage.getPEStorage().setFakeInventoryBlock(new BlockPosition(x, 0, z));
			packets.add(new SetBlocksPacket(new UpdateBlockRecord(x, 0, z, InventoryUtils.getFakeBlockForInvType(type), 0, SetBlocksPacket.FLAG_ALL_PRIORITY)));
			packets.add(new ContainerOpenPacket(windowId, type, slots, x, 0, z));
			return packets;
		} else {
			eplayer.closeInventory();
			return RecyclableEmptyList.get();
		}
	}

}
