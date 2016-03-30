package protocolsupport.protocol.transformer.utils.chunk;

public class Palette {

	protected int[] palette;
	public Palette(int[] palette) {
		this.palette = palette;
	}

	public int getBlockState(int index) {
		return palette[index];
	}

	public static class GlobalPalette extends Palette {

		public static final GlobalPalette INSTANCE = new GlobalPalette();

		private GlobalPalette() {
			super(null);
		}

		@Override
		public int getBlockState(int index) {
			return index;
		}

	}

}