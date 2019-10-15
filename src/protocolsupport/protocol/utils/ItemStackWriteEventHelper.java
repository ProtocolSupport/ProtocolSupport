package protocolsupport.protocol.utils;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.events.ItemStackWriteEvent;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.zplatform.ServerPlatform;

@SuppressWarnings("deprecation")
public class ItemStackWriteEventHelper {

	public static void callEvent(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		if (ItemStackWriteEvent.getHandlerList().getRegisteredListeners().length > 0) {
			ItemStackWriteEvent event = new ItemStackWriteEvent(version, locale, new WrappedNetworkItemStack(itemstack));
			Bukkit.getPluginManager().callEvent(event);
			List<String> additionalLore = event.getAdditionalLore();
			BaseComponent forcedDisplayName = event.getForcedDisplayName();
			if ((forcedDisplayName != null) || !additionalLore.isEmpty()) {
				NBTCompound rootTag = CommonNBT.getOrCreateRootTag(itemstack);
				NBTCompound displayNBT = CommonNBT.getOrCreateDisplayTag(rootTag);

				if (forcedDisplayName != null) {
					displayNBT.setTag(CommonNBT.DISPLAY_NAME, new NBTString(ChatAPI.toJSON(forcedDisplayName)));
				}

				if (!additionalLore.isEmpty()) {
					NBTList<NBTString> loreNBT = displayNBT.getTagListOfType(CommonNBT.DISPLAY_LORE, NBTType.STRING);
					if (loreNBT == null) {
						loreNBT = new NBTList<>(NBTType.STRING);
					}
					for (String lore : additionalLore) {
						loreNBT.addTag(new NBTString(ChatAPI.toJSON(new TextComponent(lore))));
					}
					displayNBT.setTag(CommonNBT.DISPLAY_LORE, loreNBT);
				}
			}
		}
	}

	public static class WrappedNetworkItemStack extends ItemStack {

		protected final NetworkItemStack itemstack;
		public WrappedNetworkItemStack(NetworkItemStack itemstack) {
			super(ItemMaterialLookup.getByRuntimeId(itemstack.getTypeId()), itemstack.getAmount());
			this.itemstack = itemstack;
		}

		protected static UnsupportedOperationException reject() {
			throw new UnsupportedOperationException("Can't modify wrapped stack");
		}

		protected ItemStack bukkitItemStack;

		protected ItemStack getOrCreateBukkitItemStack() {
			if (bukkitItemStack == null) {
				bukkitItemStack = ServerPlatform.get().getMiscUtils().createItemStackFromNetwork(itemstack);
			}
			return bukkitItemStack;
		}

		@Override
		public ItemMeta getItemMeta() {
			return getOrCreateBukkitItemStack().getItemMeta();
		}

		@Override
		public ItemStack clone() {
			return getOrCreateBukkitItemStack().clone();
		}

		@Override
		public boolean equals(Object obj) {
			return this == obj;
		}

		@Override
		public int hashCode() {
			return System.identityHashCode(this);
		}

		@Override
		public void setType(Material type) {
			throw reject();
		}

		@Override
		public void setAmount(int amount) {
			throw reject();
		}

		@Override
		public void setData(MaterialData data) {
			throw reject();
		}

		@Override
		public void setDurability(short durability) {
			throw reject();
		}

		@Override
		public boolean setItemMeta(ItemMeta itemMeta) {
			throw reject();
		}

		@Override
		public void addEnchantment(Enchantment ench, int level) {
			throw reject();
		}

		@Override
		public void addEnchantments(Map<Enchantment, Integer> arg0) {
			throw reject();
		}

		@Override
		public void addUnsafeEnchantment(Enchantment ench, int level) {
			throw reject();
		}

		@Override
		public void addUnsafeEnchantments(Map<Enchantment, Integer> arg0) {
			throw reject();
		}

		@Override
		public void setLore(List<String> lore) {
			throw reject();
		}

		@Override
		public ItemStack add() {
			throw reject();
		}

		@Override
		public ItemStack add(int qty) {
			throw reject();
		}

		@Override
		public ItemStack subtract() {
			throw reject();
		}

		@Override
		public ItemStack subtract(int qty) {
			throw reject();
		}

		@Override
		public void addItemFlags(ItemFlag... itemFlags) {
			throw reject();
		}

		@Override
		public ItemStack ensureServerConversions() {
			throw reject();
		}

	}

}
