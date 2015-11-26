package protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound;

import java.util.Collections;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.ContainerPlayer;
import net.minecraft.server.v1_8_R3.ContainerWorkbench;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.InventoryCrafting;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import net.minecraft.server.v1_8_R3.BlockWorkbench.TileEntityContainerWorkbench;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.packet.SynchronizedHandleNMSPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.utils.InventoryUtils;

public class CraftPacket implements ServerboundPEPacket {

	protected int windowId;
	protected int type;
	protected ItemStack[] ingredients;
	protected ItemStack result;

	@Override
	public int getId() {
		return PEPacketIDs.CRAFTING_EVENT_PACKET;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		windowId = serializer.readByte();
		type = serializer.readInt();
		serializer.readUUID();
		ingredients = new ItemStack[serializer.readInt()];
		for (int i = 0; i < ingredients.length; i++) {
			ingredients[i] = serializer.readItemStack();
		}
		int resltSize = serializer.readInt();
		if (resltSize != 1) {
			throw new DecoderException("Strange crafting with more than 1 resulting itemstack");
		}
		result = serializer.readItemStack();
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom() throws Exception {
		return Collections.singletonList(new SynchronizedHandleNMSPacket<PlayerConnection>() {
			@Override
			public void handle0(PlayerConnection listener) {
				EntityPlayer player = listener.player;
				Container active = player.activeContainer;
				ItemStack[] ingrs = null;
				InventoryCrafting craftingInv = null;
				IInventory resultInventory = null;
				if (type == 0) {
					if (!(active instanceof ContainerPlayer)) {
						player.closeInventory();
					}
					active = player.activeContainer;
					ContainerPlayer containerPlayer = ((ContainerPlayer) active);
					ingrs = trim(ingredients);
					craftingInv = containerPlayer.craftInventory;
					resultInventory = containerPlayer.resultInventory;
				}
				if (type == 2) {
					//we never actually open workbench serverside to player because client doesn't notify server about workbench close, so we have to create temporary one
					createWorkbench(player);
					active = player.activeContainer;
					//check just in case opening inventory failed due to cancelled event
					if (active instanceof ContainerWorkbench) {
						ContainerWorkbench containerWorkbench = ((ContainerWorkbench) active);
						ingrs = ingredients;
						craftingInv = containerWorkbench.craftInventory;
						resultInventory = containerWorkbench.resultInventory;
					}
				}
				if (craftingInv != null && resultInventory != null && ingrs != null) {
					boolean hasItems = findAndMove(ingrs, player.inventory, craftingInv);
					ItemStack result = resultInventory.getItem(0);
					if (hasItems && result != null) {
						InventoryUtils.add(player, result, true);
					} else {
						addAll(player, craftingInv.getContents());
					}
					clearCrafting(craftingInv, resultInventory);
				}
				player.closeInventory();
				player.updateInventory(player.defaultContainer);
			}
		});
	}

	protected static void createWorkbench(EntityPlayer player) {
		TileEntityContainerWorkbench workbench = new TileEntityContainerWorkbench(player.world, BlockPosition.ZERO);
		player.openTileEntity(workbench);
		player.activeContainer.checkReachable = false;
	}

	protected static ItemStack[] trim(ItemStack[] ingrs) {
		ItemStack[] items = new ItemStack[4];
		items[0] = ingrs[0];
		items[1] = ingrs[1];
		items[2] = ingrs[3];
		items[3] = ingrs[4];
		return items;
	}

	protected static boolean findAndMove(ItemStack[] toFind, PlayerInventory from, InventoryCrafting crafting) {
		ItemStack[] items = from.items;
		for (int i = 0; i < toFind.length; i++) {
			ItemStack ingr = toFind[i];
			if (ingr == null) {
				continue;
			}
			boolean found = false;
			for (ItemStack itemstack : items) {
				if (matchIngr(ingr, itemstack)) {
					ItemStack fIngr = InventoryUtils.takeAmount(itemstack, ingr.count);
					crafting.setItem(i, fIngr);
					found = true;
					break;
				}
			}
			if (!found) {
				return false;
			}
		}
		return true;
	}

	protected static boolean matchIngr(ItemStack ingr, ItemStack itemstack) {
		if (ingr.count < 0) {
			return false;
		}
		if (itemstack == null) {
			return false;
		}
		if (itemstack.getItem() != ingr.getItem()) {
			return false;
		}
		if (itemstack.count < ingr.count) {
			return false;
		}
		return (ingr.getData() == -1) || (ingr.getData() == itemstack.getData());
	}

	protected void addAll(EntityPlayer player, ItemStack[] itemstacks) {
		for (ItemStack itemstack : itemstacks) {
			InventoryUtils.add(player, itemstack, true);
		}
	}

	protected static void clearCrafting(InventoryCrafting craftingInv, IInventory resultInv) {
		for (int i = 0; i < craftingInv.getSize(); i++) {
			craftingInv.setItem(i, null);
		}
		resultInv.setItem(0, null);
	}

}
