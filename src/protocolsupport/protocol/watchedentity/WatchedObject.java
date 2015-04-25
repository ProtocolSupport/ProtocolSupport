package protocolsupport.protocol.watchedentity;

public class WatchedObject extends WatchedEntity {

	private boolean isBoat = false;
	private boolean isItemFrame = false;
	private boolean isMinecart = false;
	private boolean isFallingBlockOrTnt = false;

	public WatchedObject(int id, int type) {
		super(id);
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
			case 70: {
				isFallingBlockOrTnt = true;
				break;
			}
		}
	}

	@Override
	public boolean isLiving() {
		return false;
	}

	@Override
	public boolean isAgeable() {
		return false;
	}

	@Override
	public boolean isEnderman() {
		return false;
	}

	@Override
	public boolean isWolf() {
		return false;
	}

	@Override
	public boolean isMinecart() {
		return isMinecart;
	}

	@Override
	public boolean isBoat() {
		return isBoat;
	}

	@Override
	public boolean isItemFrame() {
		return isItemFrame;
	}

	@Override
	public boolean isFallingBlockOrTnt() {
		return isFallingBlockOrTnt;
	}

}
