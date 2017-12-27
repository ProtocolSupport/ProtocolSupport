package protocolsupport.protocol.typeremapper.itemstack.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackSpecificRemapper;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

public class FireworkFromPETagRemapper implements ItemStackSpecificRemapper {

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

	private NBTTagListWrapper remapExplosions(NBTTagListWrapper peExplosions) {
		NBTTagListWrapper pcExplosions = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
		for (int i = 0; i < peExplosions.size(); i++) {
			pcExplosions.addCompound(remapExplosion(peExplosions.getCompound(i)));
		}
		return pcExplosions;
	}

	private NBTTagCompoundWrapper remapExplosion(NBTTagCompoundWrapper peExplosion) {
		NBTTagCompoundWrapper pcExplosion = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
		if (peExplosion.hasKeyOfType("FireworkColor", NBTTagType.BYTE_ARRAY)) 	{ pcExplosion.setIntArray("Colors", remapColors(peExplosion.getByteArray("FireworkColor"))); }
		if (peExplosion.hasKeyOfType("FireworkFade", NBTTagType.BYTE_ARRAY)) 	{ pcExplosion.setIntArray("FadeColors", remapColors(peExplosion.getByteArray("FireworkFade"))); }
		if (peExplosion.hasKeyOfType("FireworkFlicker", NBTTagType.BYTE)) 		{ pcExplosion.setByte("Flicker", peExplosion.getByteNumber("FireworkFlicker")); }
		if (peExplosion.hasKeyOfType("FireworkTrail", NBTTagType.BYTE)) 		{ pcExplosion.setByte("Trail", peExplosion.getByteNumber("FireworkTrail")); }
		if (peExplosion.hasKeyOfType("FireworkType", NBTTagType.BYTE)) 			{ pcExplosion.setByte("Type", peExplosion.getByteNumber("FireworkType")); }
		return pcExplosion;
	}

	private int[] remapColors(byte[] peColors) {
		int[] pcColors = new int[peColors.length];
		for(int i = 0; i < peColors.length; i++) {
			pcColors[i] = remapColor(peColors[i]);
		}
		return pcColors;
	}

	//Fireworks have a custom color system. Since PE only supports the Dye colors, here's a basic switcher for now.
	private int remapColor(byte peColor) {
		switch(peColor) {
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
			default: case 15: 	return 15790320;
		}
	}
}
