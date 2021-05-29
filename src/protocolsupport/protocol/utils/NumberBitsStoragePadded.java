package protocolsupport.protocol.utils;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public class NumberBitsStoragePadded {

	//precomputed table for fast division by numbersPerLong
	protected static final int[] numbers_per_long_fast_division_mul_add_sh_table = new int[] {
		-1, -1, 0, Integer.MIN_VALUE, 0, 0, 1431655765, 1431655765, 0, Integer.MIN_VALUE, 0, 1, 858993459, 858993459, 0,
		715827882, 715827882, 0, 613566756, 613566756, 0, Integer.MIN_VALUE, 0, 2, 477218588, 477218588, 0, 429496729, 429496729, 0,
		390451572, 390451572, 0, 357913941, 357913941, 0, 330382099, 330382099, 0, 306783378, 306783378, 0, 286331153, 286331153, 0,
		Integer.MIN_VALUE, 0, 3, 252645135, 252645135, 0, 238609294, 238609294, 0, 226050910, 226050910, 0, 214748364, 214748364, 0,
		204522252, 204522252, 0, 195225786, 195225786, 0, 186737708, 186737708, 0, 178956970, 178956970, 0, 171798691, 171798691, 0,
		165191049, 165191049, 0, 159072862, 159072862, 0, 153391689, 153391689, 0, 148102320, 148102320, 0, 143165576, 143165576, 0,
		138547332, 138547332, 0, Integer.MIN_VALUE, 0, 4, 130150524, 130150524, 0, 126322567, 126322567, 0, 122713351, 122713351, 0,
		119304647, 119304647, 0, 116080197, 116080197, 0, 113025455, 113025455, 0, 110127366, 110127366, 0, 107374182, 107374182, 0,
		104755299, 104755299, 0, 102261126, 102261126, 0, 99882960, 99882960, 0, 97612893, 97612893, 0, 95443717, 95443717, 0,
		93368854, 93368854, 0, 91382282, 91382282, 0, 89478485, 89478485, 0, 87652393, 87652393, 0, 85899345, 85899345, 0,
		84215045, 84215045, 0, 82595524, 82595524, 0, 81037118, 81037118, 0, 79536431, 79536431, 0, 78090314, 78090314, 0,
		76695844, 76695844, 0, 75350303, 75350303, 0, 74051160, 74051160, 0, 72796055, 72796055, 0, 71582788, 71582788, 0,
		70409299, 70409299, 0, 69273666, 69273666, 0, 68174084, 68174084, 0, Integer.MIN_VALUE, 0, 5
	};

	protected final long[] storage;
	protected final int bitsPerNumber;
	protected final long singleNumberMask;
	protected final int numbersPerLong;
	protected final long indexMul;
	protected final long indexAdd;
	protected final int indexShift;

	public NumberBitsStoragePadded(@Nonnegative int bitsPerNumber, @Nonnegative int size) {
		this.bitsPerNumber = bitsPerNumber;
		this.singleNumberMask = (1L << bitsPerNumber) - 1L;
		this.numbersPerLong = Long.SIZE / bitsPerNumber;
		int tableOffset = 3 * (this.numbersPerLong - 1);
		this.indexMul = Integer.toUnsignedLong(numbers_per_long_fast_division_mul_add_sh_table[tableOffset]);
		this.indexAdd = Integer.toUnsignedLong(numbers_per_long_fast_division_mul_add_sh_table[tableOffset + 1]);
		this.indexShift = 32 + numbers_per_long_fast_division_mul_add_sh_table[tableOffset + 2];
		this.storage = new long[((size + numbersPerLong) - 1) / numbersPerLong];
	}

	public NumberBitsStoragePadded(@Nonnegative int bitsPerNumber, @Nonnull long[] storage) {
		this.bitsPerNumber = bitsPerNumber;
		this.singleNumberMask = (1L << bitsPerNumber) - 1L;
		this.numbersPerLong = Long.SIZE / bitsPerNumber;
		int offset = 3 * (this.numbersPerLong - 1);
		this.indexMul = Integer.toUnsignedLong(numbers_per_long_fast_division_mul_add_sh_table[offset]);
		this.indexAdd = Integer.toUnsignedLong(numbers_per_long_fast_division_mul_add_sh_table[offset + 1]);
		this.indexShift = 32 + numbers_per_long_fast_division_mul_add_sh_table[offset + 2];
		this.storage = storage;
	}

	public @Nonnegative int getBitsPerNumber() {
		return bitsPerNumber;
	}

	public @Nonnull long[] getStorage() {
		return storage;
	}

	protected final @Nonnegative int getStorageIndex(@Nonnegative int index) {
		//fast division by numbersPerLong
		return (int) (((index * indexMul) + indexAdd) >> indexShift);
	}

	protected final @Nonnegative int getStorageValueShift(@Nonnegative int index, @Nonnegative int storageIndex) {
		return (index - (storageIndex * numbersPerLong)) * bitsPerNumber;
	}

	public int getNumber(@Nonnegative int index) {
		int storageIndex = getStorageIndex(index);
		return (int) ((storage[storageIndex] >> getStorageValueShift(index, storageIndex)) & singleNumberMask);
	}

	public void setNumber(@Nonnegative int index, int number) {
		int storageIndex = getStorageIndex(index);
		int storageValueShift = getStorageValueShift(index, storageIndex);
		storage[storageIndex] = (storage[storageIndex] & ~(singleNumberMask << storageValueShift)) | ((number & singleNumberMask) << storageValueShift);
	}

}
