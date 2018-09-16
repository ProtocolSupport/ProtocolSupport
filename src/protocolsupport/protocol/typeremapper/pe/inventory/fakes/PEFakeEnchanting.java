package protocolsupport.protocol.typeremapper.pe.inventory.fakes;

import org.bukkit.Material;

import protocolsupport.api.MaterialAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryEnchant;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.InventorySetItems;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.GodPacket.InvTransaction;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.typeremapper.pe.inventory.PEInventory.PESource;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class PEFakeEnchanting {

	private NetworkItemStack inputOutputSlot = NetworkItemStack.NULL;
	private NetworkItemStack lapisSlot = NetworkItemStack.NULL;
	private int[] optionXP   = 	new int[] { 0,  0,  0};
	private int[] optionEnch = 	new int[] {-1, -1, -1};
	private int[] optionLvl  = 	new int[] { 1,  1,  1};

	public void setInputOutputStack(NetworkItemStack inputOutputStack) {
		this.inputOutputSlot = inputOutputStack;
	}

	public NetworkItemStack getInput() {
		return inputOutputSlot;
	}

	public void setLapisStack(NetworkItemStack lapisStack) {
		this.lapisSlot = lapisStack;
	}

	public void updateOptionXP(int num, int xp) {
		optionXP[num] = xp;
	}

	public void updateOptionEnch(int num, int enchant) {
		optionEnch[num] = enchant;
	}

	public void updateOptionLevel(int num, int lvl) {
		optionLvl[num] = lvl;
	}

	public NetworkItemStack[] compileInventory() {
		NetworkItemStack[] contents = new NetworkItemStack[5];
		contents[0] = inputOutputSlot;
		contents[1] = lapisSlot;
		for (int i = 0; i < 3; i++) {
			//Create option item & nbt
			if (optionEnch[i] < 0) { contents[i+2] = NetworkItemStack.NULL; break;}
			NetworkItemStack option = inputOutputSlot.cloneItemStack();
			if (option.isNull()) { break; }
			NBTTagCompoundWrapper tag = (option.getNBT() == null || option.getNBT().isNull()) ?
			ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound() : option.getNBT();
			//Display
			if (!tag.hasKeyOfType("display", NBTTagType.COMPOUND)) {
				tag.setCompound("display", ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound());
			}
			NBTTagCompoundWrapper display = tag.getCompound("display");
			display.setString("Name", "Click to enchant");
			NBTTagListWrapper lore = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
			lore.addString("Requires");
			lore.addString(optionXP[i] + (optionXP[i] == 1 ? " Enchantment Level" : " Enchantment Levels"));
			lore.addString((i + 1) + " Lapis Lazuli");
			display.setList("Lore", lore);
			tag.setCompound("display", display);
			//Enchantment
			NBTTagListWrapper ench = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
			if(ench.isEmpty()) { ench.addCompound(ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound()); }
			ench.getCompound(0).setShort("id",  optionEnch[i]);
			ench.getCompound(0).setShort("lvl", optionLvl [i]);
			tag.setList("ench", ench);
			//Wrap up
			option.setNBT(tag);
			contents[i+2] = option;
		}
		return contents;
	}

	public ClientBoundPacketData updateInventory(NetworkDataCache cache, ProtocolVersion version) {
		return InventorySetItems.create(version, cache.getAttributesCache().getLocale(), cache.getWindowCache().getOpenedWindowId(), compileInventory());
	}

	public boolean handleInventoryClick(NetworkDataCache cache, InvTransaction transaction, RecyclableArrayList<ServerBoundPacketData> packets) {
		if (transaction.getSlot() == 0) {
			setInputOutputStack(transaction.getNewItem());
		} else if (transaction.getSlot() == 1 && (transaction.getNewItem().isNull() || MaterialAPI.getItemByNetworkId(transaction.getNewItem().getTypeId()) == Material.INK_SAC)) {
			setLapisStack(transaction.getNewItem());
		} else if ((transaction.getSlot() > 1 && transaction.getSlot() <= 4) && (transaction.getInventoryId() != PESource.POCKET_INVENTORY)) {
			//If and only if on of the three fake hopper option slots are clicked proceed with the enchanting.
			packets.add(MiddleInventoryEnchant.create(cache.getWindowCache().getOpenedWindowId(), transaction.getSlot() - 2));
			//Stop caching inventory transaction (not necessary when we enchant!)
			return false;
		}
		//Proceed with inventory transaction caching.
		return true;
	}

}
