package protocolsupport.protocol.typeremapper.legacy;

public class LegacyPainting {

	public static String getName(int id) {
		switch (id) {
			case 0:
				return "Kebab";
			case 1:
				return "Aztec";
			case 2:
				return "Alban";
			case 3:
				return "Aztec2";
			case 4:
				return "Bomb";
			case 5:
				return "Plant";
			case 6:
				return "Wasteland";
			case 7:
				return "Pool";
			case 8:
				return "Courbet";
			case 9:
				return "Sea";
			case 10:
				return "Sunset";
			case 11:
				return "Creebet";
			case 12:
				return "Wanderer";
			case 13:
				return "Graham";
			case 14:
				return "Match";
			case 15:
				return "Bust";
			case 16:
				return "Stage";
			case 17:
				return "Void";
			case 18:
				return "SkullAndRoses";
			case 19:
				return "Wither";
			case 20:
				return "Fighters";
			case 21:
				return "Pointer";
			case 22:
				return "Pigscene";
			case 23:
				return "BurningSkull";
			case 24:
				return "Skeleton";
			case 25:
				return "DonkeyKong";
		}
		throw new UnsupportedOperationException("Trying to spawn painting with invalid id: " + id);
	}
}
