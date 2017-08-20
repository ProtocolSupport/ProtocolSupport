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

	//Permissions? Arg.
	public static final int PERMISSIONS_PROHIBIT_ALL = 0x00;
	public static final int PERMISSIONS_BUILD_AND_MINE = 0x01;
	public static final int PERMISSIONS_DOORS_AND_SWITCHES = 0x02;
	public static final int PERMISSIONS_OPEN_CONTAINERS = 0x04;
	public static final int PERMISSIONS_ATTACK_PLAYERS = 0x08;
	public static final int PERMISSIONS_ATTACK_MOBS = 0x10;
	public static final int PERMISSIONS_OP = 0x20;
	public static final int PERMISSIONS_TELEPORT = 0x40;
	public static final int PERMISSIONS_ALLOW_ALL = 0x1FF;
	//Permission groups? ^^
	public static final int GROUP_VISITOR = 0;
	public static final int GROUP_NORMAL = 1;
	public static final int GROUP_OP = 2;
	public static final int GROUP_CUSTOM = 3;

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

	public static ClientBoundPacketData createPacket(int entityId, int... flags) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ADVENTURE_SETTINGS, ProtocolVersion.MINECRAFT_PE);
		VarNumberSerializer.writeVarInt(serializer, Arrays.stream(flags).reduce(0, (left, right) -> left | right));
		VarNumberSerializer.writeVarInt(serializer, 0); //?
		//TODO: Actually work with permissions?
		VarNumberSerializer.writeVarInt(serializer, PERMISSIONS_ALLOW_ALL);
		VarNumberSerializer.writeVarInt(serializer, GROUP_NORMAL);
		VarNumberSerializer.writeVarInt(serializer, 0); //? (custom flags)
		VarNumberSerializer.writeSVarLong(serializer, 0);
		return serializer;
	}

	public static ClientBoundPacketData createPacket(NetworkDataCache cache) {
		return PEAdventureSettings.createPacket(
			cache.getSelfPlayerEntityId(),
			PEAdventureSettings.getGameModeFlags(cache.getGameMode()),
			PEAdventureSettings.AUTOJUMP_ENABLED,
			cache.canFly() ? PEAdventureSettings.ALLOW_FLIGHT : 0,
			cache.isFlying() ? PEAdventureSettings.FLYING : 0
		);
	}

}
