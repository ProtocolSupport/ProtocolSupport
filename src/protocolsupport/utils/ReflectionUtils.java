package protocolsupport.utils;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import sun.misc.Unsafe;

public class ReflectionUtils {

	private ReflectionUtils() {
	}

	public static @Nonnull String toStringAllFields(@Nonnull Object obj) {
		StringJoiner joiner = new StringJoiner(", ");
		Class<?> clazz = obj.getClass();
		do {
			try {
				for (Field field : clazz.getDeclaredFields()) {
					if (!Modifier.isStatic(field.getModifiers())) {
						setAccessible(field);
						Object value = field.get(obj);
						if ((value == null) || !value.getClass().isArray()) {
							joiner.add(field.getName() + ": " + Objects.toString(value));
						} else {
							joiner.add(field.getName() + ": " + Arrays.deepToString(new Object[] {value}));
						}
					}
				}
			} catch (IllegalAccessException e) {
				throw new UncheckedReflectionException("Unable to get object fields values", e);
			}
		} while ((clazz = clazz.getSuperclass()) != null);
		return obj.getClass().getName() + "(" + joiner.toString() + ")";
	}

	@SuppressWarnings("unchecked")
	public static <E extends Throwable> void sneakyThrow(Throwable e) throws E {
	    throw (E) e;
	}

	public static @Nonnull <T extends AccessibleObject> T setAccessible(@Nonnull T object) {
		object.setAccessible(true);
		return object;
	}

	public static @Nonnull Field getField(@Nonnull Class<?> clazz, @Nonnull String name) {
		do {
			for (Field field : clazz.getDeclaredFields()) {
				if (field.getName().equals(name)) {
					return setAccessible(field);
				}
			}
		} while ((clazz = clazz.getSuperclass()) != null);
		throw new UncheckedReflectionException("Can't find field " + name + " in class " + clazz + " or it's superclasses");
	}

	public static @Nonnull Method getMethod(@Nonnull Class<?> clazz, @Nonnull String name, @Nonnegative int paramlength) {
		do {
			for (Method method : clazz.getDeclaredMethods()) {
				if (method.getName().equals(name) && (method.getParameterTypes().length == paramlength)) {
					return setAccessible(method);
				}
			}
		} while ((clazz = clazz.getSuperclass()) != null);
		throw new UncheckedReflectionException("Can't find method " + name + " with params length " + paramlength + " in class " + clazz + " or it's superclasses");
	}

	public static void setFieldValue(@Nonnull Field field, @Nonnull Object object, @Nonnull Object value) {
		try {
			field.set(object, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new UncheckedReflectionException(e);
		}
	}

	public static void setStaticFinalFieldValue(@Nonnull Class<?> clazz, @Nonnull String fieldname, @Nonnull Object value) {
		try {
			sffs.set(clazz, fieldname, value);
		} catch (UncheckedReflectionException t) {
			throw t;
		} catch (Throwable t) {
			throw new UncheckedReflectionException(t);
		}
	}


	protected static interface StaticFinalFieldSetter {

		public void set(Class<?> clazz, String fieldname, Object value);

	}

	protected static class UnsafeStaticFinalFieldSetter implements StaticFinalFieldSetter {

		protected final Unsafe unsafe;

		protected UnsafeStaticFinalFieldSetter() {
			try {
				unsafe = ((Unsafe) setAccessible(Unsafe.class.getDeclaredField("theUnsafe")).get(null));
			} catch (Throwable e) {
				throw new UncheckedReflectionException(e);
			}
		}

		@Override
		public void set(Class<?> clazz, String fieldname, Object value) {
			try {
				Field field = clazz.getDeclaredField(fieldname);
				if ((value != null) && !field.getType().isInstance(value)) {
					throw new IllegalArgumentException("Can't set field type " + field.getType().getName() + " to " + value.getClass().getName());
				}
				unsafe.putObjectVolatile(unsafe.staticFieldBase(field), unsafe.staticFieldOffset(field), value);
			} catch (Throwable e) {
				throw new UncheckedReflectionException(e);
			}
		}

	}

	protected static class MethodHandleIMPLLookupStaticFinalFieldSetter implements StaticFinalFieldSetter {

		protected final MethodHandles.Lookup implLookup;

		protected MethodHandleIMPLLookupStaticFinalFieldSetter() {
			try {
				implLookup = ((MethodHandles.Lookup) setAccessible(MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP")).get(null));
			} catch (Throwable e) {
				throw new UncheckedReflectionException(e);
			}
		}

		@Override
		public void set(Class<?> clazz, String fieldname, Object value) {
			try {
				Field field = setAccessible(clazz.getDeclaredField(fieldname));
				implLookup
				.findSetter(Field.class, "modifiers", int.class)
				.invokeExact(field, field.getModifiers() & ~Modifier.FINAL);
				field.set(null, value);
			} catch (Throwable e) {
				throw new UncheckedReflectionException(e);
			}
		}

	}

	protected static class ErrorStaticFinalFieldSetter implements StaticFinalFieldSetter {

		private final UncheckedReflectionException exception;

		protected ErrorStaticFinalFieldSetter(UncheckedReflectionException exception) {
			this.exception = exception;
		}

		@Override
		public void set(Class<?> clazz, String fieldname, Object value) {
			throw exception;
		}

	}

	private static final StaticFinalFieldSetter sffs = initSFFS();

	private static StaticFinalFieldSetter initSFFS() {
		UncheckedReflectionException exception = new UncheckedReflectionException("Static final field setter initialization failed");
		try {
			return new UnsafeStaticFinalFieldSetter();
		} catch (Throwable t) {
			exception.addSuppressed(t);
		}
		try {
			return new MethodHandleIMPLLookupStaticFinalFieldSetter();
		} catch (Throwable t) {
			exception.addSuppressed(t);
		}
		return new ErrorStaticFinalFieldSetter(exception);
	}

}
