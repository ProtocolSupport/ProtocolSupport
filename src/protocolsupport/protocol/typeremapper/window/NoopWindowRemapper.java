package protocolsupport.protocol.typeremapper.window;

import java.util.function.Supplier;

import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.WindowType;

public class NoopWindowRemapper extends SingleWindowIdRemapper {

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

	public NoopWindowRemapper(WindowType windowType, int windowSlots, Supplier<Object> windowMetadataCreator) {
		super(windowType, windowSlots, windowMetadataCreator);
	}

	@Override
	protected void fillClientItems(ClientItemsArray instance, NetworkItemStack[] content) {
		instance.items = content;
	}

	@Override
	public int toClientSlot(int slot) {
		return slot;
	}

	@Override
	protected int fromClientSlot(int slot) {
		return slot;
	}

}
