package protocolsupport.protocol.typeremapper.legacy;

import protocolsupport.protocol.types.Position;
import protocolsupport.utils.Utils;

public class LegacyRelMoveConverter {

	public static RelMove[] getRelMoves(RelMove relMove, int limit) {
		int maxVal = Math.abs(relMove.getX());
		maxVal = Math.max(Math.abs(relMove.getY()), maxVal);
		maxVal = Math.max(Math.abs(relMove.getZ()), maxVal);
		RelMove[] moves = new RelMove[Utils.getSplitCount(maxVal, limit)];
		for (int i = 0; i < moves.length; i++) {
			moves[i] = new RelMove(relMove.decreaseX(limit), relMove.decreaseY(limit), relMove.decreaseZ(limit));
		}
		return moves;
	}

	public static class RelMove extends Position {

		public RelMove(int x, int y, int z) {
			super(x, y, z);
		}

		public int decreaseX(int cnt) {
			int prev = x;
			x = getDecreasedVal(x, cnt);
			return prev - x;
		}

		public int decreaseY(int cnt) {
			int prev = y;
			y = getDecreasedVal(y, cnt);
			return prev - y;
		}

		public int decreaseZ(int cnt) {
			int prev = z;
			z = getDecreasedVal(z, cnt);
			return prev - z;
		}

		private static int getDecreasedVal(int val, int cnt) {
			if (val > 0) {
				val -= Math.min(val, cnt);
			} else if (val < 0) {
				val -= Math.max(val, -cnt);
			}
			return val;
		}

	}

}
