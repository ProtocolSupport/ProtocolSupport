package protocolsupport.protocol.typeremapper.tile;

import java.util.function.Consumer;

import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.nbt.NBTString;

class TileEntityToLegacyTypeNameRemapper implements Consumer<TileEntity> {

	protected final String name;
	public TileEntityToLegacyTypeNameRemapper(String name) {
		this.name = name;
	}
	@Override
	public void accept(TileEntity tile) {
		tile.getNBT().setTag("id", new NBTString(name));
	}

}