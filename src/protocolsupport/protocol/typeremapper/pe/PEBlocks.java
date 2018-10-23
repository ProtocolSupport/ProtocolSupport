package protocolsupport.protocol.typeremapper.pe;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.MaterialAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.utils.Any;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.Utils;
import protocolsupport.zplatform.ServerPlatform;

public class PEBlocks {

	protected static byte[] peBlockDef;
	protected static final int[] pcToPeRuntimeId = new int[MinecraftData.BLOCKDATA_COUNT];
	protected static final int[] peToPcRuntimeId = new int[MinecraftData.BLOCKDATA_COUNT];
	protected static final int[] pcWaterlogged = new int[MinecraftData.BLOCKDATA_COUNT];
	protected static int WATER_BLOCK;

	private static final int CAN_BE_WATERLOGGED = 1;
	private static final int IS_WATERLOGGED = 2;

	static {
		final ArrayList<Any<String, Short>> peBlocks = new ArrayList<>();
		//Load in PE blockdefinitions (used for login definition list and mapping name+data -> runtimeid)
		for (JsonElement element : Utils.iterateJsonArrayResource(PEDataValues.getResourcePath("blockdefinition.json"))) {
			JsonObject object = element.getAsJsonObject();
			peBlocks.add(new Any<String, Short>(JsonUtils.getString(object, "name"), (short) JsonUtils.getInt(object, "data")));
		}
		//Load in PC->PE remap list.
		Arrays.fill(pcToPeRuntimeId, 1);
		pcToPeRuntimeId[0] = 0;
		for (JsonElement element : Utils.iterateJsonArrayResource(PEDataValues.getResourcePath("blockmapping.json"))) {
			JsonObject object = element.getAsJsonObject();
			int runtimeId = ServerPlatform.get().getMiscUtils().getBlockDataNetworkId(Bukkit.createBlockData(JsonUtils.getString(object, "blockdata")));
			String peName = JsonUtils.getString(object, "pename");
			short peData = (short) JsonUtils.getInt(object, "pedata");
			System.out.println("REMAPPED pcRuntimeId: " + runtimeId + "(" + JsonUtils.getString(object, "blockdata") + ") TO " + peName + "[DATA=" + peData + "] peRuntimeId: " + peBlocks.indexOf(new Any<String,Short>(peName, peData)) + ".");
			int peRuntimeId = peBlocks.indexOf(new Any<String,Short>(peName, peData));
			pcToPeRuntimeId[runtimeId] = peRuntimeId;
			peToPcRuntimeId[peRuntimeId+255] = runtimeId;
			//TODO: Stop this absurd test and actually remap in this script, also storing the waterlogged runtimeids.
			if (JsonUtils.getString(object, "blockdata").contains("waterlogged=false")) {
				pcWaterlogged[runtimeId] = IS_WATERLOGGED;
			}
			//TODO do this while compiling the list also!
			WATER_BLOCK = 54;
		}
		//Compile PE block definition for sending on login.
		ByteBuf def = Unpooled.buffer();
		VarNumberSerializer.writeVarInt(def, peBlocks.size());
		peBlocks.forEach(block -> {
			StringSerializer.writeString(def, ProtocolVersion.MINECRAFT_PE, block.getObj1());
			def.writeShortLE(block.getObj2());
		});
		peBlockDef = MiscSerializer.readAllBytes(def);
	}

	public static int getPocketRuntimeId(int pcRuntimeId) {
		return pcToPeRuntimeId[pcRuntimeId];
	}

	public static int getPcRuntimeId(int peRuntimeId) {
		return peToPcRuntimeId[peRuntimeId+255];
	}

	public static byte[] getPocketRuntimeDefinition() {
		return peBlockDef;
	}

	public static int toPocketBlock(ProtocolVersion version, Material material) {
		return toPocketBlock(version, material.createBlockData());
	}

	public static int toPocketBlock(ProtocolVersion version, BlockData blockdata) {
		return PEBlocks.getPocketRuntimeId(LegacyBlockData.REGISTRY.getTable(version).getRemap(MaterialAPI.getBlockDataNetworkId(blockdata)));
	}

	public BlockData fromPocketBlock(ProtocolVersion version, int pocketblock) {
		return MaterialAPI.getBlockDataByNetworkId(PEBlocks.getPcRuntimeId(pocketblock));
	}

	public Material materialFromPocketBlock(ProtocolVersion version, int pocketblock) {
		return fromPocketBlock(version, pocketblock).getMaterial();
	}

	public static boolean isPCBlockWaterlogged(int runtimeId) {
		return pcWaterlogged[runtimeId] == IS_WATERLOGGED;
	}

	public static boolean canPCBlockBeWaterLogged(int runtimeId) {
		return pcWaterlogged[runtimeId] == IS_WATERLOGGED || pcWaterlogged[runtimeId] == CAN_BE_WATERLOGGED;
	}

	public static int getPEWaterId() {
		return WATER_BLOCK;
	}

}
