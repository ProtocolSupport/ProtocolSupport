package protocolsupport.protocol.transformer.mcpe.packet.mcpe.both;

import io.netty.buffer.ByteBuf;

import java.lang.invoke.MethodHandle;
import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.ContainerAnvil;
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
import protocolsupport.utils.Utils;

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

	protected static final MethodHandle anvilRepairInventoryGetter = Utils.getFieldGetter(ContainerAnvil.class, "h");
	protected static final MethodHandle anvilResultInventoryGetter = Utils.getFieldGetter(ContainerAnvil.class, "g");

	@Override
	public List<? extends Packet<?>> transfrom() throws Exception {
		if (windowId != 0) {
			return Collections.singletonList(new SynchronizedHandleNMSPacket<PlayerConnection>() {
				@Override
				public void handle0(PlayerConnection listener) throws Throwable {
					EntityPlayer player = listener.player;
					Container activeContainer = player.activeContainer;
					if (activeContainer.windowId == windowId) {
						if (activeContainer instanceof ContainerChest) {
							moveItems(player, slot, itemstack, ((ContainerChest) activeContainer).e());
						}
						if (activeContainer instanceof ContainerAnvil) {
							if (slot >= 0 && slot < 2) {
								//try to take item from result slot first, maybe it will work
								boolean moved = attemptMoveToPlayer(player, 0, null, (IInventory) anvilResultInventoryGetter.invokeExact((ContainerAnvil) activeContainer));
								//move items in or out repair slots or clear it
								IInventory repInv = (IInventory) anvilRepairInventoryGetter.invokeExact((ContainerAnvil) activeContainer);
								if (!moved) {
									moveItems(player, slot, itemstack, repInv);
								} else {
									for (int i = 0; i < repInv.getSize(); i++) {
										repInv.setItem(i, null);
									}
									player.updateInventory(player.activeContainer);
								}
							} else if (slot == 2) {
								//rename item, we will never get take item packet with null itemstack because we will get and process repair item slots first
								//whoever did anvil logic and made inventory clientside - fuck you
								if (itemstack != null) {
									((ContainerAnvil) activeContainer).a(InventoryUtils.getName(itemstack));
								}
							}
						}
					}
				}
			});
		}
		return Collections.emptyList();
	}

	protected static void moveItems(EntityPlayer player, int slot, ItemStack itemstack, IInventory inventory) {
		ItemStack itemInSlot = inventory.getItem(slot);
		if (
			(itemInSlot == null && itemstack == null) ||
			(itemInSlot != null && itemstack != null && (itemInSlot.count == itemstack.count))
		) {
			return;
		}
		boolean moved = attemptMoveToPlayer(player, slot, itemstack, inventory);
		if (!moved) {
			attemptMoveToInventory(player, slot, itemstack, inventory);
		}
		player.updateInventory(player.activeContainer);
	}

	protected static void attemptMoveToInventory(EntityPlayer player, int slot, ItemStack itemstack, IInventory inventory) {
		ItemStack itemInSlot = inventory.getItem(slot);
		if (itemstack != null && (itemInSlot == null || itemInSlot.count < itemstack.count)) {
			//moved item to other inventory
			int toMove = itemInSlot == null ? itemstack.count : itemstack.count - itemInSlot.count;
			int slotFrom = PEPlayerInventory.getSlotNumber(itemstack);
			if (slotFrom != -1) {
				ItemStack from = player.inventory.getItem(slotFrom);
				if (from.count >= toMove) {
					ItemStack toAdd = InventoryUtils.takeAmount(from, toMove);
					if (from.count == 0) {
						player.inventory.setItem(slotFrom, null);
					}
					InventoryUtils.add(player, toAdd, false);
				}
			}
		}
	}

	protected static boolean attemptMoveToPlayer(EntityPlayer player, int slot, ItemStack itemstack, IInventory inventory) {
		ItemStack itemInSlot = inventory.getItem(slot);
		if (itemInSlot != null && (itemstack == null || itemInSlot.count > itemstack.count)) {
			//moved item to player inventory
			int toMove = itemstack == null ? itemInSlot.count : itemInSlot.count - itemstack.count;
			if (itemInSlot.count >= toMove) {
				ItemStack toAdd = InventoryUtils.takeAmount(itemInSlot, toMove);
				if (itemInSlot.count == 0) {
					inventory.setItem(slot, null);
				}
				InventoryUtils.add(player, toAdd, true);
				return true;
			}
		}
		return false;
	}

}
