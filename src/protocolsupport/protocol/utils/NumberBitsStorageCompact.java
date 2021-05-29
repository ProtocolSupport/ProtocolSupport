package protocolsupport.protocol.utils;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import protocolsupport.utils.Utils;

public class NumberBitsStorageCompact {

	protected final long[] storage;
	protected final int bitsPerNumber;
	protected final long singleNumberMask;

	public NumberBitsStorageCompact(@Nonnegative int bitsPerNumber, @Nonnegative int size) {
		this.bitsPerNumber = bitsPerNumber;
		this.singleNumberMask = (1L << bitsPerNumber) - 1L;
		this.storage = new long[Utils.ceilToBase(size * bitsPerNumber, Long.SIZE) / Long.SIZE];
	}

	public @Nonnegative int getBitsPerNumber() {
		return bitsPerNumber;
	}

	public @Nonnull long[] getStorage() {
		return storage;
	}

	public int getNumber(@Nonnegative int index) {
		int bitStartIndex = index * bitsPerNumber;
		int arrStartIndex = bitStartIndex >> 6;
		int arrEndIndex = ((bitStartIndex + bitsPerNumber) - 1) >> 6;
		int localStartBitIndex = bitStartIndex & 63;
		if (arrStartIndex == arrEndIndex) {
			return (int) ((storage[arrStartIndex] >>> localStartBitIndex) & singleNumberMask);
		} else {
			return (int) (((storage[arrStartIndex] >>> localStartBitIndex) | (storage[arrEndIndex] << (Long.SIZE - localStartBitIndex))) & singleNumberMask);
		}
	}

	public void setNumber(@Nonnegative int index, int number) {
		int bitStartIndex = index * this.bitsPerNumber;
		int arrStartIndex = bitStartIndex >> 6;
		int arrEndIndex = ((bitStartIndex + bitsPerNumber) - 1) >> 6;
		int localStartBitIndex = bitStartIndex & 63;
		storage[arrStartIndex] = ((storage[arrStartIndex] & ~(singleNumberMask << localStartBitIndex)) | ((number & singleNumberMask) << localStartBitIndex));
		if (arrStartIndex != arrEndIndex) {
			int thisPartSift = Long.SIZE - localStartBitIndex;
			int otherPartShift = bitsPerNumber - thisPartSift;
			storage[arrEndIndex] = (((storage[arrEndIndex] >>> otherPartShift) << otherPartShift) | ((number & singleNumberMask) >> thisPartSift));
		}
	}

}
