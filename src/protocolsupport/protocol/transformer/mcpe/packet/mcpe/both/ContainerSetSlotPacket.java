package protocolsupport.protocol.transformer.mcpe.packet.mcpe.both;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_8_R3.ContainerChest;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PlayerConnection;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.PEPlayerInventory;
import protocolsupport.protocol.transformer.mcpe.packet.SynchronizedHandleNMSPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.DualPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.utils.InventoryUtils;

public class ContainerSetSlotPacket implements DualPEPacket {

	protected int windowId;
	protected int slot;
	protected int hotbarslot;
	protected ItemStack itemstack;

	public ContainerSetSlotPacket() {
	}

	public ContainerSetSlotPacket(int windowId, int slot, ItemStack itemstack) {
		this.windowId = windowId;
		this.slot = slot;
		this.itemstack = itemstack;
	}

	public ContainerSetSlotPacket(int windowId, int slot, int hotbarslot, ItemStack itemstack) {
		this(windowId, slot, itemstack);
		this.hotbarslot = hotbarslot;
	}

	@Override
	public int getId() {
		return PEPacketIDs.CONTAINER_SET_SLOT_PACKET;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		windowId = serializer.readByte();
		slot = serializer.readShort();
		hotbarslot = serializer.readShort();
		itemstack = serializer.readItemStack();
		return this;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		serializer.writeByte(windowId);
		serializer.writeShort(slot);
		serializer.writeShort(hotbarslot);
		serializer.writeItemStack(itemstack);
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom() throws Exception {
		if (windowId != 0) {
			return Collections.singletonList(new SynchronizedHandleNMSPacket<PlayerConnection>() {
				@Override
				public void handle0(PlayerConnection listener) {
					EntityPlayer player = listener.player;
					if (player.activeContainer.windowId == windowId) {
						if (player.activeContainer instanceof ContainerChest) {
							IInventory chestInventory = ((ContainerChest) player.activeContainer).e();
							ItemStack itemInSlot = chestInventory.getItem(slot);
							if (itemstack != null && (itemInSlot == null || itemInSlot.count < itemstack.count)) {
								//moved item to chest
								int toMove = itemInSlot == null ? itemstack.count : itemstack.count - itemInSlot.count;
								int slotFrom = PEPlayerInventory.getSlotNumber(itemstack);
								if (slotFrom != -1) {
									ItemStack from = player.inventory.getItem(slotFrom);
									ItemStack toAdd = InventoryUtils.takeAmount(from, toMove);
									if (from.count == 0) {
										player.inventory.setItem(slotFrom, null);
									}
									InventoryUtils.add(player, toAdd, false);
								}
							} else if (itemInSlot != null && (itemstack == null || itemInSlot.count > itemstack.count)) {
								//moved item to player inventory
								int toMove = itemstack == null ? itemInSlot.count : itemInSlot.count - itemstack.count;
								ItemStack toAdd = InventoryUtils.takeAmount(itemInSlot, toMove);
								if (itemInSlot.count == 0) {
									chestInventory.setItem(slot, null);
								}
								InventoryUtils.add(player, toAdd, true);
							}
						}
					}
					player.updateInventory(player.activeContainer);
				}
			});
		}
		return Collections.emptyList();
	}

}
