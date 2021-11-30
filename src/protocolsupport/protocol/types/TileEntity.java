package protocolsupport.protocol.types;

import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTInt;
import protocolsupport.protocol.types.nbt.NBTString;

public class TileEntity {

	protected final TileEntityType type;
	protected final Position position;
	protected final NBTCompound nbt;

	public TileEntity(TileEntityType type, Position position, NBTCompound nbt) {
		if (nbt == null) {
			nbt = new NBTCompound();
		}
		this.type = type;
		this.position = position;
		this.nbt = nbt;
		//TODO: move to tile entity legacy format
		this.nbt.setTag("id", new NBTString(type.getRegistryId()));
		this.nbt.setTag("x", new NBTInt(position.getX()));
		this.nbt.setTag("y", new NBTInt(position.getY()));
		this.nbt.setTag("z", new NBTInt(position.getZ()));
	}

	public TileEntityType getType() {
		return type;
	}

	public Position getPosition() {
		return position;
	}

	public NBTCompound getNBT() {
		return nbt;
	}

}
