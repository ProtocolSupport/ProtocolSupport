package protocolsupport.protocol.storage.pe;

import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

//TODO: move internal caches back to network entity cached after cleanup in main branch
public class PEDataCache {

	private NBTTagCompoundWrapper signTag;
	private boolean isRightPaddleTurning;
	private boolean isLeftPaddleTurning;

	//TODO, (re)move?
	public void setSignTag(NBTTagCompoundWrapper signTag) {
		this.signTag = signTag;
	}

	public NBTTagCompoundWrapper getSignTag() {
		return signTag;
	}

	public boolean isRightPaddleTurning() {
		return isRightPaddleTurning;
	}

	public void setRightPaddleTurning(boolean isRightPaddleTurning) {
		this.isRightPaddleTurning = isRightPaddleTurning;
	}

	public boolean isLeftPaddleTurning() {
		return isLeftPaddleTurning;
	}

	public void setLeftPaddleTurning(boolean isLeftPaddleTurning) {
		this.isLeftPaddleTurning = isLeftPaddleTurning;
	}

	private final PEChunkMapCache chunkcache = new PEChunkMapCache();
	public PEChunkMapCache getChunkCache() {
		return chunkcache;
	}

	private final PEPlayerAttributesCache attrscache = new PEPlayerAttributesCache();
	public PEPlayerAttributesCache getAttributesCache() {
		return attrscache;
	}

	private final PEDimensionSwitchMovementConfirmationPacketQueue mvconfirmqueue = new PEDimensionSwitchMovementConfirmationPacketQueue();
	public PEDimensionSwitchMovementConfirmationPacketQueue getMovementConfirmQueue() {
		return mvconfirmqueue;
	}

	private final PEItemEntityCache itementitycache = new PEItemEntityCache();
	public PEItemEntityCache getItemEntityCache() {
		return itementitycache;
	}

	private final PETitleCache titlecache = new PETitleCache();
	public PETitleCache getTitleCache() {
		return titlecache;
	}

	private final PEInventoryCache inventorycache = new PEInventoryCache();
	public PEInventoryCache getInventoryCache() {
		return inventorycache;
	}

}
