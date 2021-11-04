package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleWorldCustomSound;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.typeremapper.basic.SoundRemapper;
import protocolsupport.utils.MiscUtils;

public class WorldCustomSound extends MiddleWorldCustomSound implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7,
IClientboundMiddlePacketV8 {

	public WorldCustomSound(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		io.writeClientbound(create(version, x, y, z, id, volume, pitch));
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
			sound = MiscUtils.clampString(sound, 32);
		}
		StringCodec.writeString(worldcustomsound, version, sound);
		worldcustomsound.writeInt(x);
		worldcustomsound.writeInt(y);
		worldcustomsound.writeInt(z);
		worldcustomsound.writeFloat(volume);
		worldcustomsound.writeByte((int) (pitch * 63.5));
		return worldcustomsound;
	}

}
