package protocolsupport.protocol.typeremapper.nbt.custompayload;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.ItemStack;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.RecyclablePacketDataSerializer;
import protocolsupport.protocol.typeremapper.nbt.custompayload.MerchantData.TradeOffer;
import protocolsupport.utils.netty.ChannelUtils;

public class CustomPayloadSerializer {

	private final PacketDataSerializer serializer;

	public CustomPayloadSerializer(PacketDataSerializer serializer) {
		this.serializer = serializer;
	}

	public CustomPayloadSerializer(ProtocolVersion version) {
		this.serializer = RecyclablePacketDataSerializer.create(version);
	}

	public void copyAll(CustomPayloadSerializer another) {
		serializer.writeBytes(another.serializer);
	}

	public MerchantData readMerchantData() throws IOException {
		MerchantData merchdata = new MerchantData(serializer.readInt());
		int count = serializer.readUnsignedByte();
		for (int i = 0; i < count; i++) {
			ItemStack itemstack1 = serializer.readItemStack();
			ItemStack result = serializer.readItemStack();
			ItemStack itemstack2 = null;
			if (serializer.readBoolean()) {
				itemstack2 = serializer.readItemStack();
			}
			boolean disabled = serializer.readBoolean();
			int uses = 0;
			int maxuses = 7;
			if (serializer.getVersion() == ProtocolVersion.MINECRAFT_1_8) {
				uses = serializer.readInt();
				maxuses = serializer.readInt();
			}
			merchdata.addOffer(new TradeOffer(itemstack1, itemstack2, result, disabled ? maxuses : uses, maxuses));
		}
		return merchdata;
	}

	public void writeMerchantData(MerchantData merchdata) {
		serializer.writeInt(merchdata.getWindowId());
		serializer.writeByte(merchdata.getOffers().size());
		for (TradeOffer offer : merchdata.getOffers()) {
			serializer.writeItemStack(offer.getItemStack1());
			serializer.writeItemStack(offer.getResult());
			serializer.writeBoolean(offer.hasItemStack2());
			if (offer.hasItemStack2()) {
				serializer.writeItemStack(offer.getItemStack2());
			}
			serializer.writeBoolean(offer.isDisabled());
			if (serializer.getVersion() == ProtocolVersion.MINECRAFT_1_8) {
				serializer.writeInt(offer.getUses());
				serializer.writeInt(offer.getMaxUses());
			}
		}
	}

	public byte[] toData() {
		try {
			return ChannelUtils.toArray(serializer);
		} finally {
			serializer.release();
		}
	}

}
