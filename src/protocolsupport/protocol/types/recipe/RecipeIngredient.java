package protocolsupport.protocol.types.recipe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.types.NetworkItemStack;

public class RecipeIngredient {

	protected final NetworkItemStack[] possibleStacks;

	public RecipeIngredient(ByteBuf serverdata) {
		int possibleStacksCount = VarNumberCodec.readVarInt(serverdata);
		possibleStacks = new NetworkItemStack[possibleStacksCount];
		for (int i = 0; i < possibleStacksCount; i++) {
			possibleStacks[i] = ItemStackCodec.readItemStack(serverdata);
		}
	}

	public NetworkItemStack[] getPossibleItemStacks() {
		return possibleStacks;
	}

}