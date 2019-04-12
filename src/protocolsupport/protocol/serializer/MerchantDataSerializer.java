package protocolsupport.protocol.serializer;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.types.MerchantData;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTInt;
import protocolsupport.protocol.utils.types.nbt.NBTList;
import protocolsupport.protocol.utils.types.nbt.NBTType;
import protocolsupport.protocol.utils.types.MerchantData.TradeOffer;
import protocolsupport.protocol.utils.types.NetworkItemStack;

public class MerchantDataSerializer {

	public static MerchantData readMerchantData(ByteBuf from) {
		MerchantData merchdata = new MerchantData(from.readInt());
		int count = from.readUnsignedByte();
		for (int i = 0; i < count; i++) {
			NetworkItemStack itemstack1 = ItemStackSerializer.readItemStack(from);
			NetworkItemStack result = ItemStackSerializer.readItemStack(from);
			NetworkItemStack itemstack2 = NetworkItemStack.NULL;
			if (from.readBoolean()) {
				itemstack2 = ItemStackSerializer.readItemStack(from);
			}
			boolean disabled = from.readBoolean();
			int uses = from.readInt();
			int maxuses = from.readInt();
			merchdata.addOffer(new TradeOffer(itemstack1, itemstack2, result, disabled ? maxuses : uses, maxuses));
		}
		return merchdata;
	}

	public static void writeMerchantData(ByteBuf to, ProtocolVersion version, String locale, MerchantData merchdata) {
		to.writeInt(merchdata.getWindowId());
		to.writeByte(merchdata.getOffers().size());
		for (TradeOffer offer : merchdata.getOffers()) {
			ItemStackSerializer.writeItemStack(to, version, locale, offer.getItemStack1());
			ItemStackSerializer.writeItemStack(to, version, locale, offer.getResult());
			to.writeBoolean(offer.hasItemStack2());
			if (offer.hasItemStack2()) {
				ItemStackSerializer.writeItemStack(to, version,locale, offer.getItemStack2());
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

	private static boolean isUsingUsesCount(ProtocolVersion version) {
		return version.getProtocolType() == ProtocolType.PC && version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_8);
	}

}
