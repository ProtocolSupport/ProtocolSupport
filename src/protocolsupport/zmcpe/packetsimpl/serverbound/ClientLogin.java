package protocolsupport.zmcpe.packetsimpl.serverbound;

import java.io.ByteArrayInputStream;
import java.util.Base64;

import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zmcpe.pipeline.Decompressor;

public class ClientLogin extends ServerBoundMiddlePacket {

	private String username;

	private final JsonParser parser = new JsonParser();

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		ServerBoundPacketData hsscreator = ServerBoundPacketData.create(ServerBoundPacket.HANDSHAKE_START);
		hsscreator.writeVarInt(ProtocolVersion.getLatest().getId());
		hsscreator.writeString("");
		hsscreator.writeShort(0);
		hsscreator.writeVarInt(2);
		packets.add(hsscreator);
		ServerBoundPacketData lscreator = ServerBoundPacketData.create(ServerBoundPacket.LOGIN_START);
		lscreator.writeString(username);
		packets.add(lscreator);
		return packets;
	}

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		serializer.skipBytes(Integer.BYTES); //TODO: validate protocol
		serializer.skipBytes(Byte.BYTES); //skip pe type
		ByteBuf logindata = Unpooled.wrappedBuffer(Decompressor.decompressStatic(
			ProtocolSupportPacketDataSerializer.toArray(serializer.readSlice(serializer.readVarInt()))
		));
		//decode chain
		JsonElement root = parser.parse(new InputStreamReader(new ByteBufInputStream(logindata, ByteBufUtil.swapInt(logindata.readInt()))));
		String chain = JsonUtils.getJsonArray(root.getAsJsonObject(), "chain").get(0).getAsString();
		JsonElement data = parser.parse(new InputStreamReader(new ByteArrayInputStream(Base64.getDecoder().decode(chain.split("[.]")[1]))));
		username = JsonUtils.getString(JsonUtils.getJsonObject(data.getAsJsonObject(), "extraData"), "displayName");
		//skip skin data
		logindata.skipBytes(ByteBufUtil.swapInt(logindata.readInt()));
	}

}
