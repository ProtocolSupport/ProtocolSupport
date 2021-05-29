package protocolsupport.utils;

import java.util.function.Function;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public class ThreadLocalObjectPool<T> {

	private final ThreadLocal<LocalStack<T>> pool;

	public ThreadLocalObjectPool(@Nonnegative int maxCapacity, @Nonnull Function<Handle<T>, T> allocator) {
		this.pool = ThreadLocal.withInitial(() -> new LocalStack<>(maxCapacity, allocator));
		this.pool.remove();
	}

	public @Nonnull T get() {
		return pool.get().pop().object;
	}

	public static class Handle<T> {

		private T object;

		private LocalStack<T> stack;

		/**
		 * Recycles the object
		 * @return true if recycled (object is added back to pool)
		 */
		public boolean recycle() {
			LocalStack<T> lStack = stack;

			if (lStack == null) {
				throw new IllegalStateException("Recycled already");
			}
			stack = null;

			return lStack.add(this);
		}

	}

	private static final class LocalStack<T> {

		private final Thread thread;
		private final Function<Handle<T>, T> allocator;
		private final Handle<T>[] elements;
		private int size = 0;

		@SuppressWarnings("unchecked")
		public LocalStack(int maxCapacity, Function<Handle<T>, T> allocator) {
			this.thread = Thread.currentThread();
			this.allocator = allocator;
			this.elements = new Handle[maxCapacity];
		}

		public Handle<T> pop() {
			Handle<T> handle;
			if (size > 0) {
				handle = elements[--size];
			} else {
				handle = new Handle<>();
				handle.object = allocator.apply(handle);
			}
			handle.stack = this;
			return handle;
		}

		public boolean add(Handle<T> handle) {
			if (thread != Thread.currentThread()) {
				return false;
			}

			if (size < elements.length) {
				elements[size++] = handle;
				return true;
			}
			return false;
		}

	}

}
