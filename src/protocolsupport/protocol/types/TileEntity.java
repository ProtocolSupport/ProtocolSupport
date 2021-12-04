package protocolsupport.protocol.types;

import protocolsupport.protocol.types.nbt.NBTCompound;

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
