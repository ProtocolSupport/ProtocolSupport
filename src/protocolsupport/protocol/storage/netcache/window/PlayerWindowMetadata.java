package protocolsupport.protocol.storage.netcache.window;

import protocolsupport.protocol.types.WindowType;

public interface PlayerWindowMetadata {

	public void onWindowOpen(WindowType type, int slots, Object metadata);

	public void onWindowClose();

}
