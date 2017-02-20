package protocolsupport.protocol.utils.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class MerchantData {

	private final int windowId;
	private final List<TradeOffer> offers = new ArrayList<>(10);

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
		private final ItemStackWrapper itemstack1;
		private final ItemStackWrapper itemstack2;
		private final ItemStackWrapper result;
		private final int uses;
		private final int maxuses;

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
