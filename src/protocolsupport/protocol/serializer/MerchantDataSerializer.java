package protocolsupport.protocol.serializer;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.types.MerchantData;
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
				ItemStackSerializer.writeItemStack(to, version, locale, offer.getItemStack2());
			}
			to.writeBoolean(offer.isDisabled());
			if (isUsingUsesCount(version)) {
				to.writeInt(offer.getUses());
				to.writeInt(offer.getMaxUses());
			}
		}
	}

	private static boolean isUsingUsesCount(ProtocolVersion version) {
		return (version.getProtocolType() == ProtocolType.PC) && version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_8);
	}

}
