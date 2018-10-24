package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTString;
import protocolsupport.protocol.utils.types.nbt.NBTType;

public class MonsterEggFromPEIdRemapper implements ItemStackComplexRemapper {

	@SuppressWarnings("deprecation")
	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		if (itemstack.getLegacyData() > 0) {
			NetworkEntityType type = PEDataValues.getLivingTypeFromPeNetworkId(itemstack.getLegacyData());
			if (type != null) {
				NBTCompound tag = itemstack.getNBT();
				if (tag == null) {
					tag = new NBTCompound();
				}
				NBTCompound entityTag = tag.getTagOfType("EntityTag", NBTType.COMPOUND);
				if (entityTag == null) {
					entityTag = new NBTCompound();
				}
				entityTag.setTag("id", new NBTString("minecraft:" + type.getBukkitType().getName()));
				tag.setTag("EntityTag", entityTag);
				itemstack.setNBT(tag);
			}
		}
		return itemstack;
	}

}