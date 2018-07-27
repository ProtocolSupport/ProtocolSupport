package protocolsupport.protocol.utils.types.particle;

import io.netty.buffer.ByteBuf;

public class Particle {

	public Particle(int id, String name) {
		this.id = id;
		this.name = name;
	}

	protected String name;
	protected int id;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void readData(ByteBuf buf) {
	}

	public void writeData(ByteBuf buf) {
	}

}
