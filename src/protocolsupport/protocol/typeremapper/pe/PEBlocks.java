package protocolsupport.protocol.typeremapper.pe;

import java.util.ArrayList;
import java.util.Arrays;

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
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.Utils;
import protocolsupport.zplatform.ServerPlatform;

public class PEBlocks {

	protected static byte[] peBlockDef;
	protected static final int[] pcToPeRuntimeId = new int[MinecraftData.BLOCKDATA_COUNT];

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
			pcToPeRuntimeId[runtimeId] = peBlocks.indexOf(new Any<String,Short>(peName, peData));
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

	public static byte[] getPocketRuntimeDefinition() {
		return peBlockDef;
	}

}
