package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_9r1__9r2;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleWorldSound;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;
import protocolsupport.protocol.typeremapper.basic.SoundTransformer;
import protocolsupport.protocol.types.SoundCategory;
import protocolsupport.protocol.utils.minecraftdata.MinecraftSoundData;

public class WorldSound extends MiddleWorldSound implements
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2 {

	public WorldSound(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		String soundname = id != null ? MinecraftSoundData.getNameById(id) : name;
		if (soundname == null) {
			return;
		}
		io.writeClientbound(createCustomSound(version, x, y, z, soundname, category, volume, pitch));
	}

	public static ClientBoundPacketData createCustomSound(
		ProtocolVersion version,
		double x, double y, double z,
		String sound, SoundCategory category, float volume, float pitch
	) {
		return createCustomSound(version, (int) (x * 8), (int) (y * 8), (int) (z * 8), sound, category, volume, pitch);
	}

	public static ClientBoundPacketData createCustomSound(
		ProtocolVersion version,
		int fpX, int fpY, int fpZ,
		String sound, SoundCategory category, float volume, float pitch
	) {
		ClientBoundPacketData worldcustomsound = ClientBoundPacketData.create(ClientBoundPacketType.LEGACY_PLAY_WORLD_CUSTOM_SOUND);
		StringCodec.writeVarIntUTF8String(worldcustomsound, SoundTransformer.getSoundName(version, sound));
		MiscDataCodec.writeVarIntEnum(worldcustomsound, category);
		worldcustomsound.writeInt(fpX);
		worldcustomsound.writeInt(fpY);
		worldcustomsound.writeInt(fpZ);
		worldcustomsound.writeFloat(volume);
		worldcustomsound.writeByte((int) (pitch * 63.5));
		return worldcustomsound;
	}


}
