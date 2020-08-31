package protocolsupport.protocol.typeremapper.window;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.IntFunction;

import org.apache.commons.lang3.tuple.Pair;

import protocolsupport.protocol.types.WindowType;

public class WindowsRemapper {

	protected final Map<WindowType, IntFunction<WindowRemapper>> windows = new EnumMap<>(WindowType.class);

	@SafeVarargs
	public WindowsRemapper(Pair<WindowType, IntFunction<WindowRemapper>>... entries) {
		for (Pair<WindowType, IntFunction<WindowRemapper>> entry : entries) {
			this.windows.put(entry.getLeft(), entry.getRight());
		}
	}

	@SafeVarargs
	public WindowsRemapper(WindowsRemapper other, Pair<WindowType, IntFunction<WindowRemapper>>... entries) {
		this.windows.putAll(other.windows);
		for (Pair<WindowType, IntFunction<WindowRemapper>> entry : entries) {
			this.windows.put(entry.getLeft(), entry.getRight());
		}
	}

	protected static class NonSlotCountBasedRemapperSelectFunction implements IntFunction<WindowRemapper> {

		protected final WindowRemapper entry;
		public NonSlotCountBasedRemapperSelectFunction(WindowRemapper entry) {
			this.entry = entry;
		}

		@Override
		public WindowRemapper apply(int value) {
			return entry;
		}

	}

	public WindowRemapper get(WindowType type, int windowSlots) {
		return windows.get(type).apply(windowSlots);
	}

}
