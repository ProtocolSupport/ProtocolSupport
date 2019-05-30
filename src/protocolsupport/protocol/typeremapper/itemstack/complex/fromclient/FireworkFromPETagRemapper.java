package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTByte;
import protocolsupport.protocol.types.nbt.NBTByteArray;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTIntArray;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTType;

public class FireworkFromPETagRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		NBTCompound tag = itemstack.getNBT();
		if (tag == null) {
			return itemstack;
		}

		// For firework stars, we have an Explosion top level tag
		NBTCompound explosion = tag.getTagOfType("Explosion", NBTType.COMPOUND);
		if (explosion != null) {
			tag.setTag("Explosion", remapExplosion(explosion));
		}

		// For firework rockets, we have a Fireworks top level tag
		NBTCompound fireworks = tag.getTagOfType("Fireworks", NBTType.COMPOUND);
		if (fireworks != null) {
			NBTList<NBTCompound> explosions = fireworks.getTagListOfType("Explosions", NBTType.COMPOUND);
			if (explosions != null && !explosions.isEmpty()) {
				// PE always has Explosions list, even if it is empty. Only remap
				// if non-empty, otherwise drop tag.
				fireworks.setTag("Explosions", remapExplosions(explosions));
			}
		}
		return itemstack;
	}

	private NBTList<NBTCompound> remapExplosions(NBTList<NBTCompound> peExplosions) {
		NBTList<NBTCompound> pcExplosions = new NBTList<>(NBTType.COMPOUND);
		for (int i = 0; i < peExplosions.size(); i++) {
			pcExplosions.addTag(remapExplosion(peExplosions.getTag(i)));
		}
		return pcExplosions;
	}

	private NBTCompound remapExplosion(NBTCompound peExplosion) {
		NBTCompound pcExplosion = new NBTCompound();
		NBTByteArray colors = pcExplosion.getTagOfType("FireworkColor", NBTType.BYTE_ARRAY);
		if (colors != null) {
			peExplosion.setTag("Colors", new NBTIntArray(remapColors(colors.getValue())));
		}
		NBTByteArray fadeColors = pcExplosion.getTagOfType("FireworkFade", NBTType.BYTE_ARRAY);
		if (fadeColors != null) {
			peExplosion.setTag("FadeColors", new NBTIntArray(remapColors(fadeColors.getValue())));
		}
		NBTByte flicker = pcExplosion.getTagOfType("FireworkFlicker", NBTType.BYTE);
		if (flicker != null) {
			peExplosion.setTag("Flicker", new NBTByte(flicker.getAsByte()));
		}
		NBTByte trail = pcExplosion.getTagOfType("FireworkTrail", NBTType.BYTE);
		if (trail != null) {
			peExplosion.setTag("Trail", new NBTByte(trail.getAsByte()));
		}
		NBTByte type = pcExplosion.getTagOfType("FireworkType", NBTType.BYTE);
		if (type != null) {
			peExplosion.setTag("Type", new NBTByte(type.getAsByte()));
		}

		return pcExplosion;
	}

	private int[] remapColors(byte[] peColors) {
		int[] pcColors = new int[peColors.length];
		for (int i = 0; i < peColors.length; i++) {
			pcColors[i] = remapColor(peColors[i]);
		}
		return pcColors;
	}

	//Fireworks have a custom color system. Since PE only supports the Dye colors, here's a basic switcher for now.
	private int remapColor(byte peColor) {
		switch (peColor) {
			case  0:			return  1973019;
			case  1:			return 11743532;
			case  2:			return  3887386;
			case  3:			return  5320730;
			case  4:			return  2437522;
			case  5:			return  8073150;
			case  6:			return  2651799;
			case  7:			return 11250603;
			case  8:			return  4408131;
			case  9:			return 14188952;
			case 10:			return  4312372;
			case 11:			return 14602026;
			case 12:			return  6719955;
			case 13:			return 12801229;
			case 14:			return 15435844;
			default:
			case 15:			return 15790320;
		}
	}
}
