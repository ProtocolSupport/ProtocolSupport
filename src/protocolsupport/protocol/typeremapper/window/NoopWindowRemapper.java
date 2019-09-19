package protocolsupport.protocol.typeremapper.window;

import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.WindowType;

public class NoopWindowRemapper extends SingleWindowItemsInstanceRemapper {

	public static final NoopWindowRemapper INSTANCE = new NoopWindowRemapper();

	public NoopWindowRemapper() {
		this(ORIGINAL_WINDOW, ORIGINAL_SLOTS);
	}

	public NoopWindowRemapper(WindowType clientWindowType) {
		this(clientWindowType, ORIGINAL_SLOTS);
	}

	public NoopWindowRemapper(int clientSlots) {
		this(ORIGINAL_WINDOW, clientSlots);
	}

	public NoopWindowRemapper(WindowType windowType, int windowSlots) {
		super(windowType, windowSlots);
	}

	@Override
	protected void fillWindowItems(WindowItems instance, byte windowId, NetworkItemStack[] content) {
		instance.windowId = windowId;
		instance.items = content;
	}

	@Override
	public int toWindowSlot(byte windowId, int slot) {
		return createWindowSlot(windowId, slot);
	}

	@Override
	public int fromWindowSlot(byte windowId, int slot) {
		return slot;
	}

}
