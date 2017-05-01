package protocolsupport.protocol.legacyremapper.pe;

import gnu.trove.map.hash.TIntIntHashMap;

public class PEDataValues {

	private static final TIntIntHashMap livingEntityType = new TIntIntHashMap();
	static {
		livingEntityType.put(28, 26);
		livingEntityType.put(93, 10);
		livingEntityType.put(5, 48);
		livingEntityType.put(62, 42);
		livingEntityType.put(97, 21);
		livingEntityType.put(31, 24);
		livingEntityType.put(92, 11);
		livingEntityType.put(55, 37);
		livingEntityType.put(90, 12);
		livingEntityType.put(4, 50);
		livingEntityType.put(51, 34);
		livingEntityType.put(32, 25);
		livingEntityType.put(23, 47);
		livingEntityType.put(65, 19);
		livingEntityType.put(102, 28);
		livingEntityType.put(61, 43);
		livingEntityType.put(67, 55);
		livingEntityType.put(101, 18);
		livingEntityType.put(54, 32);
		livingEntityType.put(60, 39);
		livingEntityType.put(63, 53);
		livingEntityType.put(68, 49);
		livingEntityType.put(56, 41);
		livingEntityType.put(91, 13);
		livingEntityType.put(59, 40);
		livingEntityType.put(29, 27);
		livingEntityType.put(6, 46);
		livingEntityType.put(69, 54);
		livingEntityType.put(95, 14);
		livingEntityType.put(120, 15);
		livingEntityType.put(98, 22);
		livingEntityType.put(100, 23);
		livingEntityType.put(99, 20);
		livingEntityType.put(96, 16);
		livingEntityType.put(66, 45);
		livingEntityType.put(94, 17);
		livingEntityType.put(52, 35);
		livingEntityType.put(57, 36);
		livingEntityType.put(64, 52);
		livingEntityType.put(27, 44);
		livingEntityType.put(50, 33);
		livingEntityType.put(58, 38);
	}

	public static int getLivingEntityType(int entityId) {
		return livingEntityType.get(entityId);
	}

}
