package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.utils.EnumConstantLookup;

public abstract class MiddleRecipeBookState extends ServerBoundMiddlePacket {

	protected MiddleRecipeBookState(IMiddlePacketInit init) {
		super(init);
	}

	protected RecipeBookType bookType;
	protected boolean bookOpen;
	protected boolean bookFiltering;

	@Override
	protected void write() {
		io.writeServerbound(create(bookType, bookOpen, bookFiltering));
	}

	public static ServerBoundPacketData create(RecipeBookType bookType, boolean bookOpen, boolean bookFiltering) {
		ServerBoundPacketData recipebookstate = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_RECIPE_BOOK_STATE);
		MiscDataCodec.writeVarIntEnum(recipebookstate, bookType);
		recipebookstate.writeBoolean(bookOpen);
		recipebookstate.writeBoolean(bookFiltering);
		return recipebookstate;
	}

	public enum RecipeBookType {
		CRAFTING, FURNACE, BLAST_FURNACE, SMOKER;
		public static final EnumConstantLookup<RecipeBookType> CONSTANT_LOOKUP = new EnumConstantLookup<>(RecipeBookType.class);
	}

}
