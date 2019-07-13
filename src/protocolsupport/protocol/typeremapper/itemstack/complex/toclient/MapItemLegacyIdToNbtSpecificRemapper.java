package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTByte;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTNumber;
import protocolsupport.protocol.types.nbt.NBTString;

public class MapItemLegacyIdToNbtSpecificRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		NBTCompound tag = itemstack.getNBT();

		if (tag == null)
			tag = new NBTCompound();

		NBTNumber mapId = tag.getNumberTag("map");
		if (mapId != null)
			tag.setTag("map_uuid", new NBTString(String.valueOf(mapId.getAsInt())));

		tag.setTag("map_player_display", new NBTByte((byte) 1)); //TODO: Fix players not showing up.
		itemstack.setNBT(tag);

		return itemstack;
	}

}
