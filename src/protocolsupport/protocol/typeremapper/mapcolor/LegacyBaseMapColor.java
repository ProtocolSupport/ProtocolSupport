package protocolsupport.protocol.typeremapper.mapcolor;

public enum LegacyBaseMapColor implements IMapColor {

	NONE(-1, -1, -1),
	GRASS(127, 178, 56),
	SAND(247, 233, 163),
	WOOL(167, 167, 167),
	FIRE(255, 0, 0),
	ICE(160, 160, 255),
	METAL(167, 167, 167),
	PLANT(0, 124, 0),
	SNOW(255, 255, 255),
	CLAY(164, 168, 184),
	DIRT(183, 106, 47),
	STONE(112, 112, 112),
	WATER(64, 64, 255),
	WOOD(104, 83, 50);

	private final int r;
	private final int g;
	private final int b;

	LegacyBaseMapColor(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	@Override
	public int getId() {
		return ordinal();
	}

	@Override
	public int getRed() {
		return r;
	}

	@Override
	public int getGreen() {
		return g;
	}

	@Override
	public int getBlue() {
		return b;
	}

}
