package protocolsupport.protocol.typeremapper.tile;

import java.util.function.Consumer;

import org.bukkit.Bukkit;

import protocolsupport.api.MaterialAPI;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTInt;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.utils.CommonNBT;

public class TileEntityPistonRemapper implements Consumer<TileEntity> {

	@Override
	public void accept(TileEntity t) {
		NBTCompound nbt = t.getNBT();
		//TODO: find a way to speed up conversion
		String blockdataString = CommonNBT.deserializeBlockDataFromNBT(nbt.getTagOfType("blockState", NBTType.COMPOUND));
		int legacyId = PreFlatteningBlockIdData.getCombinedId(MaterialAPI.getBlockDataNetworkId(Bukkit.createBlockData(blockdataString)));
		nbt.setTag("blockId", new NBTInt(PreFlatteningBlockIdData.getIdFromCombinedId(legacyId)));
		nbt.setTag("blockData", new NBTInt(PreFlatteningBlockIdData.getDataFromCombinedId(legacyId)));
	}

}
