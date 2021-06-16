package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldCustomSound;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.basic.SoundRemapper;
import protocolsupport.utils.Utils;

public class WorldCustomSound extends MiddleWorldCustomSound {

	public WorldCustomSound(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		codec.writeClientbound(create(version, x, y, z, id, volume, pitch));
	}

	public static ClientBoundPacketData create(
		ProtocolVersion version,
		double x, double y, double z,
		String sound, float volume, float pitch
	) {
		return create(version, (int) (x * 8), (int) (y * 8), (int) (z * 8), sound, volume, pitch);
	}

	public static ClientBoundPacketData create(
		ProtocolVersion version,
		int x, int y, int z,
		String sound, float volume, float pitch
	) {
		ClientBoundPacketData worldcustomsound = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WORLD_CUSTOM_SOUND);
		sound = SoundRemapper.getSoundName(version, sound);
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_5_2)) {
			sound = Utils.clampString(sound, 32);
		}
		StringSerializer.writeString(worldcustomsound, version, sound);
		worldcustomsound.writeInt(x);
		worldcustomsound.writeInt(y);
		worldcustomsound.writeInt(z);
		worldcustomsound.writeFloat(volume);
		worldcustomsound.writeByte((int) (pitch * 63.5));
		return worldcustomsound;
	}

}
