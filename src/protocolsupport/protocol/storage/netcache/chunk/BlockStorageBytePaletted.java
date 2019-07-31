package protocolsupport.protocol.storage.netcache.chunk;

import it.unimi.dsi.fastutil.shorts.Short2ByteMap;
import it.unimi.dsi.fastutil.shorts.Short2ByteOpenCustomHashMap;
import it.unimi.dsi.fastutil.shorts.ShortHash;
import protocolsupport.protocol.types.chunk.ChunkConstants;

public class BlockStorageBytePaletted extends BlockStorage {

	protected static class MaxSizeReachedException extends RuntimeException {
		protected static final MaxSizeReachedException instance = new MaxSizeReachedException();
		private static final long serialVersionUID = 1L;
		@Override
		public synchronized Throwable fillInStackTrace() {
			return this;
		}
	}

	protected final byte[] blocks = new byte[ChunkConstants.BLOCKS_IN_SECTION];
	protected final BlockStorageBytePaletted.Palette palette;

	public BlockStorageBytePaletted() {
		this.palette = new Palette();
	}

	public BlockStorageBytePaletted(short[] runtimeToGlobal) {
		this.palette = new Palette(runtimeToGlobal);
	}

	public BlockStorageBytePaletted.Palette getPalette() {
		return palette;
	}

	@Override
	public short getBlockData(int index) {
		return palette.getGlobalId(blocks[index]);
	}

	@Override
	public void setBlockData(int index, short blockdata) {
		setRuntimeId(index, palette.getOrComputeRuntimeId(blockdata));
	}

	public byte getRuntimeId(int index) {
		return blocks[index];
	}

	public void setRuntimeId(int index, byte runtimeId) {
		blocks[index] = runtimeId;
	}

	public static class Palette {

		protected static final short initial_size = 32;

		protected static final short max_size = 256;

		protected final Short2ByteMap globalToRuntime = new Short2ByteOpenCustomHashMap(initial_size, new ShortHash.Strategy() {
			@Override
			public int hashCode(short e) {
				return e;
			}

			@Override
			public boolean equals(short a, short b) {
				return a == b;
			}
		});
		// TODO: make it grow initial -> max with power of two
		protected short[] runtimeToGlobal = new short[max_size];
		protected short nextRuntimeId = 0;

		public Palette() {
			getOrComputeRuntimeId((short) 0);
		}

		public Palette(short[] runtimeToGlobal) {
			if (runtimeToGlobal.length > max_size) {
				throw MaxSizeReachedException.instance;
			}
			int rtgLenth = runtimeToGlobal.length;
			System.arraycopy(runtimeToGlobal, 0, this.runtimeToGlobal, 0, rtgLenth);
			this.nextRuntimeId = (short) rtgLenth;
			for (int r = 0; r < rtgLenth; r++) {
				this.globalToRuntime.put(runtimeToGlobal[r], (byte) r);
			}
		}

		public short[] getPalette() {
			return runtimeToGlobal;
		}

		public int getPaletteSize() {
			return nextRuntimeId;
		}

		public byte getOrComputeRuntimeId(short globalId) {
			return globalToRuntime.computeIfAbsent(globalId, k -> {
				short cRuntimeId = nextRuntimeId++;
				if (cRuntimeId == max_size) {
					throw MaxSizeReachedException.instance;
				}
				runtimeToGlobal[cRuntimeId] = (short) k;
				return (byte) cRuntimeId;
			});
		}

		public short getGlobalId(byte runtimeId) {
			return runtimeToGlobal[runtimeId & 0xFF];
		}

	}

}