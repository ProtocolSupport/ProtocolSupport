package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_14r1_14r2_15_16r1;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleRecipeBookRecipe;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleRecipeBookState;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleRecipeBookState.RecipeBookType;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV16r1;
import protocolsupport.protocol.utils.EnumConstantLookup;

public class RecipeBookData extends ServerBoundMiddlePacket implements
IServerboundMiddlePacketV14r1,
IServerboundMiddlePacketV14r2,
IServerboundMiddlePacketV15,
IServerboundMiddlePacketV16r1 {

	public RecipeBookData(IMiddlePacketInit init) {
		super(init);
	}

	protected Type type;
	protected String recipeId;
	protected boolean craftRecipeBookOpen;
	protected boolean craftRecipeBookFiltering;
	protected boolean smeltingRecipeBookOpen;
	protected boolean smeltingRecipeBookFiltering;
	protected boolean blastingRecipeBookOpen;
	protected boolean blastingRecipeBookFiltering;
	protected boolean smokingRecipeBookOpen;
	protected boolean smokingRecipeBookFiltering;

	@Override
	protected void read(ByteBuf clientdata) {
		type = MiscDataCodec.readVarIntEnum(clientdata, Type.CONSTANT_LOOKUP);
		switch (type) {
			case DISPLAYED_RECIPE: {
				recipeId = StringCodec.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
				break;
			}
			case RECIPE_BOOK_STATUS: {
				craftRecipeBookOpen = clientdata.readBoolean();
				craftRecipeBookFiltering = clientdata.readBoolean();
				smeltingRecipeBookOpen = clientdata.readBoolean();
				smeltingRecipeBookFiltering = clientdata.readBoolean();
				blastingRecipeBookOpen = clientdata.readBoolean();
				blastingRecipeBookFiltering = clientdata.readBoolean();
				smokingRecipeBookOpen = clientdata.readBoolean();
				smokingRecipeBookFiltering = clientdata.readBoolean();
				break;
			}
		}
	}

	@Override
	protected void write() {
		switch (type) {
			case DISPLAYED_RECIPE: {
				io.writeServerbound(MiddleRecipeBookRecipe.create(recipeId));
				break;
			}
			case RECIPE_BOOK_STATUS: {
				io.writeServerbound(MiddleRecipeBookState.create(RecipeBookType.CRAFTING, craftRecipeBookOpen, craftRecipeBookFiltering));
				io.writeServerbound(MiddleRecipeBookState.create(RecipeBookType.FURNACE, smeltingRecipeBookOpen, smeltingRecipeBookFiltering));
				io.writeServerbound(MiddleRecipeBookState.create(RecipeBookType.BLAST_FURNACE, blastingRecipeBookOpen, blastingRecipeBookFiltering));
				io.writeServerbound(MiddleRecipeBookState.create(RecipeBookType.SMOKER, smokingRecipeBookOpen, smokingRecipeBookFiltering));
				break;
			}
		}
	}

	protected enum Type {
		DISPLAYED_RECIPE, RECIPE_BOOK_STATUS;
		public static final EnumConstantLookup<Type> CONSTANT_LOOKUP = new EnumConstantLookup<>(Type.class);
	}

}
