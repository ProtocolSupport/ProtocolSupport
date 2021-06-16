package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.utils.EnumConstantLookup;

public abstract class MiddleRecipeBookState extends ServerBoundMiddlePacket {

	protected MiddleRecipeBookState(MiddlePacketInit init) {
		super(init);
	}

	protected RecipeBookType bookType;
	protected boolean bookOpen;
	protected boolean bookFiltering;

	@Override
	protected void write() {
		codec.writeServerbound(create(bookType, bookOpen, bookFiltering));
	}

	public static ServerBoundPacketData create(RecipeBookType bookType, boolean bookOpen, boolean bookFiltering) {
		ServerBoundPacketData recipebookstate = ServerBoundPacketData.create(ServerBoundPacketType.SERVERBOUND_PLAY_RECIPE_BOOK_STATE);
		MiscSerializer.writeVarIntEnum(recipebookstate, bookType);
		recipebookstate.writeBoolean(bookOpen);
		recipebookstate.writeBoolean(bookFiltering);
		return recipebookstate;
	}

	public enum RecipeBookType {
		CRAFTING, FURNACE, BLAST_FURNACE, SMOKER;
		public static final EnumConstantLookup<RecipeBookType> CONSTANT_LOOKUP = new EnumConstantLookup<>(RecipeBookType.class);
	}

}
