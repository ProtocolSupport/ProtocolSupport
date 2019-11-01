package protocolsupport.protocol.types.recipe;

public class Recipe {

	protected final String id;
	protected final RecipeType type;

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