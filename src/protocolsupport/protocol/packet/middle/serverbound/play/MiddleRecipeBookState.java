package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.utils.EnumConstantLookups.EnumConstantLookup;

public abstract class MiddleRecipeBookState extends ServerBoundMiddlePacket {

	public MiddleRecipeBookState(MiddlePacketInit init) {
		super(init);
	}

	protected RecipeBookType bookType;
	protected boolean bookOpen;
	protected boolean bookFiltering;

	@Override
	protected void writeToServer() {
		codec.read(create(bookType, bookOpen, bookFiltering));
	}

	public static ServerBoundPacketData create(RecipeBookType bookType, boolean bookOpen, boolean bookFiltering) {
		ServerBoundPacketData recipebookstate = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_RECIPE_BOOK_STATE);
		MiscSerializer.writeVarIntEnum(recipebookstate, bookType);
		recipebookstate.writeBoolean(bookOpen);
		recipebookstate.writeBoolean(bookFiltering);
		return recipebookstate;
	}

	public static enum RecipeBookType {
		CRAFTING, FURNACE, BLAST_FURNACE, SMOKER;
		public static final EnumConstantLookup<RecipeBookType> CONSTANT_LOOKUP = new EnumConstantLookup<>(RecipeBookType.class);
	}

}
