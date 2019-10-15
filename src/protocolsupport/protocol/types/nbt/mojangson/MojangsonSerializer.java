package protocolsupport.protocol.types.nbt.mojangson;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import protocolsupport.protocol.types.nbt.NBT;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupportbuildprocessor.Preload;

@Preload
public class MojangsonSerializer {

	public static String serialize(NBTCompound compound) {
		StringBuilder builder = new StringBuilder();
		writeCompound(builder, compound);
		return builder.toString();
	}


	protected static final Map<NBTType<? extends NBT>, TagWriter<? extends NBT>> typeWriters = new HashMap<>();

	protected static <T extends NBT> void registerType(NBTType<T> type, TagWriter<T> typeWriter) {
		typeWriters.put(type, typeWriter);
	}

	static {
		registerType(NBTType.BYTE, (builder, tag) -> writeByte(builder, tag.getAsByte()));
		registerType(NBTType.SHORT, (builder, tag) -> {
			builder.append(tag.getAsShort());
			builder.append(MojangsonConstants.type_short);
		});
		registerType(NBTType.INT, (builder, tag) -> builder.append(tag.getAsInt()));
		registerType(NBTType.LONG, (builder, tag) -> writeLong(builder, tag.getAsLong()));
		registerType(NBTType.FLOAT, (builder, tag) -> {
			builder.append(tag.getAsFloat());
			builder.append(MojangsonConstants.type_float);
		});
		registerType(NBTType.DOUBLE, (builder, tag) -> {
			builder.append(tag.getAsFloat());
			builder.append(MojangsonConstants.type_double);
		});
		registerType(NBTType.STRING, (builder, tag) -> writeQuotedString(builder, tag.getValue()));
		registerType(NBTType.BYTE_ARRAY, (builder, tag) -> {
			builder.append(MojangsonConstants.array_start);
			builder.append(MojangsonConstants.type_byte_u);
			builder.append(MojangsonConstants.array_type_separator);
			byte[] array = tag.getValue();
			int length = array.length;
			if (length> 0) {
				writeByte(builder, array[0]);
			}
			if (length > 1) {
				for (int i = 1; i < length; i++) {
					builder.append(MojangsonConstants.separator);
					writeByte(builder, array[i]);
				}
			}
			builder.append(MojangsonConstants.array_end);
		});
		registerType(NBTType.INT_ARRAY, (builder, tag) -> {
			builder.append(MojangsonConstants.array_start);
			builder.append(MojangsonConstants.type_int_u);
			builder.append(MojangsonConstants.array_type_separator);
			int[] array = tag.getValue();
			int length = array.length;
			if (length > 0) {
				builder.append(array[0]);
			}
			if (length > 1) {
				for (int i = 1; i < length; i++) {
					builder.append(MojangsonConstants.separator);
					builder.append(array[1]);
				}
			}
			builder.append(MojangsonConstants.array_end);
		});
		registerType(NBTType.LONG_ARRAY, (builder, tag) -> {
			builder.append(MojangsonConstants.array_start);
			builder.append(MojangsonConstants.type_long_u);
			builder.append(MojangsonConstants.array_type_separator);
			long[] array = tag.getValue();
			int length = array.length;
			if (length > 0) {
				writeLong(builder, array[0]);
			}
			if (length > 1) {
				for (int i = 1; i < length; i++) {
					builder.append(MojangsonConstants.separator);
					writeLong(builder, array[i]);
				}
			}
			builder.append(MojangsonConstants.array_end);
		});
		registerType(NBTType.LIST, (builder, tag) -> {
			builder.append(MojangsonConstants.array_start);
			@SuppressWarnings("unchecked")
			List<NBT> array = tag.getTags();
			int length = array.size();
			if (length > 0) {
				writeTag(builder, array.get(0));
			}
			if (length > 1) {
				for (int i = 1; i < length; i++) {
					builder.append(MojangsonConstants.separator);
					writeTag(builder, array.get(i));
				}
			}
			builder.append(MojangsonConstants.array_end);
		});
		registerType(NBTType.COMPOUND, MojangsonSerializer::writeCompound);
	}

	@SuppressWarnings("unchecked")
	protected static void writeTag(StringBuilder builder, NBT tag) {
		TagWriter<NBT> f = (TagWriter<NBT>) typeWriters.get(tag.getType());
		if (f == null) {
			throw new IllegalArgumentException(MessageFormat.format("No writer registered for nbt type {0}", tag.getType()));
		}
		f.writeTag(builder, tag);
	}

	protected static void writeByte(StringBuilder builder, byte b) {
		builder.append(b);
		builder.append(MojangsonConstants.type_byte);
	}

	protected static void writeLong(StringBuilder builder, long l) {
		builder.append(l);
		builder.append(MojangsonConstants.type_long);
	}

	protected static void writeCompound(StringBuilder builder, NBTCompound compound) {
		builder.append(MojangsonConstants.compound_start);
		Iterator<Entry<String, NBT>> iterator = compound.getTags().entrySet().iterator();
		if (iterator.hasNext()) {
			Entry<String, NBT> entry = iterator.next();
			writeQuotedString(builder, entry.getKey());
			builder.append(MojangsonConstants.compound_kv);
			writeTag(builder, entry.getValue());
		}
		while (iterator.hasNext()) {
			builder.append(MojangsonConstants.separator);
			Entry<String, NBT> entry = iterator.next();
			writeQuotedString(builder, entry.getKey());
			builder.append(MojangsonConstants.compound_kv);
			writeTag(builder, entry.getValue());
		}
		builder.append(MojangsonConstants.compound_end);
	}

	protected static void writeQuotedString(StringBuilder builder, String string) {
		builder.append(MojangsonConstants.string_quote);
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			if (c == MojangsonConstants.string_quote) {
				builder.append(MojangsonConstants.string_escape);
				builder.append(MojangsonConstants.string_quote);
			} else if (c == MojangsonConstants.string_escape) {
				builder.append(MojangsonConstants.string_escape);
				builder.append(MojangsonConstants.string_escape);
			} else {
				builder.append(c);
			}
		}
		builder.append(MojangsonConstants.string_quote);
	}

	@FunctionalInterface
	protected static interface TagWriter<T extends NBT> {
		public void writeTag(StringBuilder builder, T tag);
	}

}
