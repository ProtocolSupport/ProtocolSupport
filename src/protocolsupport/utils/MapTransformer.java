package protocolsupport.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class MapTransformer {

	private int[] colors = new int[16384];

	{
		Arrays.fill(colors, 1337);
	}

	public void loadFromNewMapData(int columns, int rows, int xstart, int ystart, byte[] data) {
		for (int column = 0; column < columns; ++column) {
			for (int row = 0; row < rows; ++row) {
				colors[xstart + column + (ystart + row) * 128] = data[column + row * columns];
			}
		}
	}

	public ArrayList<ColumnEntry> transformToOldMapData() {
		ArrayList<ColumnEntry> entries = new ArrayList<ColumnEntry>();
		for (int column = 0; column < 128; column++) {
			ColumnEntry entry = new ColumnEntry();
			entry.x = column;
			boolean foundstart = false;
			for (int row = 0; row < 128; row++) {
				int value = colors[row * 128 + column];
				if (value != 1337) {
					if (!foundstart) {
						foundstart = true;
						entry.y = row;
						entry.colors = new byte[128 - row];
					}
					entry.colors[row - entry.y] = (byte) value;
				} else if (foundstart) {
					byte[] colors = new byte[row - entry.y];
					System.arraycopy(entry.colors, 0, colors, 0, colors.length);
					entry.colors = colors;
					break;
				}
			}
			if (entry.colors != null) {
				entries.add(entry);
			}
		}
		return entries;
	}

	public static class ColumnEntry {

		private int x = -1;
		private int y = -1;

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
