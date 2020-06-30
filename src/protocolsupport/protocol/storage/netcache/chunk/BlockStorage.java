package protocolsupport.protocol.storage.netcache.chunk;

public abstract interface BlockStorage {

	public abstract short getBlockData(int index);

	public abstract void setBlockData(int index, short blockdata);

}