package protocolsupport.protocol.typeremapper.nbt.custompayload;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import protocolsupport.protocol.utils.types.ItemStackWrapper;

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
		private ItemStackWrapper itemstack1;
		private ItemStackWrapper itemstack2;
		private ItemStackWrapper result;
		private int uses;
		private int maxuses;

		public TradeOffer(ItemStackWrapper itemstack1, ItemStackWrapper itemstack2, ItemStackWrapper result, int uses, int maxuses) {
			this.itemstack1 = itemstack1;
			this.result = result;
			this.uses = uses;
			this.maxuses = maxuses;
			this.itemstack2 = itemstack2;
		}

		public ItemStackWrapper getItemStack1() {
			return itemstack1;
		}

		public boolean hasItemStack2() {
			return !itemstack2.isNull();
		}

		public ItemStackWrapper getItemStack2() {
			return itemstack2;
		}

		public ItemStackWrapper getResult() {
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
