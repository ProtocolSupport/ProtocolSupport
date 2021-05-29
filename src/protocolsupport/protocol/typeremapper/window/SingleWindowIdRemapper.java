package protocolsupport.protocol.typeremapper.window;

import java.util.function.Supplier;

import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.WindowType;

public abstract class SingleWindowIdRemapper extends WindowRemapper {

	protected SingleWindowIdRemapper(WindowType clientWindowType, int clientSlots) {
		super(clientWindowType, clientSlots);
	}

	protected SingleWindowIdRemapper(WindowType clientWindowType, int clientSlots, Supplier<Object> windowMetadataCreator) {
		super(clientWindowType, clientSlots, windowMetadataCreator);
	}

	protected final ClientItemsArray clientItemsArray = new ClientItemsArray((byte) 0, null);
	protected final ClientItems clientItems = new ClientItems(clientItemsArray);
	protected final WindowSlot windowSlot = new WindowSlot((byte) 0, 0);

	@Override
	public ClientItems toClientItems(byte windowId, NetworkItemStack[] content) {
		clientItemsArray.windowId = windowId;
		fillClientItems(clientItemsArray, content);
		return clientItems;
	}

	@Override
	public WindowSlot toClientSlot(byte windowId, int slot) {
		windowSlot.windowId = windowId;
		windowSlot.slot = toClientSlot(slot);
		return windowSlot;
	}

	@Override
	public WindowSlot fromClientSlot(byte windowId, int slot) {
		windowSlot.windowId = windowId;
		windowSlot.slot = fromClientSlot(slot);
		return windowSlot;
	}

	protected abstract void fillClientItems(ClientItemsArray instance, NetworkItemStack[] content);

	protected abstract int toClientSlot(int slot);

	protected abstract int fromClientSlot(int slot);

}
