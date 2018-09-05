package protocolsupport.zplatform.impl.pe;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Material;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.MaterialAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class PEPaletteCompiler {

	private PEPaletteCompiler() { }
	private static final PEPaletteCompiler INSTANCE = new PEPaletteCompiler();

	public static PEPaletteCompiler getInstance() {
		return INSTANCE;
	}

	private final AtomicInteger paletteSize = new AtomicInteger();
	private byte[] globalPaletteDefinition;

	public byte[] getGlobalPaletteDefinition() {
		return globalPaletteDefinition;
	}

	public int getPaletteSize() {
		return paletteSize.get();
	}

	public void compilePaletteDefinition() {
		ByteBuf buf = Unpooled.buffer();
		Arrays.stream(Material.values())
		.filter(m -> m.isBlock())
		.forEach(material -> {
			StringSerializer.writeString(buf, ProtocolVersion.MINECRAFT_PE, remapKey(material.getKey().toString()));
			buf.writeShortLE(MaterialAPI.getItemNetworkId(material));
			paletteSize.incrementAndGet();
		});
		globalPaletteDefinition = MiscSerializer.readAllBytes(buf);
	}

	//TODO Actually remap needed keys.
	public String remapKey(String peName) {
		return peName;
	}

}