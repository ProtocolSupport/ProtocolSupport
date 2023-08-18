package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleWorldSound;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;
import protocolsupport.protocol.typeremapper.basic.SoundTransformer;
import protocolsupport.protocol.types.SoundCategory;
import protocolsupport.protocol.utils.minecraftdata.MinecraftSoundData;

public class WorldSound extends MiddleWorldSound implements
IClientboundMiddlePacketV20 {

	public WorldSound(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		String soundname = id != null ? MinecraftSoundData.getNameById(id) : name;
		if (soundname == null) {
			return;
		}
		io.writeClientbound(createCustomSound(version, x, y, z, soundname, null, category, volume, pitch, seed));
	}

	public static ClientBoundPacketData createCustomSound(
		ProtocolVersion version,
		double x, double y, double z,
		String sound, Float fixedrange, SoundCategory category, float volume, float pitch, long seed
	) {
		return createCustomSound(version, (int) (x * 8), (int) (y * 8), (int) (z * 8), sound, fixedrange, category, volume, pitch, seed);
	}

	public static ClientBoundPacketData createCustomSound(
		ProtocolVersion version,
		int fpX, int fpY, int fpZ,
		String sound, Float fixedrange, SoundCategory category, float volume, float pitch, long seed
	) {
		ClientBoundPacketData worldsoundPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WORLD_SOUND);
		VarNumberCodec.writeVarInt(worldsoundPacket, 0);
		StringCodec.writeVarIntUTF8String(worldsoundPacket, SoundTransformer.getSoundName(version, sound));
		OptionalCodec.writeOptional(worldsoundPacket, fixedrange, ByteBuf::writeFloat);
		MiscDataCodec.writeVarIntEnum(worldsoundPacket, category);
		worldsoundPacket.writeInt(fpX);
		worldsoundPacket.writeInt(fpY);
		worldsoundPacket.writeInt(fpZ);
		worldsoundPacket.writeFloat(volume);
		worldsoundPacket.writeFloat(pitch);
		worldsoundPacket.writeLong(seed);
		return worldsoundPacket;
	}

}
