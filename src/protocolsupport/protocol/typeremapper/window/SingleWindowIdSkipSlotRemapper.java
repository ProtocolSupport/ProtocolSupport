package protocolsupport.protocol.typeremapper.window;

import java.util.Arrays;
import java.util.function.Supplier;

import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.WindowType;

public class SingleWindowIdSkipSlotRemapper extends SingleWindowIdRemapper {

	protected final int skipSlot;

	public SingleWindowIdSkipSlotRemapper(WindowType clientWindowType, int clientSlots, int skipSlot) {
		super(clientWindowType, clientSlots);
		this.skipSlot = skipSlot;
	}

	public SingleWindowIdSkipSlotRemapper(WindowType clientWindowType, int clientSlots, Supplier<Object> windowMetadataCreator, int skipSlot) {
		super(clientWindowType, clientSlots, windowMetadataCreator);
		this.skipSlot = skipSlot;
	}

	@Override
	protected void fillClientItems(ClientItemsArray instance, NetworkItemStack[] content) {
		int clientItemsLenth = content.length - 1;
		NetworkItemStack[] items = Arrays.copyOf(content, clientItemsLenth);
		System.arraycopy(content, skipSlot + 1, items, skipSlot, clientItemsLenth - skipSlot);
		instance.items = items;
	}

	@Override
	public int toClientSlot(int slot) {
		if (slot == skipSlot) {
			throw NoSuchSlotException.INSTANCE;
		}
		return slot < skipSlot ? slot : slot - 1;
	}

	@Override
	public int fromClientSlot(int slot) {
		return slot < skipSlot ? slot : slot + 1;
	}

}
