package protocolsupport.protocol.typeremapper.tile;

import java.util.function.Function;

import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.TileEntity;
import protocolsupport.protocol.utils.types.TileEntityType;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTInt;

class TileEntityBedSupplier implements Function<Position, TileEntity> {

	protected final int color;
	public TileEntityBedSupplier(int color) {
		this.color = color;
	}

	@Override
	public TileEntity apply(Position position) {
		NBTCompound tag = new NBTCompound();
		tag.setTag("color", new NBTInt(color));
		return new TileEntity(TileEntityType.BED, position, tag);
	}

}