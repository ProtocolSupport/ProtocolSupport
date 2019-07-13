package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTByte;
import protocolsupport.protocol.types.nbt.NBTByteArray;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTIntArray;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTType;

public class FireworkToPETagSpecificRemapper implements ItemStackComplexRemapper {

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
			fireworks.setTag("Explosions", remapExplosions(explosions));
		}
		return itemstack;
	}

	private NBTList<NBTCompound> remapExplosions(NBTList<NBTCompound> pcExplosions) {
		NBTList<NBTCompound> peExplosions = new NBTList<>(NBTType.COMPOUND);
		// PE always need Explosions list, even if it is empty
		if (pcExplosions != null) {
			for (int i = 0; i < pcExplosions.size(); i++) {
				peExplosions.addTag(remapExplosion(pcExplosions.getTag(i)));
			}
		}
		return peExplosions;
	}

	private NBTCompound remapExplosion(NBTCompound pcExplosion) {
		NBTCompound peExplosion = new NBTCompound();
		NBTIntArray colors = pcExplosion.getTagOfType("Colors", NBTType.INT_ARRAY);
		if (colors != null) {
			peExplosion.setTag("FireworkColor", new NBTByteArray(remapColors(colors.getValue())));
		}
		NBTIntArray fadeColors = pcExplosion.getTagOfType("FadeColors", NBTType.INT_ARRAY);
		if (fadeColors != null) {
			peExplosion.setTag("FireworkFade", new NBTByteArray(remapColors(fadeColors.getValue())));
		}
		NBTByte flicker = pcExplosion.getTagOfType("Flicker", NBTType.BYTE);
		if (flicker != null) {
			peExplosion.setTag("FireworkFlicker", new NBTByte(flicker.getAsByte()));
		}
		NBTByte trail = pcExplosion.getTagOfType("Trail", NBTType.BYTE);
		if (trail != null) {
			peExplosion.setTag("FireworkTrail", new NBTByte(trail.getAsByte()));
		}
		NBTByte type = pcExplosion.getTagOfType("Type", NBTType.BYTE);
		if (type != null) {
			peExplosion.setTag("FireworkType", new NBTByte(type.getAsByte()));
		}
		return peExplosion;
	}

	private byte[] remapColors(int[] pcColors) {
		byte[] peColors = new byte[pcColors.length];
		for (int i = 0; i < pcColors.length; i++) {
			peColors[i] = remapColor(pcColors[i]);
		}
		return peColors;
	}

	//Fireworks have a custom color system. Since PE only supports the Dye colors, here's a basic switcher for now.
	private byte remapColor(int pcColor) {
		switch (pcColor) {
			case 1973019:				return  0;
			case 11743532:				return  1;
			case 3887386:				return  2;
			case 5320730:				return  3;
			case 2437522:				return  4;
			case 8073150:				return  5;
			case 2651799:				return  6;
			case 11250603:				return  7;
			case 4408131:				return  8;
			case 14188952:				return  9;
			case 4312372:				return 10;
			case 14602026:				return 11;
			case 6719955:				return 12;
			case 12801229:				return 13;
			case 15435844: 				return 14;
			default:
			case 15790320: 				return 15;
		}
	}

}
