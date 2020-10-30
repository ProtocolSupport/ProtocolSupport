package protocolsupport.protocol.typeremapper.mapcolor;

public class MapColor implements IMapColor {

	protected final int id;
	protected final int r;
	protected final int g;
	protected final int b;

	public MapColor(int id, int r, int g, int b) {
		this.id = id;
		this.r = r;
		this.g = g;
		this.b = b;
	}

	@Override
	public int getId() {
		return id;
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
