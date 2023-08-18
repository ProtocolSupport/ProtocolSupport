package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_10__18;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleWorldSound;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;
import protocolsupport.protocol.typeremapper.basic.SoundTransformer;
import protocolsupport.protocol.types.SoundCategory;
import protocolsupport.protocol.utils.minecraftdata.MinecraftSoundData;

public class WorldSound extends MiddleWorldSound implements
IClientboundMiddlePacketV10,
IClientboundMiddlePacketV11,
IClientboundMiddlePacketV12r1,
IClientboundMiddlePacketV12r2,
IClientboundMiddlePacketV13,
IClientboundMiddlePacketV14r1,
IClientboundMiddlePacketV14r2,
IClientboundMiddlePacketV15,
IClientboundMiddlePacketV16r1,
IClientboundMiddlePacketV16r2,
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2,
IClientboundMiddlePacketV18 {

	public WorldSound(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		String soundname = id != null ? MinecraftSoundData.getNameById(id) : name;
		if (soundname == null) {
			return;
		}
		io.writeClientbound(WorldSound.createCustomSound(version, x, y, z, soundname, category, volume, pitch));
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
		worldcustomsound.writeFloat(pitch);
		return worldcustomsound;
	}

}
