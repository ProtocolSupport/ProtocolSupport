package protocolsupport.protocol.typeremapper.window;

import java.util.function.Supplier;

import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.WindowType;

public abstract class WindowRemapper {

	protected static final WindowType ORIGINAL_WINDOW = null;
	protected static final int ORIGINAL_SLOTS = -1;

	protected final WindowType clientWindowType;
	protected final int clientWindowSlots;
	protected final Supplier<Object> windowMetadataCreator;

	protected WindowRemapper(WindowType clientWindowType, int clientSlots) {
		this(clientWindowType, clientSlots, () -> null);
	}

	protected WindowRemapper(WindowType clientWindowType, int clientSlots, Supplier<Object> windowMetadataCreator) {
		this.clientWindowType = clientWindowType;
		this.clientWindowSlots = clientSlots;
		this.windowMetadataCreator = windowMetadataCreator;
	}

	public Object createWindowMetadata() {
		return windowMetadataCreator.get();
	}

	public WindowType toClientWindowType(WindowType type) {
		return clientWindowType != ORIGINAL_WINDOW ? clientWindowType : type;
	}

	public int toClientWindowSlots(int slots) {
		return clientWindowSlots != ORIGINAL_SLOTS ? clientWindowSlots : slots;
	}

	public abstract ClientItems toClientItems(byte windowId, NetworkItemStack[] content);

	public abstract WindowSlot toClientSlot(byte windowId, int slot);

	public abstract WindowSlot fromClientSlot(byte windowId, int slot);


	public static class ClientItems {

		protected static final ClientItemsSingle[] single_slots_empty = {};

		protected ClientItemsArray[] itemsArrays;
		protected ClientItemsSingle[] itemsSingle;

		public ClientItems(ClientItemsArray[] itemsArrays, ClientItemsSingle[] itemsSingle) {
			this.itemsArrays = itemsArrays;
			this.itemsSingle = itemsSingle;
		}

		public ClientItems(ClientItemsArray... itemsArrays) {
			this.itemsArrays = itemsArrays;
			this.itemsSingle = single_slots_empty;
		}

		public ClientItemsArray[] getItemsArrays() {
			return itemsArrays;
		}

		public ClientItemsSingle[] getItemsSingle() {
			return itemsSingle;
		}

	}

	public static class ClientItemsArray {

		protected byte windowId;
		protected NetworkItemStack[] items;

		public ClientItemsArray(byte windowId, NetworkItemStack[] content) {
			this.windowId = windowId;
			this.items = content;
		}

		public byte getWindowId() {
			return windowId;
		}

		public NetworkItemStack[] getItems() {
			return items;
		}

	}

	public static class ClientItemsSingle {

		protected byte windowId;
		protected int slot;
		protected NetworkItemStack item;

		public ClientItemsSingle(byte windowId, int slot, NetworkItemStack item) {
			this.windowId = windowId;
			this.slot = slot;
			this.item = item;
		}

		public byte getWindowId() {
			return windowId;
		}

		public int getSlot() {
			return slot;
		}

		public NetworkItemStack getItem() {
			return item;
		}

	}

	public static class WindowSlot {

		protected byte windowId;
		protected int slot;

		public WindowSlot(byte windowId, int slot) {
			this.windowId = windowId;
			this.slot = slot;
		}

		public byte getWindowId() {
			return windowId;
		}

		public int getSlot() {
			return slot;
		}

	}


	public static class NoSuchSlotException extends RuntimeException {
		public static final WindowRemapper.NoSuchSlotException INSTANCE = new NoSuchSlotException();
		private static final long serialVersionUID = 1L;
		@Override
		public synchronized Throwable fillInStackTrace() {
			return this;
		}
	}

}