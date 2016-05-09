package protocolsupport.protocol.legacyremapper;

import java.util.ArrayList;

public class MapTransformer {

	private byte[] colors = new byte[16384];

	private int columnStart;
	private int columnEnd;

	private int rowStart;
	private int rowEnd;

	public void loadFromNewMapData(int columns, int rows, int xstart, int ystart, byte[] data) {
		for (int column = 0; column < columns; ++column) {
			for (int row = 0; row < rows; ++row) {
				colors[xstart + column + (ystart + row) * 128] = data[column + row * columns];
			}
		}
		columnStart = xstart;
		columnEnd = xstart + columns;
		rowStart = ystart;
		rowEnd = ystart + rows;
	}

	public ArrayList<ColumnEntry> toPre18MapData() {
		ArrayList<ColumnEntry> entries = new ArrayList<ColumnEntry>();
		for (int column = columnStart; column < columnEnd; column++) {
			ColumnEntry entry = new ColumnEntry(column, rowStart, rowEnd - rowStart);
			for (int row = rowStart; row < rowEnd; row++) {
				entry.colors[row - rowStart] = colors[row * 128 + column];
			}
			entries.add(entry);
		}
		return entries;
	}

	public static class ColumnEntry {

		public ColumnEntry(int x, int y, int rows) {
			this.x = x;
			this.y = y;
			this.colors = new byte[rows];
		}

		private int x;
		private int y;

		private byte[] colors;

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public byte[] getColors() {
			return colors;
		}

	}

}
