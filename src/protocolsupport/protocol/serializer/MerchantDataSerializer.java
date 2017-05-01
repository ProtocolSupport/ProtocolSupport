package protocolsupport.protocol.serializer;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.types.MerchantData;
import protocolsupport.protocol.utils.types.MerchantData.TradeOffer;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class MerchantDataSerializer {

	public static MerchantData readMerchantData(ByteBuf from, ProtocolVersion version) {
		MerchantData merchdata = new MerchantData(from.readInt());
		int count = from.readUnsignedByte();
		for (int i = 0; i < count; i++) {
			ItemStackWrapper itemstack1 = ItemStackSerializer.readItemStack(from, version);
			ItemStackWrapper result = ItemStackSerializer.readItemStack(from, version);
			ItemStackWrapper itemstack2 = ServerPlatform.get().getWrapperFactory().createNullItemStack();
			if (from.readBoolean()) {
				itemstack2 = ItemStackSerializer.readItemStack(from, version);
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

	public static void writeMerchantData(ByteBuf to, ProtocolVersion version, MerchantData merchdata, boolean fireItemStackEvents) {
		to.writeInt(merchdata.getWindowId());
		to.writeByte(merchdata.getOffers().size());
		for (TradeOffer offer : merchdata.getOffers()) {
			ItemStackSerializer.writeItemStack(to, version, offer.getItemStack1(), fireItemStackEvents);
			ItemStackSerializer.writeItemStack(to, version, offer.getResult(), fireItemStackEvents);
			to.writeBoolean(offer.hasItemStack2());
			if (offer.hasItemStack2()) {
				ItemStackSerializer.writeItemStack(to, version, offer.getItemStack2(), fireItemStackEvents);
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
