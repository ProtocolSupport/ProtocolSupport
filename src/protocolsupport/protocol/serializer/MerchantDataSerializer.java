package protocolsupport.protocol.serializer;

import io.netty.buffer.ByteBuf;

import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.types.MerchantData;
import protocolsupport.protocol.types.WindowType;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTInt;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.types.MerchantData.TradeOffer;
import protocolsupport.protocol.types.NetworkItemStack;

public class MerchantDataSerializer {

	public static MerchantData readMerchantData(ByteBuf from) {
		int windowId = VarNumberSerializer.readVarInt(from);
		TradeOffer[] offers = new TradeOffer[from.readUnsignedByte()];
		for (int i = 0; i < offers.length; i++) {
			NetworkItemStack itemstack1 = ItemStackSerializer.readItemStack(from);
			NetworkItemStack result = ItemStackSerializer.readItemStack(from);
			NetworkItemStack itemstack2 = NetworkItemStack.NULL;
			if (from.readBoolean()) {
				itemstack2 = ItemStackSerializer.readItemStack(from);
			}
			boolean disabled = from.readBoolean();
			int uses = from.readInt();
			int maxuses = from.readInt();
			int xp = from.readInt();
			int specialPrice = from.readInt();
			float priceMultiplier = from.readFloat();
			offers[i] = new TradeOffer(
				itemstack1, itemstack2, result,
				disabled ? maxuses : uses, maxuses,
				xp, specialPrice, priceMultiplier
			);
		}
		int villagerLevel = VarNumberSerializer.readVarInt(from);
		int villagerXp = VarNumberSerializer.readVarInt(from);
		boolean villagerRegular = from.readBoolean();
		boolean restockingVillager = from.readBoolean();
		return new MerchantData(windowId, offers, villagerLevel, villagerXp, villagerRegular, restockingVillager);
	}

	public static void writeMerchantData(ByteBuf to, ProtocolVersion version, String locale, MerchantData merchdata) {
		boolean advandedTrading = isUsingAdvancedTrading(version);
		boolean usesCount = isUsingUsesCount(version);
		if (advandedTrading) {
			VarNumberSerializer.writeVarInt(to, merchdata.getWindowId());
		} else {
			to.writeInt(merchdata.getWindowId());
		}
		to.writeByte(merchdata.getOffers().length);
		for (TradeOffer offer : merchdata.getOffers()) {
			ItemStackSerializer.writeItemStack(to, version, locale, offer.getItemStack1());
			ItemStackSerializer.writeItemStack(to, version, locale, offer.getResult());
			to.writeBoolean(offer.hasItemStack2());
			if (offer.hasItemStack2()) {
				ItemStackSerializer.writeItemStack(to, version, locale, offer.getItemStack2());
			}
			to.writeBoolean(offer.isDisabled());
			if (usesCount) {
				to.writeInt(offer.getUses());
				to.writeInt(offer.getMaxUses());
			}
			if (advandedTrading) {
				to.writeInt(offer.getXP());
				to.writeInt(offer.getSpecialPrice());
				to.writeFloat(offer.getPriceMultiplier());
			}
		}
		if (advandedTrading) {
			VarNumberSerializer.writeVarInt(to, merchdata.getVillagerLevel());
			VarNumberSerializer.writeVarInt(to, merchdata.getVillagerXP());
			to.writeBoolean(merchdata.isVillagerRegular());
			if (isUsingRestockingVillagerField(version)) {
				to.writeBoolean(merchdata.isRestockingVillager());
			}
		}
	}

	public static void writePEMerchantData(ByteBuf to, ProtocolVersion version, NetworkDataCache cache, long villagerId, String title, MerchantData merchdata) {
		String locale = cache.getAttributesCache().getLocale();
		to.writeByte((byte) cache.getWindowCache().getOpenedWindowId());
		to.writeByte(PEDataValues.WINDOWTYPE.getTable(version).getRemap(WindowType.MERCHANT.ordinal()));
		VarNumberSerializer.writeSVarInt(to, 0); //?
		VarNumberSerializer.writeSVarInt(to, 0); //?
		if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_PE_1_8)) {
			VarNumberSerializer.writeSVarInt(to, 0); //?
		}
		to.writeBoolean(true); //Is always willing!
		VarNumberSerializer.writeSVarLong(to, villagerId);
		VarNumberSerializer.writeSVarLong(to, cache.getWatchedEntityCache().getSelfPlayerEntityId());
		StringSerializer.writeString(to, version, title);
		NBTCompound tag = new NBTCompound();
		NBTList<NBTCompound> recipes = new NBTList<>(NBTType.COMPOUND);
		if (merchdata != null) {
			for (TradeOffer offer : merchdata.getOffers()) {
				NBTCompound recipe = new NBTCompound();
				recipe.setTag("buyA", CommonNBT.serializeItemStackToPENBT(version, locale, offer.getItemStack1()));
				recipe.setTag("sell", CommonNBT.serializeItemStackToPENBT(version, locale, offer.getResult()));
				if (offer.hasItemStack2()) {
					recipe.setTag("buyB", CommonNBT.serializeItemStackToPENBT(version, locale, offer.getItemStack2()));
				}
				recipe.setTag("uses", new NBTInt(offer.isDisabled() ? offer.getMaxUses() : offer.getUses()));
				recipe.setTag("maxUses", new NBTInt(offer.getMaxUses()));
				//recipe.setByte("rewardExp", 0);
				recipes.addTag(recipe);
			}
		}
		tag.setTag("Recipes", recipes);
		ItemStackSerializer.writeTag(to, true, version, tag);
	}

	protected static boolean isUsingAdvancedTrading(ProtocolVersion version) {
		return (version.getProtocolType() == ProtocolType.PC) && version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_14);
	}

	protected static boolean isUsingUsesCount(ProtocolVersion version) {
		return (version.getProtocolType() == ProtocolType.PC) && version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_8);
	}

	protected static boolean isUsingRestockingVillagerField(ProtocolVersion version) {
		return (version.getProtocolType() == ProtocolType.PC) && version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_14_3);
	}
}
