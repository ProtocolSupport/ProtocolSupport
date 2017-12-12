package protocolsupport.protocol.typeremapper.itemstack.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackSpecificRemapper;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

public class FireworkToPETagSpecificRemapper implements ItemStackSpecificRemapper {

	@Override
	public ItemStackWrapper remap(ProtocolVersion version, String locale, ItemStackWrapper itemstack) {
		NBTTagCompoundWrapper tag = itemstack.getTag();
		if (tag.isNull()) {
			return itemstack;
		}
		if(tag.hasKeyOfType("Explosion", NBTTagType.COMPOUND)) {
			tag.setCompound("Explosion", remapExplosion(tag.getCompound("Explosion")));
		}
		if(tag.hasKeyOfType("Fireworks", NBTTagType.COMPOUND)) {
			NBTTagCompoundWrapper fireworks = tag.getCompound("Fireworks");
			if(fireworks.hasKeyOfType("Explosions", NBTTagType.LIST)) {
				fireworks.setList("Explosions", remapExplosions(fireworks.getList("Explosions")));
			}
		}
		return itemstack;
	}

	private NBTTagListWrapper remapExplosions(NBTTagListWrapper pcExplosions) {
		NBTTagListWrapper peExplosions = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
		for (int i = 0; i < pcExplosions.size(); i++) {
			peExplosions.addCompound(remapExplosion(pcExplosions.getCompound(i)));
		}
		return peExplosions;
	}

	private NBTTagCompoundWrapper remapExplosion(NBTTagCompoundWrapper pcExplosion) {
		NBTTagCompoundWrapper peExplosion = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
		peExplosion.setByteArray("FireworkColor", remapColors(pcExplosion.getIntArray("Colors")));
		peExplosion.setByteArray("FireworkFade", remapColors(pcExplosion.getIntArray("FadeColors")));
		peExplosion.setByte("FireworkFlicker", pcExplosion.getByteNumber("Flicker"));
		peExplosion.setByte("FireworkTrail", pcExplosion.getByteNumber("Trail"));
		peExplosion.setByte("FireworkType", pcExplosion.getByteNumber("Type"));
		return peExplosion;
	}

	private byte[] remapColors(int[] pcColors) {
		byte[] peColors = new byte[pcColors.length];
		for(int i = 0; i < pcColors.length; i++) {
			peColors[i] = remapColor(pcColors[i]);
		}
		return peColors;
	}

	//Fireworks have a custom color system. Since PE only supports the Dye colors, here's a basic switcher for now.
	private byte remapColor(int pcColor) {
		switch(pcColor) {
			case 1973019:				return 0;
			case 11743532:				return 1;
			case 3887386:				return 2;
			case 5320730:				return 3;
			case 2437522:				return 4;
			case 8073150:				return 5;
			case 2651799:				return 6;
			case 11250603:				return 7;
			case 4408131:				return 8;
			case 14188952:				return 9;
			case 4312372:				return 10;
			case 14602026:				return 11;
			case 6719955:				return 12;
			case 12801229:				return 13;
			case 15435844: 				return 14;
			default: case 15790320: 	return 15;
		}
	}

}
