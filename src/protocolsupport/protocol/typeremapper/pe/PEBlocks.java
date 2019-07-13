package protocolsupport.protocol.typeremapper.pe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.MaterialAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupportbuildprocessor.Preload;

@Preload
public class PEBlocks {

	private static final byte[] peBlockDef;
	private static final byte[] peBlockDef112;
	private static final int[] pcToPeRuntimeId = new int[MinecraftData.BLOCKDATA_COUNT];
	private static final int[] pcWaterlogged = new int[MinecraftData.BLOCKDATA_COUNT];
	private static final EnumMap<ProtocolVersion, Integer> waterRuntime = new EnumMap<>(ProtocolVersion.class);
	private static final EnumMap<ProtocolVersion, Integer> airRuntime = new EnumMap<>(ProtocolVersion.class);

	private static final int CAN_BE_WATERLOGGED = 1;
	private static final int IS_WATERLOGGED = 2;

	static {
		final ArrayList<PEBlock> peBlocks = new ArrayList<>();
		final JsonObject peMappings = ResourceUtils.getAsJson(PEDataValues.getResourcePath("blockmapping.json"));
		//Load in PE blockdefinitions (used for login definition list and mapping name+data -> runtimeid)
		for (JsonElement element : ResourceUtils.getAsIterableJson(PEDataValues.getResourcePath("blockdefinition.json"))) {
			JsonObject object = element.getAsJsonObject();
			peBlocks.add(new PEBlock(JsonUtils.getString(object, "name"), (short) JsonUtils.getInt(object, "data")));
		}
		//Some pre-defined are always inherently waterlogged:
		List<Material> inherentlyWaterlogged = Arrays.asList(Material.BUBBLE_COLUMN, Material.KELP, Material.KELP_PLANT, Material.SEAGRASS, Material.TALL_SEAGRASS);
		//Iterate over all possible blockstates for remap.
		for (int i = 0; i < MinecraftData.BLOCKDATA_COUNT; i++) {
			BlockData data = ServerPlatform.get().getMiscUtils().getBlockDataByNetworkId(i);
			//Store waterloggedness.
			if (data instanceof Waterlogged) {
				pcWaterlogged[i] = ((Waterlogged) data).isWaterlogged() ? IS_WATERLOGGED : CAN_BE_WATERLOGGED;
			}
			if (inherentlyWaterlogged.contains(data.getMaterial())) {
				pcWaterlogged[i] = IS_WATERLOGGED;
			}
			//Remap to PE
			if (peMappings.has(data.getAsString())) {
				PEBlock peBlock = new PEBlock(JsonUtils.getJsonObject(peMappings, data.getAsString()));
				pcToPeRuntimeId[i] = peBlocks.indexOf(peBlock);
			}
		}
		//Specify water block for waterlog remapping.
		for (ProtocolVersion version : ProtocolVersionsHelper.ALL_PE) {
			waterRuntime.put(version, toPocketBlock(version, Material.WATER));
		}
		for (ProtocolVersion version : ProtocolVersionsHelper.ALL_PE) {
			airRuntime.put(version, toPocketBlock(version, Material.AIR));
		}
		//Compile PE block definition for sending on login.
		ByteBuf def = Unpooled.buffer();
		ByteBuf def112 = Unpooled.buffer();
		VarNumberSerializer.writeVarInt(def, peBlocks.size());
		VarNumberSerializer.writeVarInt(def112, peBlocks.size());
		int blockId = 0;
		for (PEBlock block : peBlocks) {
			StringSerializer.writeString(def, ProtocolVersionsHelper.LATEST_PE, block.getName());
			StringSerializer.writeString(def112, ProtocolVersionsHelper.LATEST_PE, block.getName());
			def.writeShortLE(block.getData());
			def112.writeShortLE(block.getData());
			def112.writeShortLE(blockId++);
		}
		peBlockDef = MiscSerializer.readAllBytes(def);
		peBlockDef112 = MiscSerializer.readAllBytes(def112);
	}

	public static int getPocketRuntimeId(int pcRuntimeId) {
		return pcToPeRuntimeId[pcRuntimeId];
	}

	public static byte[] getPocketRuntimeDefinition(ProtocolVersion version) {
		if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_PE_1_12)) {
			return peBlockDef112;
		}
		return peBlockDef;
	}

	public static int toPocketBlock(ProtocolVersion version, Material material) {
		return toPocketBlock(version, material.createBlockData());
	}

	public static int toPocketBlock(ProtocolVersion version, BlockData blockdata) {
		return PEBlocks.getPocketRuntimeId(LegacyBlockData.REGISTRY.getTable(version).getRemap(MaterialAPI.getBlockDataNetworkId(blockdata)));
	}

	public static boolean isPCBlockWaterlogged(int runtimeId) {
		return pcWaterlogged[runtimeId] == IS_WATERLOGGED;
	}

	public static boolean canPCBlockBeWaterLogged(int runtimeId) {
		return pcWaterlogged[runtimeId] == IS_WATERLOGGED || pcWaterlogged[runtimeId] == CAN_BE_WATERLOGGED;
	}

	public static int getPEWaterId(ProtocolVersion version) {
		return waterRuntime.get(version);
	}

	public static int getPEAirId(ProtocolVersion version) {
		return airRuntime.get(version);
	}

	private static class PEBlock {

		private final String name;
		private final short data;

		public PEBlock(String name, short data) {
			this.name = name;
			this.data = data;
		}

		public PEBlock(JsonObject jsonBlock) {
			this(JsonUtils.getString(jsonBlock, "pename"), JsonUtils.getShort(jsonBlock, "pedata"));
		}

		public String getName() {
			return name;
		}

		public short getData() {
			return data;
		}

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 47 * hash + name.hashCode();
			hash = 47 * hash + data;
			return hash;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof PEBlock) {
				PEBlock block = (PEBlock) obj;
				return block.name.equals(name) &&
					block.data == data;
			}
			return false;
		}

	}

}
