package protocolsupport.protocol.typeremapper.pe;

import java.util.Arrays;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;

public class PEAdventureSettings {

	public static final int ADVENTURE_MODE_ENABLED = 0x1;
	public static final int PVP_DISABLED = 0x2;
	public static final int PVE_DISABLED = 0x4;
	public static final int AUTOJUMP_ENABLED = 0x20;
	public static final int ALLOW_FLIGHT = 0x40;
	public static final int NOCLIP_ENABLED = 0x80;
	public static final int FLYING = 0x200;

	public static int getGameModeFlags(int gamemode) {
		switch (gamemode) {
			case 2: {
				return ADVENTURE_MODE_ENABLED;
			}
			case 3: {
				return PVP_DISABLED | PVE_DISABLED | ALLOW_FLIGHT | FLYING | NOCLIP_ENABLED;
			}
			default: {
				return 0;
			}
		}
	}

	public static ClientBoundPacketData createPacket(int... flags) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ADVENTURE_SETTINGS, ProtocolVersion.MINECRAFT_PE);
		VarNumberSerializer.writeVarInt(serializer, Arrays.stream(flags).reduce(0, (left, right) -> left | right));
		VarNumberSerializer.writeVarInt(serializer, 0); //permissions level, 0 - user
		return serializer;
	}

	public static ClientBoundPacketData createPacket(NetworkDataCache cache) {
		return PEAdventureSettings.createPacket(
			PEAdventureSettings.getGameModeFlags(cache.getGameMode()),
			PEAdventureSettings.AUTOJUMP_ENABLED,
			cache.canFly() ? PEAdventureSettings.ALLOW_FLIGHT : 0,
			cache.isFlying() ? PEAdventureSettings.FLYING : 0
		);
	}

}
