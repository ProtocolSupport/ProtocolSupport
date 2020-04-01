package protocolsupport.protocol.storage.netcache.window;

public class WindowEnchantmentCache {

	protected final int[] enchantmentIds = new int[] {-1, -1, -1};
	protected final int[] enchantmentLevels = new int[] {-1, -1, -1};

	public int updateEnchantmentId(int index, int id) {
		enchantmentIds[index] = id;
		if (id == -1) {
			return -1;
		} else {
			return (enchantmentLevels[index] << 8) | (id);
		}
	}

	public int updateEnchantmentLevel(int index, int level) {
		enchantmentLevels[index] = level;
		if (level == -1) {
			return -1;
		} else {
			return (level << 8) | enchantmentIds[index];
		}
	}

}
