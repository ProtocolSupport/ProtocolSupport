package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldCustomSound;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.basic.SoundRemapper;
import protocolsupport.utils.Utils;

public class WorldCustomSound extends MiddleWorldCustomSound {

	public WorldCustomSound(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData worldcustomsound = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_WORLD_CUSTOM_SOUND);
		id = SoundRemapper.getSoundName(version, id);
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_5_2)) {
			id = Utils.clampString(id, 32);
		}
		StringSerializer.writeString(worldcustomsound, version, id);
		worldcustomsound.writeInt(x);
		worldcustomsound.writeInt(y);
		worldcustomsound.writeInt(z);
		worldcustomsound.writeFloat(volume);
		worldcustomsound.writeByte((int) (pitch * 63.5));
		codec.write(worldcustomsound);
	}

}
