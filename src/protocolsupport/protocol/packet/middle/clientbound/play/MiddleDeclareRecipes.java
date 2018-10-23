package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.protocol.utils.types.NetworkItemStack;

public abstract class MiddleDeclareRecipes extends ClientBoundMiddlePacket {

	public MiddleDeclareRecipes(ConnectionImpl connection) {
		super(connection);
	}

	protected static final Map<ProtocolVersion, ByteBuf> cachedBuffers = Collections.synchronizedMap(new EnumMap<>(ProtocolVersion.class));

	protected Recipe[] recipes;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		if (cachedBuffers.containsKey(connection.getVersion())) {
			MiscSerializer.readAllBytesSlice(serverdata);
		} else {
			int count = VarNumberSerializer.readVarInt(serverdata);
			recipes = new Recipe[count];
			for (int i = 0; i < count; i++) {
				String id = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC);
				RecipeType type = RecipeType.valueOf(StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC));
				recipes[i] = type.read(id, serverdata);
			}
		}
	}

	public static class Ingredient {
		private NetworkItemStack[] possibleStacks;

		public Ingredient(ByteBuf serverdata) {
			int possibleStacksCount = VarNumberSerializer.readVarInt(serverdata);
			possibleStacks = new NetworkItemStack[possibleStacksCount];
			for (int i = 0; i < possibleStacksCount; i++) {
				possibleStacks[i] = ItemStackSerializer.readItemStack(serverdata, ProtocolVersionsHelper.LATEST_PC, I18NData.DEFAULT_LOCALE, false);
			}
		}

		public NetworkItemStack[] getPossibleStacks() {
			return possibleStacks;
		}
	}

	public static class Recipe {
		private String id;
		private RecipeType type;

		public Recipe(String id, RecipeType type) {
			this.id = id;
			this.type = type;
		}

		public String getId() {
			return id;
		}

		public RecipeType getType() {
			return type;
		}
	}

	public static class ShapelessRecipe extends Recipe {
		private String group;
		private Ingredient[] ingredients;
		private NetworkItemStack result;

		public ShapelessRecipe(String id, ByteBuf data) {
			super(id, RecipeType.crafting_shapeless);

			group = StringSerializer.readString(data, ProtocolVersionsHelper.LATEST_PC);
			int ingredientCount = VarNumberSerializer.readVarInt(data);
			ingredients = new Ingredient[ingredientCount];
			for (int j = 0; j < ingredientCount; j++) {
				ingredients[j] = new Ingredient(data);
			}
			result = ItemStackSerializer.readItemStack(data, ProtocolVersionsHelper.LATEST_PC, I18NData.DEFAULT_LOCALE, false);
		}

		public String getGroup() {
			return group;
		}

		public Ingredient[] getIngredients() {
			return ingredients;
		}

		public NetworkItemStack getResult() {
			return result;
		}
	}

	public static class ShapedRecipe extends Recipe {
		private String group;
		private int width;
		private int height;
		private Ingredient[] ingredients;
		private NetworkItemStack result;

		public ShapedRecipe(String id, ByteBuf data) {
			super(id, RecipeType.crafting_shaped);

			width = VarNumberSerializer.readVarInt(data);
			height = VarNumberSerializer.readVarInt(data);
			group = StringSerializer.readString(data, ProtocolVersionsHelper.LATEST_PC);
			int ingredientCount = width * height;
			ingredients = new Ingredient[ingredientCount];
			for (int j = 0; j < ingredientCount; j++) {
				ingredients[j] = new Ingredient(data);
			}
			result = ItemStackSerializer.readItemStack(data, ProtocolVersionsHelper.LATEST_PC, I18NData.DEFAULT_LOCALE, false);
		}

		public String getGroup() {
			return group;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}

		public Ingredient[] getIngredients() {
			return ingredients;
		}

		public NetworkItemStack getResult() {
			return result;
		}
	}

	public static class SmeltingRecipe extends Recipe {
		private String group;
		private Ingredient ingredient;
		private NetworkItemStack result;
		private float exp;
		private int time;

		public SmeltingRecipe(String id, ByteBuf data) {
			super(id, RecipeType.smelting);

			group = StringSerializer.readString(data, ProtocolVersionsHelper.LATEST_PC);
			ingredient = new Ingredient(data);
			result = ItemStackSerializer.readItemStack(data, ProtocolVersionsHelper.LATEST_PC, I18NData.DEFAULT_LOCALE, false);
			exp = data.readFloat();
			time = VarNumberSerializer.readVarInt(data);
		}

		public String getGroup() {
			return group;
		}

		public Ingredient getIngredient() {
			return ingredient;
		}

		public NetworkItemStack getResult() {
			return result;
		}

		public float getExp() {
			return exp;
		}

		public int getTime() {
			return time;
		}
	}

	public enum RecipeType {
		crafting_shapeless {
			@Override
			public Recipe read(String id, ByteBuf data) {
				return new ShapelessRecipe(id, data);
			}
		},
		crafting_shaped {
			@Override
			public Recipe read(String id, ByteBuf data) {
				return new ShapedRecipe(id, data);
			}
		},
		crafting_special_armordye,
		crafting_special_bookcloning,
		crafting_special_mapcloning,
		crafting_special_mapextending,
		crafting_special_firework_rocket,
		crafting_special_firework_star,
		crafting_special_firework_star_fade,
		crafting_special_repairitem,
		crafting_special_tippedarrow,
		crafting_special_bannerduplicate,
		crafting_special_banneraddpattern,
		crafting_special_shielddecoration,
		crafting_special_shulkerboxcoloring,
		smelting {
			@Override
			public Recipe read(String id, ByteBuf data) {
				return new SmeltingRecipe(id, data);
			}
		};

		public Recipe read(String id, ByteBuf serverdata) {
			return new Recipe(id, this);

		}
	}
}
