package protocolsupport.protocol.utils.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		private final NetworkItemStack itemstack1;
		private final NetworkItemStack itemstack2;
		private final NetworkItemStack result;
		private final int uses;
		private final int maxuses;

		public TradeOffer(NetworkItemStack itemstack1, NetworkItemStack itemstack2, NetworkItemStack result, int uses, int maxuses) {
			this.itemstack1 = itemstack1;
			this.result = result;
			this.uses = uses;
			this.maxuses = maxuses;
			this.itemstack2 = itemstack2;
		}

		public NetworkItemStack getItemStack1() {
			return itemstack1;
		}

		public boolean hasItemStack2() {
			return !itemstack2.isNull();
		}

		public NetworkItemStack getItemStack2() {
			return itemstack2;
		}

		public NetworkItemStack getResult() {
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
