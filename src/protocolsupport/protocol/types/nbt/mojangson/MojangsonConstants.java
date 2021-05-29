package protocolsupport.protocol.types.nbt.mojangson;

public class MojangsonConstants {

	private MojangsonConstants() {
	}

	public static final char compound_start = '{';
	public static final char compound_kv = ':';
	public static final char compound_end = '}';

	public static final char array_start = '[';
	public static final char array_end = ']';
	public static final char array_type_separator = ';';

	public static final char separator = ',';

	public static final char string_quote = '\"';
	public static final char string_quote_single = '\'';
	public static final char string_escape = '\\';

	public static final char type_byte = 'b';
	public static final char type_byte_u = 'B';

	public static final char type_short = 's';
	public static final char type_short_u = 'S';

	public static final char type_int_u = 'I';

	public static final char type_long = 'l';
	public static final char type_long_u = 'L';

	public static final char type_float = 'f';
	public static final char type_float_u = 'F';

	public static final char type_double = 'd';
	public static final char type_double_u = 'D';

	public static boolean isQuote(char c) {
		return (c == string_quote) || (c == string_quote_single);
	}

	public static boolean isAllowedUnquotedStringCodePoint(char c) {
		return ((c >= '0') && (c <= '9')) || ((c >= 'A') && (c <= 'Z')) || ((c >= 'a') && (c <= 'z')) || (c == '_') || (c == '-') || (c == '.') || (c == '+');
	}

}
