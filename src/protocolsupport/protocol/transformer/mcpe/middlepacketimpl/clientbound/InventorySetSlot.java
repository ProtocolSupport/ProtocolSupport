package protocolsupport.protocol.transformer.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventorySetSlot;
import protocolsupport.protocol.transformer.mcpe.PEPlayerInventory;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.ContainerSetSlotPacket;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventorySetSlot extends MiddleInventorySetSlot<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public boolean needsPlayer() {
		return true;
	}

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		if (windowId == 0) {
			itemstack = PEPlayerInventory.addSlotNumberTag(itemstack, PEPlayerInventory.toInvSlot(slot, 45));
			if (slot >= 9 && slot < 45) {
				return RecyclableSingletonList.create(new ContainerSetSlotPacket(
					PEPlayerInventory.PLAYER_INVENTORY_WID, slot - 9,
					((PEPlayerInventory) ((CraftPlayer) player).getHandle().inventory).getHotbarSlotFor(slot), itemstack
				));
			} else if (slot >= 5 && slot < 9) {
				return RecyclableSingletonList.create(new ContainerSetSlotPacket(PEPlayerInventory.PLAYER_ARMOR_WID, slot - 5, itemstack));
			}
		} else {
			return RecyclableSingletonList.create(new ContainerSetSlotPacket(windowId, slot, itemstack));
		}
		return RecyclableEmptyList.get();
	}

}
