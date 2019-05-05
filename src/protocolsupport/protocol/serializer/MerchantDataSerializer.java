package protocolsupport.protocol.serializer;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.types.MerchantData;
import protocolsupport.protocol.utils.types.MerchantData.TradeOffer;
import protocolsupport.protocol.utils.types.NetworkItemStack;

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
		return new MerchantData(windowId, offers, villagerLevel, villagerXp, villagerRegular);
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
		}
	}

	protected static boolean isUsingAdvancedTrading(ProtocolVersion version) {
		return (version.getProtocolType() == ProtocolType.PC) && version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_14);
	}

	protected static boolean isUsingUsesCount(ProtocolVersion version) {
		return (version.getProtocolType() == ProtocolType.PC) && version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_8);
	}

}
