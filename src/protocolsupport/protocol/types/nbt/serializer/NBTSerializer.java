package protocolsupport.protocol.types.nbt.serializer;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import protocolsupport.protocol.types.nbt.NBT;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTType;

public class NBTSerializer<IN, OUT> {

	public NBTCompound deserializeTag(IN from) throws IOException {
		NBTType<?> type = readTagType(from);
		if (type == NBTType.END) {
			return null;
		}
		if (type != NBTType.COMPOUND) {
			throw new IOException(MessageFormat.format("Root tag must be compound, got: {0}", type));
		}
		readTagName(from);
		return (NBTCompound) readTag(from, type);
	}

	public void serializeTag(OUT to, NBT tag) throws IOException {
		NBTType<?> type = tag.getType();
		writeTagType(to, type);
		if (tag.getType() == NBTType.END) {
			return;
		}
		if (type != NBTType.COMPOUND) {
			throw new IOException(MessageFormat.format("Root tag must be compound, got: {0}", type));
		}
		writeTagName(to, "");
		writeTag(to, tag);
	}


	protected final IdReader<IN> idReader;
	protected final IdWriter<OUT> idWriter;
	protected final NameReader<IN> nameReader;
	protected final NameWriter<OUT> nameWriter;
	public NBTSerializer(
		IdReader<IN> idReader, IdWriter<OUT> idWriter,
		NameReader<IN> nameReader, NameWriter<OUT> nameWriter
	) {
		this.idReader = idReader;
		this.idWriter = idWriter;
		this.nameReader = nameReader;
		this.nameWriter = nameWriter;
	}

	protected final Int2ObjectMap<NBTType<? extends NBT>> idToType = new Int2ObjectOpenHashMap<>();
	protected final Object2IntMap<NBTType<? extends NBT>> typeToId = new Object2IntOpenHashMap<>();
	{
		typeToId.defaultReturnValue(-1);
	}
	protected final Map<NBTType<? extends NBT>, TagReader<IN, ? extends NBT>> typeReaders = new HashMap<>();
	protected final Map<NBTType<? extends NBT>, TagWriter<OUT, ? extends NBT>> typeWriters = new HashMap<>();

	protected <T extends NBT> void registerType(
		NBTType<T> type, int id,
		TagReader<IN, T> typeReader,
		TagWriter<OUT, T> typeWriter
	) {
		if (typeToId.containsKey(type)) {
			throw new IllegalArgumentException(MessageFormat.format("Nbt type {0} is already registered", type));
		}
		if (idToType.containsKey(id)) {
			throw new IllegalArgumentException(MessageFormat.format("Nbt type id {0} is already registered", id));
		}
		idToType.put(id, type);
		typeToId.put(type, id);
		typeReaders.put(type, typeReader);
		typeWriters.put(type, typeWriter);
	}

	protected NBTType<?> readTagType(IN from) throws IOException {
		int id = idReader.readId(from);
		NBTType<?> type = idToType.get(id);
		if (type == null) {
			throw new IOException(MessageFormat.format("Unknown nbt type id {0}", id));
		}
		return type;
	}

	protected String readTagName(IN from) throws IOException {
		return nameReader.readName(from);
	}

	protected NBT readTag(IN from, NBTType<?> type) throws IOException {
		TagReader<IN, ? extends NBT> f = typeReaders.get(type);
		if (f == null) {
			throw new IOException(MessageFormat.format("No reader registered for nbt type {0}", type));
		}
		return f.readTag(from);
	}

	protected void writeTagType(OUT stream, NBTType<?> type) throws IOException {
		int id = typeToId.getInt(type);
		if (id == typeToId.defaultReturnValue()) {
			throw new IOException(MessageFormat.format("Unknown nbt type {0}", type));
		}
		idWriter.writeId(stream, id);
	}

	protected void writeTagName(OUT stream, String name) throws IOException {
		nameWriter.writeName(stream, name);
	}

	@SuppressWarnings("unchecked")
	protected void writeTag(OUT stream, NBT tag) throws IOException {
		TagWriter<OUT, NBT> f = (TagWriter<OUT, NBT>) typeWriters.get(tag.getType());
		if (f == null) {
			throw new IOException(MessageFormat.format("No writer registered for nbt type {0}", tag.getType()));
		}
		f.writeTag(stream, tag);
	}

	@FunctionalInterface
	protected static interface IdReader<T> {
		public int readId(T from) throws IOException;
	}

	@FunctionalInterface
	protected static interface IdWriter<T> {
		public void writeId(T to, int id) throws IOException;
	}

	@FunctionalInterface
	protected static interface NameReader<T> {
		public String readName(T from) throws IOException;
	}

	@FunctionalInterface
	protected static interface NameWriter<T> {
		public void writeName(T to, String name) throws IOException;
	}

	@FunctionalInterface
	protected static interface TagReader<IN, T extends NBT> {
		public T readTag(IN from) throws IOException;
	}

	@FunctionalInterface
	public static interface TagWriter<OUT, T extends NBT> {
		public void writeTag(OUT to, T tag) throws IOException;
	}

}
