package protocolsupport.protocol.types.recipe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.NetworkItemStack;

public class RecipeIngredient {

	protected final NetworkItemStack[] possibleStacks;

	public RecipeIngredient(ByteBuf serverdata) {
		int possibleStacksCount = VarNumberSerializer.readVarInt(serverdata);
		possibleStacks = new NetworkItemStack[possibleStacksCount];
		for (int i = 0; i < possibleStacksCount; i++) {
			possibleStacks[i] = ItemStackSerializer.readItemStack(serverdata);
		}
	}

	public NetworkItemStack[] getPossibleItemStacks() {
		return possibleStacks;
	}

}