package protocolsupport.protocol.serializer;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.utils.types.MerchantData;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.protocol.utils.types.MerchantData.TradeOffer;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;

public class MerchantDataSerializer {

	public static MerchantData readMerchantData(ByteBuf from, ProtocolVersion version, String locale) {
		MerchantData merchdata = new MerchantData(from.readInt());
		int count = from.readUnsignedByte();
		for (int i = 0; i < count; i++) {
			ItemStackWrapper itemstack1 = ItemStackSerializer.readItemStack(from, version, locale, false);
			ItemStackWrapper result = ItemStackSerializer.readItemStack(from, version, locale, false);
			ItemStackWrapper itemstack2 = ItemStackWrapper.NULL;
			if (from.readBoolean()) {
				itemstack2 = ItemStackSerializer.readItemStack(from, version, locale, false);
			}
			boolean disabled = from.readBoolean();
			int uses = 0;
			int maxuses = 7;
			if (isUsingUsesCount(version)) {
				uses = from.readInt();
				maxuses = from.readInt();
			}
			merchdata.addOffer(new TradeOffer(itemstack1, itemstack2, result, disabled ? maxuses : uses, maxuses));
		}
		return merchdata;
	}

	public static void writeMerchantData(ByteBuf to, ProtocolVersion version, String locale, MerchantData merchdata) {
		to.writeInt(merchdata.getWindowId());
		to.writeByte(merchdata.getOffers().size());
		for (TradeOffer offer : merchdata.getOffers()) {
			ItemStackSerializer.writeItemStack(to, version, locale, offer.getItemStack1(), true);
			ItemStackSerializer.writeItemStack(to, version, locale, offer.getResult(), true);
			to.writeBoolean(offer.hasItemStack2());
			if (offer.hasItemStack2()) {
				ItemStackSerializer.writeItemStack(to, version,locale, offer.getItemStack2(), true);
			}
			to.writeBoolean(offer.isDisabled());
			if (isUsingUsesCount(version)) {
				to.writeInt(offer.getUses());
				to.writeInt(offer.getMaxUses());
			}
		}
	}

	public static void writePEMerchantData(ByteBuf to, ProtocolVersion version, NetworkDataCache cache, long villagerId, String title, MerchantData merchdata) {
		String locale = cache.getAttributesCache().getLocale();
		to.writeByte((byte) cache.getWindowCache().getOpenedWindowId());
		to.writeByte(PEDataValues.WINDOWTYPE.getTable(version).getRemap(WindowType.VILLAGER.toLegacyId()));
		VarNumberSerializer.writeSVarInt(to, 0); //?
		VarNumberSerializer.writeSVarInt(to, 0); //?
		to.writeBoolean(true); //Is always willing!
		VarNumberSerializer.writeSVarLong(to, villagerId);
		VarNumberSerializer.writeSVarLong(to, cache.getWatchedEntityCache().getSelfPlayerEntityId());
		StringSerializer.writeString(to, version, title);
		NBTTagCompoundWrapper tag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
		NBTTagListWrapper recipes = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
		if (merchdata != null) {
			for (TradeOffer offer : merchdata.getOffers()) {
				NBTTagCompoundWrapper recipe = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
				ServerPlatform.get().getMiscUtils();
				recipe.setCompound("buyA", ItemStackToPENBT(version, locale, offer.getItemStack1()));
				recipe.setCompound("sell", ItemStackToPENBT(version, locale, offer.getResult()));
				if (offer.hasItemStack2()) {
					recipe.setCompound("buyB", ItemStackToPENBT(version, locale, offer.getItemStack2()));
				}
				recipe.setInt("uses", offer.isDisabled() ? offer.getMaxUses() : offer.getUses());
				recipe.setInt("maxUses", offer.getMaxUses());
				//recipe.setByte("rewardExp", 0);
				recipes.addCompound(recipe);
			}
		}
		tag.setList("Recipes", recipes);
		ItemStackSerializer.writeTag(to, true, version, tag);
	}

	private static boolean isUsingUsesCount(ProtocolVersion version) {
		return (version.getProtocolType() == ProtocolType.PC) && version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_8);
	}

	//TODO: Find proper place for this.
	private static NBTTagCompoundWrapper ItemStackToPENBT(ProtocolVersion version, String locale, ItemStackWrapper itemstack) {
		NBTTagCompoundWrapper item = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
		itemstack = ItemStackSerializer.remapItemToClient(version, locale, itemstack);
		item.setByte("Count", itemstack.getAmount());
		item.setShort("Damage", itemstack.getData());
		item.setShort("id", itemstack.getTypeId());
		if ((itemstack.getTag() != null) && !itemstack.getTag().isNull()) {
			item.setCompound("tag", itemstack.getTag().clone());
		}
		return item;
	}

}
