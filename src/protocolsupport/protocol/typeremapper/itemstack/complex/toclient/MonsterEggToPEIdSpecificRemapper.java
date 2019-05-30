package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import org.apache.commons.lang3.StringUtils;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;

public class MonsterEggToPEIdSpecificRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		NBTCompound tag = itemstack.getNBT();
		if (tag == null) {
			return itemstack;
		}
		NBTCompound entityTag = tag.getTagOfType("EntityTag", NBTType.COMPOUND);
		if (entityTag == null) {
			return itemstack;
		}
		NBTString idString = tag.getTagOfType("id", NBTType.STRING);
		if (idString == null) {
			return itemstack;
		}
		String id = idString.getValue();
		if (StringUtils.isEmpty(id)) {
			return itemstack;
		}
		itemstack.setLegacyData(PEDataValues.getEntityNetworkId(NetworkEntityType.getByRegistrySTypeId(id)));
		return itemstack;
	}

}