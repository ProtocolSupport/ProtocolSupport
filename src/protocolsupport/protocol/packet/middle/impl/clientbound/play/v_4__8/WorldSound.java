package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleWorldSound;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.typeremapper.basic.SoundTransformer;
import protocolsupport.protocol.utils.minecraftdata.MinecraftSoundData;
import protocolsupport.utils.MiscUtils;

public class WorldSound extends MiddleWorldSound implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7,
IClientboundMiddlePacketV8 {

	public WorldSound(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		String soundname = id != null ? MinecraftSoundData.getNameById(id) : name;
		if (soundname == null) {
			return;
		}
		io.writeClientbound(createCustomSound(version, x, y, z, soundname, volume, pitch));
	}

	public static ClientBoundPacketData createCustomSound(
		ProtocolVersion version,
		double fxX, double fpY, double fpZ,
		String sound, float volume, float pitch
	) {
		return createCustomSound(version, (int) (fxX * 8), (int) (fpY * 8), (int) (fpZ * 8), sound, volume, pitch);
	}

	public static ClientBoundPacketData createCustomSound(
		ProtocolVersion version,
		int x, int y, int z,
		String sound, float volume, float pitch
	) {
		ClientBoundPacketData worldcustomsound = ClientBoundPacketData.create(ClientBoundPacketType.LEGACY_PLAY_WORLD_CUSTOM_SOUND);
		sound = SoundTransformer.getSoundName(version, sound);
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
