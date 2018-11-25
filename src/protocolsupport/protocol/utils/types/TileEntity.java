package protocolsupport.protocol.utils.types;

import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTInt;
import protocolsupport.protocol.utils.types.nbt.NBTString;
import protocolsupport.protocol.utils.types.nbt.NBTType;

public class TileEntity {

	protected final TileEntityType type;
	protected final Position position;
	protected final NBTCompound nbt;

	public TileEntity(NBTCompound fullNbt) {
		this.type = TileEntityType.getByRegistryId(fullNbt.getTagOfType("id", NBTType.STRING).getValue());
		this.position = new Position(fullNbt.getNumberTag("x").getAsInt(), fullNbt.getNumberTag("y").getAsInt(), fullNbt.getNumberTag("z").getAsInt());
		this.nbt = fullNbt;
	}

	public TileEntity(TileEntityType type, Position position, NBTCompound nbt) {
		this.type = type;
		this.position = position;
		this.nbt = nbt;
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
