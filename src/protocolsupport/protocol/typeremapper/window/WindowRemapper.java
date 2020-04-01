package protocolsupport.protocol.typeremapper.window;

import java.util.function.Supplier;

import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.WindowType;

public abstract class WindowRemapper {

	protected static final WindowType ORIGINAL_WINDOW = null;
	protected static final int ORIGINAL_SLOTS = -1;

	protected final WindowType clientWindowType;
	protected final int clientSlots;
	protected final Supplier<Object> windowMetadataCreator;

	public WindowRemapper(WindowType clientWindowType, int clientSlots) {
		this(clientWindowType, clientSlots, () -> null);
	}

	public WindowRemapper(WindowType clientWindowType, int clientSlots, Supplier<Object> windowMetadataCreator) {
		this.clientWindowType = clientWindowType;
		this.clientSlots = clientSlots;
		this.windowMetadataCreator = windowMetadataCreator;
	}

	public Object createWindowMetadata() {
		return windowMetadataCreator.get();
	}

	public WindowType toClientWindowType(WindowType type) {
		return clientWindowType != ORIGINAL_WINDOW ? clientWindowType : type;
	}

	public int toClientSlots(int slots) {
		return clientSlots != ORIGINAL_SLOTS ? clientSlots : slots;
	}

	public abstract ClientItems[] toClientItems(byte windowId, NetworkItemStack[] content);

	public abstract int toClientSlot(byte windowId, int slot);

	public abstract int fromClientSlot(byte windowId, int slot);

	public static int createClientSlot(byte windowId, int slot) {
		return (slot << 8) | (windowId & 0xFF);
	}

	public static byte getClientSlotWindowId(int windowSlot) {
		return (byte) (windowSlot & 0xFF);
	}

	public static int getClientSlotSlot(int windowSlot) {
		return windowSlot >>> 8;
	}

	public static class ClientItems {

		protected byte windowId;
		protected NetworkItemStack[] items;

		public ClientItems(byte windowId, NetworkItemStack[] content) {
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

	public static class SlotDoesntExistException extends RuntimeException {
		public static final WindowRemapper.SlotDoesntExistException INSTANCE = new SlotDoesntExistException();
		private static final long serialVersionUID = 1L;
		@Override
		public synchronized Throwable fillInStackTrace() {
			return this;
		}
	}

}