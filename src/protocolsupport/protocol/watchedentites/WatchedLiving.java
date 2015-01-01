package protocolsupport.protocol.watchedentites;

public class WatchedLiving extends WatchedEntity {

	private boolean isLiving = false;
	private boolean isEnderman = false;
	private boolean isAgeable = false;
	private boolean isWolf = false;

	public WatchedLiving(int id, int type) {
		super(id);
		switch (type) {
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

	@Override
	public boolean isLiving() {
		return isLiving;
	}

	@Override
	public boolean isAgeable() {
		return isAgeable;
	}

	@Override
	public boolean isEnderman() {
		return isEnderman;
	}

	@Override
	public boolean isWolf() {
		return isWolf;
	}

	@Override
	public boolean isMinecart() {
		return false;
	}

	@Override
	public boolean isBoat() {
		return false;
	}

	@Override
	public boolean isItemFrame() {
		return false;
	}

	@Override
	public boolean isFallingBlockOrTnt() {
		return false;
	}

}
