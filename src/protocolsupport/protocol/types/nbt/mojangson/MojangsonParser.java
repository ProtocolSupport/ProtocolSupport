package protocolsupport.protocol.types.nbt.mojangson;

import java.io.IOException;

import it.unimi.dsi.fastutil.bytes.ByteArrayList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import protocolsupport.protocol.types.nbt.NBT;
import protocolsupport.protocol.types.nbt.NBTByte;
import protocolsupport.protocol.types.nbt.NBTByteArray;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTDouble;
import protocolsupport.protocol.types.nbt.NBTFloat;
import protocolsupport.protocol.types.nbt.NBTInt;
import protocolsupport.protocol.types.nbt.NBTIntArray;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTLong;
import protocolsupport.protocol.types.nbt.NBTLongArray;
import protocolsupport.protocol.types.nbt.NBTShort;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;

public class MojangsonParser {

	public static NBTCompound parse(String mojangson) throws IOException {
		MojangsonReaderHelper reader = new MojangsonReaderHelper(mojangson);
		if (reader.read() != MojangsonConstants.compound_start) {
			throw new IOException("Root tag is not compound");
		}
		try {
			return readCompound(reader);
		} catch (Exception e) {
			throw reader.wrapException(e);
		}
	}

	protected static NBT read(char firstChar, MojangsonReaderHelper reader) throws IOException {
		if (firstChar == MojangsonConstants.compound_start) {
			return readCompound(reader);
		} else if (firstChar == MojangsonConstants.array_start) {
			return readArray(reader);
		} else {
			if (firstChar == MojangsonConstants.string_quote) {
				return new NBTString(readQuotedString(reader));
			} else {
				String string = readUnquotedString(firstChar, reader);
				if (string.equals("true")) {
					return new NBTByte((byte) 1);
				}
				if (string.equals("false")) {
					return new NBTByte((byte) 0);
				}
				try {
					int lastCharIndex = string.length() - 1;
					int lastStringChar = string.codePointAt(lastCharIndex);
					switch (lastStringChar) {
						case MojangsonConstants.type_byte:
						case MojangsonConstants.type_byte_u: {
							return new NBTByte(Byte.parseByte(string.substring(0, lastCharIndex)));
						}
						case MojangsonConstants.type_short:
						case MojangsonConstants.type_short_u: {
							return new NBTShort(Short.parseShort(string.substring(0, lastCharIndex)));
						}
						case MojangsonConstants.type_long:
						case MojangsonConstants.type_long_u: {
							return new NBTLong(Long.parseLong(string.substring(0, lastCharIndex)));
						}
						case MojangsonConstants.type_float:
						case MojangsonConstants.type_float_u: {
							return new NBTFloat(Float.parseFloat(string.substring(0, lastCharIndex)));
						}
						case MojangsonConstants.type_double:
						case MojangsonConstants.type_double_u: {
							return new NBTDouble(Double.parseDouble(string.substring(0, lastCharIndex)));
						}
						default: {
							double d = Double.parseDouble(string);
							int i = (int) d;
							return d == i ? new NBTInt(i) : new NBTDouble(d);
						}
					}
				} catch (NumberFormatException e) {
					return new NBTString(string);
				}
			}
		}
	}

	protected static NBTCompound readCompound(MojangsonReaderHelper reader) throws IOException {
		NBTCompound compound = new NBTCompound();
		for (;;) {
			char keyStartChar = skipWhitespace(reader);
			String key = keyStartChar == MojangsonConstants.string_quote ? readQuotedString(reader) : readUnquotedString(keyStartChar, reader);
			if (skipWhitespace(reader) != MojangsonConstants.compound_kv) {
				throw new IllegalArgumentException("Missing kv separator after compound key");
			}

			compound.setTag(key, read(skipWhitespace(reader), reader));

			if (!skipWhitespaceAndCheckSeparatorOrEnd(reader, MojangsonConstants.compound_end)) {
				return compound;
			}
		}
	}

	protected abstract static class NBTArrayReaderHelper<T extends NBT, E extends NBT> {
		public abstract T getArray();
		public abstract void addElement(E element);
	}

	protected static class GenericNBTListArrayReaderHelper extends NBTArrayReaderHelper <NBTList<NBT>, NBT> {
		protected final NBTList<NBT> list;
		public GenericNBTListArrayReaderHelper(NBTType<NBT> type) {
			list = new NBTList<>(type);
		}
		@Override
		public NBTList<NBT> getArray() {
			return list;
		}
		@Override
		public void addElement(NBT element) {
			list.addTag(element);
		}
	}

	protected static class NBTByteArrayReaderHelper extends NBTArrayReaderHelper<NBTByteArray, NBTByte> {
		protected final ByteArrayList array = new ByteArrayList();
		@Override
		public NBTByteArray getArray() {
			return new NBTByteArray(array.toByteArray());
		}
		@Override
		public void addElement(NBTByte element) {
			array.add(element.getAsByte());
		}
	}

	protected static class NBTIntArrayReaderHelper extends NBTArrayReaderHelper<NBTIntArray, NBTInt> {
		protected final IntArrayList array = new IntArrayList();
		@Override
		public NBTIntArray getArray() {
			return new NBTIntArray(array.toIntArray());
		}
		@Override
		public void addElement(NBTInt element) {
			array.add(element.getAsInt());
		}
	}

	protected static class NBTLongArrayReaderHelper extends NBTArrayReaderHelper<NBTLongArray, NBTLong> {
		protected final LongArrayList array = new LongArrayList();
		@Override
		public NBTLongArray getArray() {
			return new NBTLongArray(array.toLongArray());
		}
		@Override
		public void addElement(NBTLong element) {
			array.add(element.getAsLong());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static NBT readArray(MojangsonReaderHelper reader) throws IOException {
		char firstChar = reader.read();
		NBTArrayReaderHelper arrayReaderHelper = null;
		if ((firstChar != MojangsonConstants.string_quote) && (reader.peek() == MojangsonConstants.array_type_separator)) {
			reader.skip();
			if (firstChar == MojangsonConstants.type_byte_u) {
				arrayReaderHelper = new NBTByteArrayReaderHelper();
			} else if (firstChar == MojangsonConstants.type_int_u) {
				arrayReaderHelper = new NBTIntArrayReaderHelper();
			} else if (firstChar == MojangsonConstants.type_long_u) {
				arrayReaderHelper = new NBTLongArrayReaderHelper();
			} else {
				throw new IllegalArgumentException("Unknown flat array type " + firstChar);
			}
		} else {
			if (Character.isWhitespace(firstChar)) {
				firstChar = skipWhitespace(reader);
			}
			NBT firstValue = read(firstChar, reader);
			arrayReaderHelper = new GenericNBTListArrayReaderHelper((NBTType<NBT>) firstValue.getType());
			arrayReaderHelper.addElement(firstValue);
			if (!skipWhitespaceAndCheckSeparatorOrEnd(reader, MojangsonConstants.array_end)) {
				return arrayReaderHelper.getArray();
			}
		}
		for (;;) {
			arrayReaderHelper.addElement(read(skipWhitespace(reader), reader));
			if (!skipWhitespaceAndCheckSeparatorOrEnd(reader, MojangsonConstants.array_end)) {
				return arrayReaderHelper.getArray();
			}
		}
	}

	/**
	 * Skips whitespace and checks if char after that is a separator (,) or and expected end char <br>
	 * After finishing reader current position will point to the separator or end char <br>
	 * Returns true if char after whitespace was a separator char <br>
	 * Returns false if char after whitespace was an expected end char <br>
	 * Throws {@link IOException} otherwise
	 * @param reader reader
	 * @param expectedEndChar end char
	 * @return true if separator is found, false if expected end
	 * @throws IOException
	 */
	protected static boolean skipWhitespaceAndCheckSeparatorOrEnd(MojangsonReaderHelper reader, char expectedEndChar) throws IOException {
		int endChar = skipWhitespace(reader);
		if (endChar == MojangsonConstants.separator) {
			return true;
		}
		if (endChar != expectedEndChar) {
			throw new IllegalArgumentException("Missing expected tag end char " + expectedEndChar);
		}
		return false;
	}

	/**
	 * Reads quoted string <br>
	 * After finishing reader current position will point to the finishing quote <br>
	 * Returns read string
	 * @param reader reader
	 * @return string
	 * @throws IOException
	 */
	protected static String readQuotedString(MojangsonReaderHelper reader) throws IOException {
		StringBuilder builder = new StringBuilder();
		for (;;) {
			char c = reader.read();
			if (c == MojangsonConstants.string_escape) {
				char afterEscapeChar = reader.read();
				if (afterEscapeChar == MojangsonConstants.string_quote) {
					builder.append(MojangsonConstants.string_quote);
				} else if (afterEscapeChar == MojangsonConstants.string_escape) {
					builder.append(MojangsonConstants.string_escape);
				} else {
					throw new IllegalArgumentException("Invalid char " + afterEscapeChar + " after string escape");
				}
			} else if (c != MojangsonConstants.string_quote) {
				builder.append(c);
			} else {
				return builder.toString();
			}
		}
	}

	/**
	 * Reads unquoted string <br>
	 * After finishing reader current position will point to the last string char <br>
	 * Returns read string
	 * @param firstChar first char of the string
	 * @param reader reader
	 * @return string
	 * @throws IOException
	 */
	protected static String readUnquotedString(char firstChar, MojangsonReaderHelper reader) throws IOException {
		if (!MojangsonConstants.isAllowedUnquotedStringCodePoint(firstChar)) {
			throw new IllegalArgumentException("Unallowed unquoted string start char " + firstChar);
		}
		StringBuilder builder = new StringBuilder();
		builder.append(firstChar);
		char c;
		while (MojangsonConstants.isAllowedUnquotedStringCodePoint(c = reader.peek())) {
			builder.append(c);
			reader.skip();
		}
		return builder.toString();
	}

	/**
	 * Skips whitespace chars <br>
	 * After finishing reader current position will point to first non-whitespace char <br>
	 * Returns first non-whitespace char read
	 * @param reader reader
	 * @return first non-whitespace char read
	 * @throws IOException
	 */
	protected static char skipWhitespace(MojangsonReaderHelper reader) throws IOException {
		for (;;) {
			char c = reader.read();
			if (!Character.isWhitespace(c)) {
				return c;
			}
		}
	}

	protected static class MojangsonReaderHelper {

		protected final String mojangson;
		protected int next = 0;

		public MojangsonReaderHelper(String mojangson) {
			this.mojangson = mojangson;
		}

		public char read() throws IOException {
			if (next >= mojangson.length()) {
				throw new IOException("Unexpected end of mojangson");
			}
			return mojangson.charAt(next++);
		}

		public char peek() throws IOException {
			if (next >= mojangson.length()) {
				throw new IOException("Unexpected end of mojangson");
			}
			return mojangson.charAt(next);
		}

		public void skip() {
			next++;
		}

		public IOException wrapException(Exception e) {
			return new IOException("Can't parse " + mojangson + " as mojangson. Error near index " + (next - 1) + ". " + e.getMessage() + ".", e);
		}

	}

}
