package protocolsupport.protocol.types.nbt.mojangson;

class MojangsonConstants {

	static final char compound_start = '{';
	static final char compound_kv = ':';
	static final char compound_end = '}';

	static final char array_start = '[';
	static final char array_end = ']';
	static final char array_type_separator = ';';

	static final char separator = ',';

	static final char string_quote = '\"';
	static final char string_escape = '\\';

	static final char type_byte = 'b';
	static final char type_byte_u = 'B';

	static final char type_short = 's';
	static final char type_short_u = 'S';

	static final char type_int_u = 'I';

	static final char type_long = 'l';
	static final char type_long_u = 'L';

	static final char type_float = 'f';
	static final char type_float_u = 'F';

	static final char type_double = 'd';
	static final char type_double_u = 'D';

	static boolean isAllowedUnquotedStringCodePoint(char c) {
		return ((c >= '0') && (c <= '9')) || ((c >= 'A') && (c <= 'Z')) || ((c >= 'a') && (c <= 'z')) || (c == '_') || (c == '-') || (c == '.') || (c == '+');
	}

}
