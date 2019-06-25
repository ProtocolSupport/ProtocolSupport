package protocolsupport.protocol.types;

public class MerchantData {

	protected final int windowId;
	protected final TradeOffer[] offers;
	protected final int villagerLevel;
	protected final int villagerXp;
	protected final boolean villagerRegular;
	protected final boolean restockingVillager;

	public MerchantData(int windowId, TradeOffer[] offers, int villagerLevel, int villagerXp, boolean villagerRegular, boolean restockingVillager) {
		this.windowId = windowId;
		this.offers = offers;
		this.villagerLevel = villagerLevel;
		this.villagerXp = villagerXp;
		this.villagerRegular = villagerRegular;
		this.restockingVillager = restockingVillager;
	}

	public int getWindowId() {
		return windowId;
	}

	public TradeOffer[] getOffers() {
		return offers;
	}

	public int getVillagerLevel() {
		return villagerLevel;
	}

	public int getVillagerXP() {
		return villagerXp;
	}

	public boolean isVillagerRegular() {
		return villagerRegular;
	}

	public boolean isRestockingVillager() {
		return restockingVillager;
	}

	public static class TradeOffer {

		protected final NetworkItemStack itemstack1;
		protected final NetworkItemStack itemstack2;
		protected final NetworkItemStack result;
		protected final int uses;
		protected final int maxuses;
		protected final int xp;
		protected final int specialPrice;
		protected final float priceMultiplier;

		public TradeOffer(
			NetworkItemStack itemstack1, NetworkItemStack itemstack2, NetworkItemStack result,
			int uses, int maxuses,
			int xp, int specialPrice, float priceMultiplier
		) {
			this.itemstack1 = itemstack1;
			this.result = result;
			this.uses = uses;
			this.maxuses = maxuses;
			this.itemstack2 = itemstack2;
			this.xp = xp;
			this.specialPrice = specialPrice;
			this.priceMultiplier = priceMultiplier;
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

		public int getXP() {
			return xp;
		}

		public int getSpecialPrice() {
			return specialPrice;
		}

		public float getPriceMultiplier() {
			return priceMultiplier;
		}

	}

}
