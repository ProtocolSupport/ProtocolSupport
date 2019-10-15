package protocolsupport.protocol.typeremapper.window;

import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.WindowType;

public abstract class SingleWindowIdRemapper extends WindowRemapper {

	public SingleWindowIdRemapper(WindowType clientWindowType, int clientSlots) {
		super(clientWindowType, clientSlots);
	}

	protected final ClientItems clientitems = new ClientItems((byte) 0, null);
	protected final ClientItems[] clientitemsarray = new ClientItems[] {clientitems};

	@Override
	public ClientItems[] toClientItems(byte windowId, NetworkItemStack[] content) {
		clientitems.windowId = windowId;
		fillClientItems(clientitems, content);
		return clientitemsarray;
	}

	@Override
	public int toClientSlot(byte windowId, int slot) {
		return createClientSlot(windowId, toClientSlot(slot));
	}

	protected abstract void fillClientItems(ClientItems instance, NetworkItemStack[] content);

	protected abstract int toClientSlot(int slot);

}
