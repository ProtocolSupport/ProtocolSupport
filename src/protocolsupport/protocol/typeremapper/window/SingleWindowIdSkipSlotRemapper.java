package protocolsupport.protocol.typeremapper.window;

import java.util.Arrays;

import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.WindowType;

public class SingleWindowIdSkipSlotRemapper extends SingleWindowIdRemapper {

	protected final int skipSlot;
	public SingleWindowIdSkipSlotRemapper(WindowType clientWindowType, int clientSlots, int skipSlot) {
		super(clientWindowType, clientSlots);
		this.skipSlot = skipSlot;
	}

	@Override
	protected void fillWindowItems(WindowItems instance, NetworkItemStack[] content) {
		int clientItemsLenth = content.length - 1;
		NetworkItemStack[] items = Arrays.copyOf(content, clientItemsLenth);
		System.arraycopy(content, skipSlot + 1, items, skipSlot, clientItemsLenth - skipSlot);
		instance.items = items;
	}

	@Override
	public int toWindowSlot(int slot) {
		if (slot == skipSlot) {
			throw SlotDoesntExistException.INSTANCE;
		}
		return slot < skipSlot ? slot : slot - 1;
	}

	@Override
	public int fromWindowSlot(byte windowId, int slot) {
		return slot < skipSlot ? slot : slot + 1;
	}

}
