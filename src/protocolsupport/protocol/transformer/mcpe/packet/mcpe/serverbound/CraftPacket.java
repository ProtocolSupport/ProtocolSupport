package protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;

import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.ContainerPlayer;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.InventoryCrafting;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PlayerInventory;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.packet.SynchronizedHandleNMSPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;

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
			public void handle(PlayerConnection listener) {
				EntityPlayer player = listener.player;
				Container active = player.activeContainer;
				if (active instanceof ContainerPlayer) {
					ContainerPlayer containerPlayer = ((ContainerPlayer) active);
					InventoryCrafting craftingInv = containerPlayer.craftInventory;
					boolean hasItems = findAndMove(trim(ingredients), player.inventory, craftingInv);
					if (hasItems) {
						ItemStack result = containerPlayer.resultInventory.getItem(0);
						if (result != null) {
							moveItemStack(player, result);
							clearCrafting(containerPlayer);
						} else {
							moveAll(craftingInv, player.inventory);
						}
					}
				}
				player.updateInventory(player.activeContainer);
			}
		});
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
					ItemStack fIngr = takeAmount(itemstack, ingr.count);
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

	protected static void moveItemStack(EntityPlayer player, ItemStack result) {
		HashMap<Integer, org.bukkit.inventory.ItemStack> left = player.getBukkitEntity().getInventory().addItem(CraftItemStack.asCraftMirror(result));
		for (org.bukkit.inventory.ItemStack bitemstack : left.values()) {
			player.getBukkitEntity().getWorld().dropItem(player.getBukkitEntity().getLocation(), bitemstack);
		}
	}

	protected static void clearCrafting(ContainerPlayer containerPlayer) {
		IInventory crafting = containerPlayer.craftInventory;
		for (int i = 0; i < crafting.getSize(); i++) {
			crafting.setItem(i, null);
		}
		containerPlayer.resultInventory.setItem(0, null);
	}

	protected void moveAll(InventoryCrafting from, PlayerInventory to) {
		
	}

	protected static ItemStack takeAmount(ItemStack itemstack, int amount) {
		ItemStack clone = itemstack.cloneItemStack();
		itemstack.count -= amount;
		clone.count = amount;
		return clone;
	}

}
