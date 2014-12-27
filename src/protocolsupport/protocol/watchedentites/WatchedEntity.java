package protocolsupport.protocol.watchedentites;

public class WatchedEntity {

	private int id;

	private boolean isLiving = false;
	private boolean isAgeable = false;
	private boolean isMinecart = false;
	private boolean isEnderman = false;
	private boolean isWolf = false;
	private boolean isItemFrame = false;
	private boolean isBoat = false;

	public WatchedEntity(int id) {
		this.id = id;
		isLiving = true;
	}

	public WatchedEntity(int id, int type) {
		this.id = id;
		switch (type) {
			case 1: {
				isBoat = true;
				break;
			}
			case 71: {
				isItemFrame = true;
				break;
			}
			case 10: {
				isMinecart = true;
				break;
			}
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57: {
				isLiving = true;
				break;
			}
			case 58: {
				isEnderman = true;
				isLiving = true;
				break;
			}
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
			case 64:
			case 65:
			case 66:
			case 67:
			case 68: {
				isLiving = true;
				break;
			}
			case 90:
			case 91:
			case 92:
			case 93: {
				isAgeable = true;
				isLiving = true;
				break;
			}
			case 94: {
				isLiving = true;
				break;
			}
			case 95: {
				isAgeable = true;
				isLiving = true;
				isWolf = true;
				break;
			}
			case 96: {
				isAgeable = true;
				isLiving = true;
				break;
			}
			case 97: {
				isLiving = true;
				break;
			}
			case 98: {
				isAgeable = true;
				isLiving = true;
				break;
			}
			case 99: {
				isLiving = true;
				break;
			}
			case 100:
			case 101:
			case 120: {
				isAgeable = true;
				isLiving = true;
				break;
			}
		}
	}

	public int getId() {
		return id;
	}

	public boolean isLiving() {
		return isLiving;
	}

	public boolean isAgeable() {
		return isAgeable;
	}

	public boolean isMinecart() {
		return isMinecart;
	}

	public boolean isBoat() {
		return isBoat;
	}

	public boolean isEnderman() {
		return isEnderman;
	}

	public boolean isWolf() {
		return isWolf;
	}

	public boolean isItemFrame() {
		return isItemFrame;
	}

}
