package protocolsupport.protocol.storage.pe;

//TODO: move internal caches back to network entity cached after cleanup in main branch
public class PEDataCache {

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

//	TODO: restore
//	private NBTTagCompoundWrapper signTag;
//
//	public void setSignTag(NBTTagCompoundWrapper signTag) {
//		this.signTag = signTag;
//	}
//
//	public NBTTagCompoundWrapper getSignTag() {
//		return signTag;
//	}

}
