package protocolsupport.protocol.utils.types.particle;

import java.util.function.Supplier;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public abstract class Particle {

	private static Int2ObjectMap<Supplier<Particle>> particles = new Int2ObjectArrayMap<Supplier<Particle>>();
	static {
		particles.isEmpty();
	}

	public static Particle fromId(int id) {
		return null;
	}

	public abstract String getName();

	public void readData(ByteBuf buf) { };

}
