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
import protocolsupport.protocol.types.nbt.mojangson.MojangsonSerializer.TagWriter;
import protocolsupportbuildprocessor.Preload;

@Preload
public class LegacyMojangsonSerializer {

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
		registerType(NBTType.BYTE, (builder, tag) -> {
			builder.append(tag.getAsByte());
			builder.append(MojangsonConstants.type_byte);
		});
		registerType(NBTType.SHORT, (builder, tag) -> {
			builder.append(tag.getAsShort());
			builder.append(MojangsonConstants.type_short);
		});
		registerType(NBTType.INT, (builder, tag) -> builder.append(tag.getAsInt()));
		registerType(NBTType.LONG, (builder, tag) -> {
			builder.append(tag.getAsLong());
			builder.append(MojangsonConstants.type_long);
		});
		registerType(NBTType.FLOAT, (builder, tag) -> {
			builder.append(tag.getAsFloat());
			builder.append(MojangsonConstants.type_float);
		});
		registerType(NBTType.DOUBLE, (builder, tag) -> {
			builder.append(tag.getAsFloat());
			builder.append(MojangsonConstants.type_double);
		});
		registerType(NBTType.STRING, (builder, tag) -> MojangsonSerializer.writeQuotedString(builder, tag.getValue()));
		registerType(NBTType.INT_ARRAY, (builder, tag) -> {
			builder.append(MojangsonConstants.array_start);
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

	protected static void writeCompound(StringBuilder builder, NBTCompound compound) {
		builder.append(MojangsonConstants.compound_start);
		Iterator<Entry<String, NBT>> iterator = compound.getTags().entrySet().iterator();
		if (iterator.hasNext()) {
			Entry<String, NBT> entry = iterator.next();
			writeUnquotedString(builder, entry.getKey());
			builder.append(MojangsonConstants.compound_kv);
			writeTag(builder, entry.getValue());
		}
		while (iterator.hasNext()) {
			builder.append(MojangsonConstants.separator);
			Entry<String, NBT> entry = iterator.next();
			writeUnquotedString(builder, entry.getKey());
			builder.append(MojangsonConstants.compound_kv);
			writeTag(builder, entry.getValue());
		}
		builder.append(MojangsonConstants.compound_end);
	}

	protected static void writeUnquotedString(StringBuilder builder, String string) {
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			if (MojangsonConstants.isAllowedUnquotedStringCodePoint(c)) {
				builder.append(c);
			}
		}
	}

}
