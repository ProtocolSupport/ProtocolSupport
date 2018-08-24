package protocolsupport.zplatform.impl.pe;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Material;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.MaterialAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.netty.Allocator;

public class PEPaletteCompiler {

	private PEPaletteCompiler() { }
	private static final PEPaletteCompiler INSTANCE = new PEPaletteCompiler();

	public static PEPaletteCompiler getInstance() {
		return INSTANCE;
	}

	private AtomicInteger paletteSize;
	private byte[] globalPaletteDefinition;

	public byte[] getGlobalPaletteDefinition() {
		return globalPaletteDefinition;
	}

	public int getPaletteSize() {
		return paletteSize.get();
	}

	public void compilePaletteDefinition() {
		ByteBuf buf = Allocator.allocateBuffer();
		Arrays.stream(Material.values())
		.filter(m -> m.isBlock())
		.forEach(material -> {
			StringSerializer.writeString(buf, ProtocolVersion.MINECRAFT_PE, remapKey(material.getKey().toString()));
			buf.writeShortLE(MaterialAPI.getItemNetworkId(material));
			paletteSize.incrementAndGet();
		});
	}

	//TODO Actually remap needed keys.
	public String remapKey(String peName) {
		return peName;
	}

}