package protocolsupport.protocol.typeremapper.pe;

import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.Position;

public class PESoundLevelEvent {

	public static final int RECORD_13 = 101;
	public static final int RECORD_CAT = 102;
	public static final int RECORD_BLOCKS = 103;
	public static final int RECORD_CHIRP = 104;
	public static final int RECORD_FAR = 105;
	public static final int RECORD_MALL = 106;
	public static final int RECORD_MELLOHI = 107;
	public static final int RECORD_STAL = 108;
	public static final int RECORD_STRAD = 109;
	public static final int RECORD_WARD = 110;
	public static final int RECORD_11 = 111;
	public static final int RECORD_WAIT = 112;
	public static final int STOP_RECORD = 113;

	public static ClientBoundPacketData createPacket(int soundEvent, float x, float y, float z, int extraData, int pitch, boolean isBabyMob, boolean disableRelativeVolume) {
		ClientBoundPacketData clientLevelEvent = ClientBoundPacketData.create(PEPacketIDs.LEVEL_SOUND_EVENT);
		clientLevelEvent.writeByte(soundEvent);
		clientLevelEvent.writeFloatLE(x);
		clientLevelEvent.writeFloatLE(y);
		clientLevelEvent.writeFloatLE(z);
		VarNumberSerializer.writeSVarInt(clientLevelEvent, extraData);
		VarNumberSerializer.writeSVarInt(clientLevelEvent, pitch);
		clientLevelEvent.writeBoolean(isBabyMob);
		clientLevelEvent.writeBoolean(disableRelativeVolume);
		return clientLevelEvent;
	}

	public static ClientBoundPacketData createPacket(int levelEvent, Position position, int extraData, int pitch, boolean isBabyMob) {
		return createPacket(levelEvent, position.getX(), position.getY(), position.getZ(), extraData, pitch, isBabyMob, false);
	}

	public static ClientBoundPacketData createPacket(int levelEvent, Position position, int extraData, int pitch) {
		return createPacket(levelEvent, position, extraData, pitch, false);
	}

	public static ClientBoundPacketData createPacket(int levelEvent, Position position, int extraData) {
		return createPacket(levelEvent, position, extraData, 1);
	}

	public static ClientBoundPacketData createPacket(int levelEvent, Position position) {
		return createPacket(levelEvent, position, -1);
	}
}