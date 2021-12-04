package protocolsupport.protocol.typeremapper.tile;

import java.util.function.Consumer;

import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTInt;
import protocolsupport.protocol.types.nbt.NBTString;

public class TileEntityAppendTypeAndPositionRemapper implements Consumer<TileEntity> {

	public static final TileEntityAppendTypeAndPositionRemapper INSTANCE = new TileEntityAppendTypeAndPositionRemapper();

	protected TileEntityAppendTypeAndPositionRemapper() {
	}

	@Override
	public void accept(TileEntity tileentity) {
		NBTCompound tag = tileentity.getNBT();
		Position position = tileentity.getPosition();
		tag.setTag("id", new NBTString(tileentity.getType().getRegistryId()));
		tag.setTag("x", new NBTInt(position.getX()));
		tag.setTag("y", new NBTInt(position.getY()));
		tag.setTag("z", new NBTInt(position.getZ()));
	}

}
