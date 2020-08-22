package protocolsupport.protocol.serializer.chat;

import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.modifiers.ClickAction;
import protocolsupport.api.chat.modifiers.HoverAction;
import protocolsupport.api.chat.modifiers.Modifier;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.protocol.utils.json.SimpleJsonTreeSerializer;
import protocolsupport.utils.JsonUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class ChatSerializer {

	private static final Gson deserializer = new GsonBuilder()
	.registerTypeHierarchyAdapter(BaseComponent.class, ComponentSerializer.DEFAULT_INSTANCE)
	.registerTypeHierarchyAdapter(Modifier.class, ModifierSerializer.INSTANCE)
	.registerTypeHierarchyAdapter(ClickAction.class, ClickActionSerializer.INSTANCE)
	.registerTypeHierarchyAdapter(HoverAction.class, HoverActionSerializer.INSTANCE)
	.create();

	private static final Map<ProtocolVersion, SimpleJsonTreeSerializer<String>> serializers = new EnumMap<>(ProtocolVersion.class);

	protected static void register(SimpleJsonTreeSerializer<String> gson, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			serializers.put(version, gson);
		}
	}

	static {
		ComponentSerializer serverTranslateComponentSerializer =
			new ComponentSerializer.Builder()
			.setTranslateSerializer(ServerTranslateTranslateComponentContentSerializer.INSTANCE)
			.build();

		register(
			new SimpleJsonTreeSerializer.Builder<String>()
			.registerSerializer(BaseComponent.class, serverTranslateComponentSerializer)
			.registerSerializer(Modifier.class, ModifierSerializer.INSTANCE)
			.registerSerializer(ClickAction.class, UrlFixClickActionSerializer.INSTANCE)
			.registerSerializer(HoverAction.class, HoverActionSerializer.INSTANCE)
			.build(),
			ProtocolVersionsHelper.UP_1_16
		);

		for (ProtocolVersion version : ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_12, ProtocolVersion.MINECRAFT_1_15_2)) {
			register(
				new SimpleJsonTreeSerializer.Builder<String>()
				.registerSerializer(BaseComponent.class, serverTranslateComponentSerializer)
				.registerSerializer(Modifier.class, LegacyColorsModifierSerializer.INSTANCE)
				.registerSerializer(ClickAction.class, UrlFixClickActionSerializer.INSTANCE)
				.registerSerializer(HoverAction.class, new LegacyValueHoverActionSerializer(version))
				.build(),
				version
			);
		}
		for (ProtocolVersion version : ProtocolVersionsHelper.DOWN_1_11_1) {
			register(
				new SimpleJsonTreeSerializer.Builder<String>()
				.registerSerializer(
					BaseComponent.class,
					new ComponentSerializer.Builder()
					.setTranslateSerializer(ServerTranslateTranslateComponentContentSerializer.INSTANCE)
					.setKeybindSerializer(AsTextKeybindComponentContentSerializer.INSTANCE)
					.build()
				)
				.registerSerializer(Modifier.class, LegacyColorsModifierSerializer.INSTANCE)
				.registerSerializer(ClickAction.class, UrlFixClickActionSerializer.INSTANCE)
				.registerSerializer(HoverAction.class, new LegacyValueHoverActionSerializer(version))
				.build(),
				version
			);
		}
	}

	public static BaseComponent deserializeTree(JsonElement json) {
		return deserializer.fromJson(json, BaseComponent.class);
	}

	public static BaseComponent deserialize(String string) {
		return deserializer.fromJson(string, BaseComponent.class);
	}

	public static JsonElement serializeTree(ProtocolVersion version, String locale, BaseComponent component) {
		return serializers.get(version).serialize(component, locale);
	}

	public static String serialize(ProtocolVersion version, String locale, BaseComponent component) {
		return JsonUtils.GSON.toJson(serializeTree(version, locale, component));
	}

	public static class GsonBaseComponentSerializer implements JsonDeserializer<BaseComponent>, JsonSerializer<BaseComponent> {

		private final ProtocolVersion version;

		public GsonBaseComponentSerializer(ProtocolVersion version) {
			this.version = version;
		}

		public GsonBaseComponentSerializer() {
			this(ProtocolVersionsHelper.LATEST_PC);
		}

		@Override
		public BaseComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
			return ChatSerializer.deserializeTree(json);
		}

		@Override
		public JsonElement serialize(BaseComponent src, Type typeOfSrc, JsonSerializationContext context) {
			return ChatSerializer.serializeTree(version, I18NData.DEFAULT_LOCALE, src);
		}

	}

}
