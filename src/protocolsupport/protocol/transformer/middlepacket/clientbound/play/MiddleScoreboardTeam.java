package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleScoreboardTeam<T> extends ClientBoundMiddlePacket<T> {

	protected String name;
	protected int mode;
	protected String displayName;
	protected String prefix;
	protected String suffix;
	protected int friendlyFire;
	protected String nameTagVisibility;
	protected int color;
	protected String[] players;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		name = serializer.readString(16);
		mode = serializer.readUnsignedByte();
		if (mode == 0 || mode == 2) {
			displayName = serializer.readString(32);
			prefix = serializer.readString(16);
			suffix = serializer.readString(16);
			friendlyFire = serializer.readUnsignedByte();
			nameTagVisibility = serializer.readString(32);
			color = serializer.readUnsignedByte();
		}
		if (mode == 0 || mode == 3 || mode == 4) {
			players = new String[serializer.readVarInt()];
			for (int i = 0; i < players.length; i++) {
				players[i] = serializer.readString(40);
			}
		}
	}

}
