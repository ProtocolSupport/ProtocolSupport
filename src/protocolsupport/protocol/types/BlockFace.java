package protocolsupport.protocol.types;

import java.text.MessageFormat;

import protocolsupport.utils.CollectionsUtils;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public enum BlockFace {

	SELF	(-1,  0,  0,  0),
	BOTTOM	( 0,  0, -1,  0),
	TOP		( 1,  0,  1,  0),
	NORTH	( 2,  0,  0, -1),
	SOUTH	( 3,  0,  0,  1),
	WEST	( 4, -1,  0,  0),
	EAST	( 5,  1,  0,  0);

	private static final ArrayMap<BlockFace> byId = CollectionsUtils.makeEnumMappingArrayMap(BlockFace.class, BlockFace::getNetworkId);
	private final int netId;
	private final int modX;
	private final int modY;
	private final int modZ;

	private BlockFace(int netId, int modX, int modY, int modZ) {
		this.netId = netId;
		this.modX = modX;
		this.modY = modY;
		this.modZ = modZ;
	}

	public void modPosition(Position position) {
		position.modifyX(modX);
		position.modifyY(modY);
		position.modifyZ(modZ);
	}

	public int getNetworkId() {
		return netId;
	}

	public static BlockFace getById(int id) {
		BlockFace bface = byId.get(id);
		if (bface == null) {
			throw new IllegalArgumentException(MessageFormat.format("Unknown blockface network id {0}", id));
		}
		return bface;
	}

}
