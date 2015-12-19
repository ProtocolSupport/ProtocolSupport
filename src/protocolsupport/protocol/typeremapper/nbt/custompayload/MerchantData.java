package protocolsupport.protocol.typeremapper.nbt.custompayload;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_8_R3.ItemStack;

public class MerchantData {

	private int windowId;
	private List<TradeOffer> offers = new ArrayList<TradeOffer>(10);

	public MerchantData(int windowId) {
		this.windowId = windowId;
	}

	public int getWindowId() {
		return windowId;
	}

	public void addOffer(TradeOffer offer) {
		offers.add(offer);
	}

	public List<TradeOffer> getOffers() {
		return Collections.unmodifiableList(offers);
	}

	public static class TradeOffer {
		private ItemStack itemstack1;
		private ItemStack itemstack2;
		private ItemStack result;
		private int uses;
		private int maxuses;

		public TradeOffer(ItemStack itemstack1, ItemStack itemstack2, ItemStack result, int uses, int maxuses) {
			this.itemstack1 = itemstack1;
			this.result = result;
			this.uses = uses;
			this.maxuses = maxuses;
			this.itemstack2 = itemstack2;
		}

		public ItemStack getItemStack1() {
			return itemstack1;
		}

		public boolean hasItemStack2() {
			return itemstack2 != null;
		}

		public ItemStack getItemStack2() {
			return itemstack2;
		}

		public ItemStack getResult() {
			return result;
		}

		public boolean isDisabled() {
			return uses >= maxuses;
		}

		public int getUses() {
			return uses;
		}

		public int getMaxUses() {
			return maxuses;
		}
	}

}
