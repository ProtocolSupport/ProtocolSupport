package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.tab.TabAPI;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.Environment;
import protocolsupport.protocol.types.GameMode;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public abstract class MiddleStartGame extends ClientBoundMiddlePacket {

	public MiddleStartGame(ConnectionImpl connection) {
		super(connection);
	}

	protected int playerEntityId;
	protected GameMode gamemode;
	protected boolean hardcore;
	protected Environment dimension;
	protected int maxplayers;
	protected String leveltype;
	protected int renderDistance;
	protected boolean reducedDebugInfo;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		playerEntityId = serverdata.readInt();
		int gmdata = serverdata.readByte();
		gamemode = GameMode.getById(gmdata & 0xFFFFFFF7);
		hardcore = (gmdata & 0x8) == 0x8;
		dimension = Environment.getById(serverdata.readInt());
		serverdata.readByte();
		maxplayers = TabAPI.getMaxTabSize();
		leveltype = StringSerializer.readVarIntUTF8String(serverdata);
		renderDistance = VarNumberSerializer.readVarInt(serverdata);
		reducedDebugInfo = serverdata.readBoolean();
	}

	@Override
	public boolean postFromServerRead() {
		cache.getWatchedEntityCache().addWatchedSelfPlayer(NetworkEntity.createPlayer(playerEntityId));
		cache.getAttributesCache().setCurrentDimension(dimension);
		return true;
	}

}
