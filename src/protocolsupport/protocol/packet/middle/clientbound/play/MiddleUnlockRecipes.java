package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;

public abstract class MiddleUnlockRecipes extends ClientBoundMiddlePacket {

	protected int action;
	protected boolean openBook;
	protected boolean enableFiltering;
	protected int[] recipes1;
	protected int[] recipes2;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		action = serverdata.readShort();
		openBook = serverdata.readBoolean();
		enableFiltering = serverdata.readBoolean();
		recipes1 = ArraySerializer.readVarIntIntArray(serverdata);
		recipes2 = ArraySerializer.readVarIntIntArray(serverdata);
	}

}
