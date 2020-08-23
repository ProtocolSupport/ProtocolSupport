package protocolsupport.protocol.types;

import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTInt;
import protocolsupport.protocol.types.nbt.NBTString;

public class TileEntity {

	protected final TileEntityType type;
	protected final Position position;
	protected final NBTCompound nbt;

	public TileEntity(NBTCompound fullNbt) {
		this.type = TileEntityType.getByRegistryId(fullNbt.getStringTagValueOrThrow("id"));
		this.position = new Position(fullNbt.getNumberTagOrThrow("x").getAsInt(), fullNbt.getNumberTagOrThrow("y").getAsInt(), fullNbt.getNumberTagOrThrow("z").getAsInt());
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
