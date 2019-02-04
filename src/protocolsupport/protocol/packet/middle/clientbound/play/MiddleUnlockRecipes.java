package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.utils.EnumConstantLookups;

public abstract class MiddleUnlockRecipes extends ClientBoundMiddlePacket {

	public MiddleUnlockRecipes(ConnectionImpl connection) {
		super(connection);
	}

	protected Action action;
	protected boolean craftRecipeBookOpen;
	protected boolean craftRecipeBookFiltering;
	protected boolean smeltingRecipeBookOpen;
	protected boolean smeltingRecipeBookFiltering;
	protected String[] recipes1;
	protected String[] recipes2;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		action = MiscSerializer.readVarIntEnum(serverdata, Action.CONSTANT_LOOKUP);
		craftRecipeBookOpen = serverdata.readBoolean();
		craftRecipeBookFiltering = serverdata.readBoolean();
		smeltingRecipeBookOpen = serverdata.readBoolean();
		smeltingRecipeBookFiltering = serverdata.readBoolean();
		recipes1 = ArraySerializer.readVarIntVarIntUTF8StringArray(serverdata);
		if (action == Action.INIT) {
			recipes2 = ArraySerializer.readVarIntVarIntUTF8StringArray(serverdata);
		}
	}

	protected static enum Action {
		INIT, ADD, REMOVE;
		public static final EnumConstantLookups.EnumConstantLookup<Action> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(Action.class);
	}

}
