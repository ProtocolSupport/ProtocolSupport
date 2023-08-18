package protocolsupport.protocol.types;

import java.util.Objects;

import protocolsupport.utils.reflection.ReflectionUtils;

public class WorldPosition extends Position {

	protected String world;

	public WorldPosition(String world, int x, int y, int z) {
		super(x, y, z);
	}

	public String getWorld() {
		return world;
	}

	public void setWorld(String world) {
		this.world = world;
	}

	@Override
	public String toString() {
		return ReflectionUtils.toStringAllFields(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + Objects.hash(world);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		WorldPosition other = (WorldPosition) obj;
		return Objects.equals(world, other.world);
	}



}
