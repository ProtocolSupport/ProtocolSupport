package protocolsupport.protocol.typeremapper.pe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.utils.Any;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.Utils;
import protocolsupport.zplatform.ServerPlatform;

public class PEBlocks {

	protected static byte[] peBlockDef;
	protected static final int[] pcToPeRuntimeId = new int[MinecraftData.BLOCKDATA_COUNT];

	public static int getPocketRuntimeId(int pcRuntimeId) {
		return pcToPeRuntimeId[pcRuntimeId];
	}

	public static byte[] getPocketRuntimeDefinition() {
		return peBlockDef;
	}

	static {
		JsonObject blocksData = Utils.getResourceJson(MappingsData.getFlatteningResoucePath(ProtocolVersion.MINECRAFT_1_13, "blocks.json"));
		if (blocksData != null) {
			for (Entry<String, JsonElement> entry : blocksData.entrySet()) {
				String name = entry.getKey();
				for (JsonElement blockdataElement : JsonUtils.getJsonArray(entry.getValue().getAsJsonObject(), "states")) {
					JsonObject blockdataObject = blockdataElement.getAsJsonObject();
					String blockdata = name;
					if (blockdataObject.has("properties")) {
						blockdata +=
							"[" +
							blockdataObject.getAsJsonObject("properties").entrySet().stream()
							.map(bdEntry -> bdEntry.getKey() + "=" + bdEntry.getValue().getAsString())
							.collect(Collectors.joining(",")) +
							"]";
						if (blockdataObject.getAsJsonObject("properties").has("waterlogged")) {
							
						}
					}
					
				}
			}
		}
		
		
		final Map<String, BlockAny> mappings = new HashMap<>();
		{ //Actual Remaps
			
		}

		//Load in PE blockdefinitions (used for login definition list and mapping name+data -> runtimeid)
		final Map<BlockAny, PocketBlock> peBlocks = new HashMap<>();
		{
			int runtimeId;
			for (JsonElement element : Utils.iterateJsonArrayResource(PEDataValues.getResourcePath("blockdefinition.json"))) {
				PocketBlock block = Utils.GSON.fromJson(element, PocketBlock.class).setRuntimeId(runtimeId++);
				peBlocks.put(new BlockAny(block.getName(), block.getData()), block);
			}
		}

		//Remap the PC blockstates to PE blockAnys.
		//mappings.fo
		
		
		//Load in PC->PE remap list.
		Arrays.fill(pcToPeRuntimeId, 1);
		pcToPeRuntimeId[0] = 0;
		for (JsonElement element : Utils.iterateJsonArrayResource(PEDataValues.getResourcePath("blockmapping.json"))) {
			JsonObject object = element.getAsJsonObject();
			int runtimeId = ServerPlatform.get().getMiscUtils().getBlockDataNetworkId(Bukkit.createBlockData(JsonUtils.getString(object, "blockdata")));
			String peName = JsonUtils.getString(object, "pename");
			short peData = (short) JsonUtils.getInt(object, "pedata");
			System.out.println("REMAPPED pcRuntimeId: " + runtimeId + "(" + JsonUtils.getString(object, "blockdata") + ") TO " + peName + "[DATA=" + peData + "] peRuntimeId: " + peBlocks.indexOf(new Any<String,Short>(peName, peData)) + ".");
			pcToPeRuntimeId[runtimeId] = peBlocks.indexOf(new Any<String,Short>(peName, peData));
		}
		//Compile PE block definition for sending on login.
		ByteBuf def = Unpooled.buffer();
		VarNumberSerializer.writeVarInt(def, peBlocks.size());
		peBlocks.keySet().forEach(block -> {
			StringSerializer.writeString(def, ProtocolVersion.MINECRAFT_PE, block.getObj1());
			def.writeShortLE(block.getObj2());
		});
		peBlockDef = MiscSerializer.readAllBytes(def);
	}

	private static class BlockAny extends Any<String, Short> {
		public BlockAny(String t1, Short t2) {
			super(t1, t2);
		}
	}

	private static class PocketBlock {

		private transient int runtimeId;
		private String name;
		private int id;
		private short data;

		public PocketBlock setRuntimeId(int runtimeId) {
			this.runtimeId = runtimeId;
			return this;
		}

		public int getRuntimeId() {
			return runtimeId;
		}
		public String getName() {
			return name;
		}
		public int getId() {
			return id;
		}
		public short getData() {
			return data;
		}

	}

}
