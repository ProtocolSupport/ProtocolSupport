package protocolsupport.protocol.storage;

public class SharedStorage {

	private double x;
	private double y;
	private double z;
	private int teleportConfirmId;

	public boolean isTeleportConfirmNeeded() {
		return teleportConfirmId != -1;
	}

	public int tryTeleportConfirm(double x, double y, double z) {
		if (
			Double.doubleToLongBits(this.x) == Double.doubleToLongBits(x) &&
			Double.doubleToLongBits(this.y) == Double.doubleToLongBits(y) &&
			Double.doubleToLongBits(this.z) == Double.doubleToLongBits(z)
		) {
			int r = teleportConfirmId;
			teleportConfirmId = -1;
			return r;
		}
		return -1;
	}

	public void setTeleportLocation(double x, double y, double z, int teleportConfirmId) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.teleportConfirmId = teleportConfirmId;
	}

	private WindowType windowType = WindowType.PLAYER;

	public void setOpenedWindow(WindowType windowType) {
		this.windowType = windowType;
	}

	public WindowType getOpenedWindow() {
		return this.windowType;
	}

	public void closeWindow() {
		this.windowType = WindowType.PLAYER;
	}

	public static enum WindowType {
		CHEST, CRAFTING_TABLE, FURNACE, DISPENSER, ENCHANT, BREING, VILLAGER, BEACON, ANVIL, HOPPER, DROPPER, HORSE, PLAYER;

		public static WindowType fromName(String inventoryid) {
			switch (inventoryid) {
				case "minecraft:chest":
				case "minecraft:container": {
					return CHEST;
				}
				case "minecraft:crafting_table": {
					return CRAFTING_TABLE;
				}
				case "minecraft:furnace": {
					return FURNACE;
				}
				case "minecraft:dispenser": {
					return DISPENSER;
				}
				case "minecraft:enchanting_table": {
					return ENCHANT;
				}
				case "minecraft:brewing_stand": {
					return BREING;
				}
				case "minecraft:villager": {
					return VILLAGER;
				}
				case "minecraft:beacon": {
					return BEACON;
				}
				case "minecraft:anvil": {
					return ANVIL;
				}
				case "minecraft:hopper": {
					return HOPPER;
				}
				case "minecraft:dropper": {
					return DROPPER;
				}
				case "EntityHorse": {
					return HORSE;
				}
			}
			throw new IllegalArgumentException("Don't know how to convert " + inventoryid);
		}
	}

}
