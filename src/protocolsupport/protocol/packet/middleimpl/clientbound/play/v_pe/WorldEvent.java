package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import gnu.trove.map.hash.TIntIntHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.pe.PELevelEvent;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldEvent;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class WorldEvent extends MiddleWorldEvent {

	private static final TIntIntHashMap remaps = new TIntIntHashMap();
	static {
		remaps.put(1000, PELevelEvent.SOUND_CLICK);
		remaps.put(1001, PELevelEvent.SOUND_CLICK_FAIL);
		remaps.put(1002, PELevelEvent.SOUND_SHOOT);
//		1003 	Ender eye launched 	
//		1004 	Firework shot 	
		remaps.put(1005, PELevelEvent.SOUND_DOOR);
		remaps.put(1006, PELevelEvent.SOUND_DOOR);
		remaps.put(1007, PELevelEvent.SOUND_DOOR);
		remaps.put(1008, PELevelEvent.SOUND_DOOR);
//		1009 	Fire extinguished 	
//		1010 	Play record 	Record ID
		remaps.put(1011, PELevelEvent.SOUND_DOOR);
		remaps.put(1012, PELevelEvent.SOUND_DOOR);
		remaps.put(1013, PELevelEvent.SOUND_DOOR);
		remaps.put(1014, PELevelEvent.SOUND_DOOR);
		remaps.put(1015, PELevelEvent.SOUND_GHAST);
		remaps.put(1016, PELevelEvent.SOUND_GHAST_SHOOT);
		remaps.put(1017, PELevelEvent.SOUND_BLAZE_SHOOT);
		remaps.put(1018, PELevelEvent.SOUND_BLAZE_SHOOT);
		remaps.put(1019, PELevelEvent.SOUND_DOOR_BUMP);
		remaps.put(1020, PELevelEvent.SOUND_DOOR_BUMP);
		remaps.put(1021, PELevelEvent.SOUND_DOOR_CRASH);
		remaps.put(1022, PELevelEvent.SOUND_DOOR_CRASH);	
//		1023 	Wither spawned 	
		remaps.put(1024, PELevelEvent.SOUND_BLAZE_SHOOT);
//		1025 	Bat takes off 	
//		1026 	Zombie infects 	
//		1027 	Zombie villager converted 	
//		1028 	Ender dragon death 	
		remaps.put(1029, PELevelEvent.SOUND_ANVIL_BREAK);
		remaps.put(1030, PELevelEvent.SOUND_ANVIL_USE);
		remaps.put(1031, PELevelEvent.SOUND_ANVIL_FALL);
		remaps.put(1032, PELevelEvent.SOUND_PORTAL);
//		1033 	Chorus flower grown 	
//		1034 	Chorus flower died 	
//		1035 	Brewing stand brewed 	
		remaps.put(1036, PELevelEvent.SOUND_DOOR);
		remaps.put(1037, PELevelEvent.SOUND_DOOR);
		remaps.put(2000, PELevelEvent.PARTICLE_SHOOT);
		remaps.put(2001, PELevelEvent.PARTICLE_DESTROY);
		remaps.put(2002, PELevelEvent.PARTICLE_SPLASH);
		remaps.put(2003, PELevelEvent.PARTICLE_EYE_DESPAWN);
		remaps.put(2004, PELevelEvent.PARTICLE_SPAWN);
		remaps.put(2005, PELevelEvent.PARTICLE_BONEMEAL);
//		2006 	Dragon breath 	
//		3000 	End gateway spawn 	
//		3001 	Enderdragon growl 
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		return RecyclableSingletonList.create(PELevelEvent.createPacket(remaps.get(effectId), position.getX(), position.getY(), position.getZ(), data));
	}

}
