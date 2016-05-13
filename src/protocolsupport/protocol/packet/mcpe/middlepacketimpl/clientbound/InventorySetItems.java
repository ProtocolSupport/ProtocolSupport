package protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;

import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.ItemStack;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.mcpe.PEPlayerInventory;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound.ContainerSetContentsPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventorySetItems;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class InventorySetItems extends MiddleInventorySetItems<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public boolean needsPlayer() {
		return true;
	}

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		EntityPlayer eplayer = ((CraftPlayer) player).getHandle();
		ItemStack[] packetitems = itemstacks.toArray(new ItemStack[itemstacks.size()]);
		for (int i = 0; i < packetitems.length; i++) {
			packetitems[i] = PEPlayerInventory.addSlotNumberTag(packetitems[i], PEPlayerInventory.toInvSlot(i, packetitems.length));
		}
		if (windowId == 0) {
			RecyclableArrayList<ContainerSetContentsPacket> packets = RecyclableArrayList.create();
			ItemStack[] inventory = new ItemStack[45];
			System.arraycopy(packetitems, 9, inventory, 0, inventory.length - 9);
			packets.add(new ContainerSetContentsPacket(PEPlayerInventory.PLAYER_INVENTORY_WID, inventory, ((PEPlayerInventory) eplayer.inventory).getHotbarRefs()));
			ItemStack[] armor = new ItemStack[4];
			System.arraycopy(packetitems, 5, armor, 0, armor.length);
			packets.add(new ContainerSetContentsPacket(PEPlayerInventory.PLAYER_ARMOR_WID, armor, ContainerSetContentsPacket.EMPTY_HOTBAR_SLOTS));
			return packets;
		} else {
			RecyclableArrayList<ContainerSetContentsPacket> packets = RecyclableArrayList.create();
			ItemStack[] inventory = new ItemStack[45];
			System.arraycopy(packetitems, packetitems.length - 36, inventory, 0, inventory.length - 9);
			packets.add(new ContainerSetContentsPacket(PEPlayerInventory.PLAYER_INVENTORY_WID, inventory, ((PEPlayerInventory) eplayer.inventory).getHotbarRefs()));
			ItemStack[] contitems = new ItemStack[packetitems.length - 36];
			System.arraycopy(packetitems, 0, contitems, 0, contitems.length);
			packets.add(new ContainerSetContentsPacket(windowId, contitems, ContainerSetContentsPacket.EMPTY_HOTBAR_SLOTS));
			return packets;
		}
	}

}
