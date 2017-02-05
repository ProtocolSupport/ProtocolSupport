package protocolsupport.utils;

public class ApacheCommonsUtils {

	public static void isTrue(boolean bool, String msg) {
		if (!bool) {
			throw new IllegalStateException(msg);
		}
	}

	public static void notNull(Object obj, String msg) {
		if (obj == null) {
			throw new IllegalArgumentException(msg);
		}
	}

	public static boolean isEmpty(String str) {
		return (str == null) || str.isEmpty();
	}

	public static class ImmutablePair<T1, T2> {

		public static <ST1, ST2> ImmutablePair<ST1, ST2> of(ST1 o1, ST2 o2) {
			return new ImmutablePair<ST1, ST2>(o1, o2);
		}

		private final T1 o1;
		private final T2 o2;
		protected ImmutablePair(T1 o1, T2 o2) {
			this.o1 = o1;
			this.o2 = o2;
		}

		public T1 getLeft() {
			return o1;
		}

		public T2 getRight() {
			return o2;
		}
	}

}
