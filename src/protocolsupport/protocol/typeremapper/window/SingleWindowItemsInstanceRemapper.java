package protocolsupport.protocol.typeremapper.window;

import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.WindowType;

public abstract class SingleWindowItemsInstanceRemapper extends WindowRemapper {

	public SingleWindowItemsInstanceRemapper(WindowType clientWindowType, int clientSlots) {
		super(clientWindowType, clientSlots);
	}

	protected final WindowItems windowitems = new WindowItems((byte) 0, null);
	protected final WindowItems[] windiwitemsarray = new WindowItems[] {windowitems};

	@Override
	public WindowItems[] toWindowItems(byte windowId, NetworkItemStack[] content) {
		fillWindowItems(windowitems, windowId, content);
		return windiwitemsarray;
	}

	protected abstract void fillWindowItems(WindowItems instance, byte windowId, NetworkItemStack[] content);

}
