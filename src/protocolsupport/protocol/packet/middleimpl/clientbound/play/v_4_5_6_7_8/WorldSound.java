package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldSound;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.basic.SoundRemapper;
import protocolsupport.utils.Utils;

public class WorldSound extends MiddleWorldSound {

	public WorldSound(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		String soundname = SoundRemapper.getSoundName(version, id);
		if (soundname != null) {
			ClientBoundPacketData worldsound = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WORLD_CUSTOM_SOUND);
			if (version.isBefore(ProtocolVersion.MINECRAFT_1_6_1)) {
				soundname = Utils.clampString(soundname, 32);
			}
			StringSerializer.writeString(worldsound, version, soundname);
			worldsound.writeInt(x);
			worldsound.writeInt(y);
			worldsound.writeInt(z);
			worldsound.writeFloat(volume);
			worldsound.writeByte((int) (pitch * 63.5));
			codec.write(worldsound);
		}
	}

}
