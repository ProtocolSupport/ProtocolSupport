package protocolsupport.protocol.typeremapper.window;

import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.WindowType;

public abstract class SingleWindowIdRemapper extends WindowRemapper {

	public SingleWindowIdRemapper(WindowType clientWindowType, int clientSlots) {
		super(clientWindowType, clientSlots);
	}

	protected final WindowItems windowitems = new WindowItems((byte) 0, null);
	protected final WindowItems[] windiwitemsarray = new WindowItems[] {windowitems};

	@Override
	public WindowItems[] toWindowItems(byte windowId, NetworkItemStack[] content) {
		windowitems.windowId = windowId;
		fillWindowItems(windowitems, content);
		return windiwitemsarray;
	}

	@Override
	public int toWindowSlot(byte windowId, int slot) {
		return createWindowSlot(windowId, toWindowSlot(slot));
	}

	protected abstract void fillWindowItems(WindowItems instance, NetworkItemStack[] content);

	protected abstract int toWindowSlot(int slot);

}
