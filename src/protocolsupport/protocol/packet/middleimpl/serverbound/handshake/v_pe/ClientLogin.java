package protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_pe;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonObject;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class ClientLogin extends ServerBoundMiddlePacket {

	private String username;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		ServerBoundPacketData hsscreator = ServerBoundPacketData.create(ServerBoundPacket.HANDSHAKE_START);
		VarNumberSerializer.writeVarInt(hsscreator, ProtocolVersion.getLatest(ProtocolType.PC).getId());
		StringSerializer.writeString(hsscreator, ProtocolVersion.getLatest(ProtocolType.PC), "");
		hsscreator.writeShort(0);
		VarNumberSerializer.writeVarInt(hsscreator, 2);
		packets.add(hsscreator);
		ServerBoundPacketData lscreator = ServerBoundPacketData.create(ServerBoundPacket.LOGIN_START);
		StringSerializer.writeString(lscreator, ProtocolVersion.getLatest(ProtocolType.PC), username);
		packets.add(lscreator);
		return packets;
	}

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		/*clientdata.skipBytes(Integer.BYTES); // TODO: validate protocol
		clientdata.skipBytes(Byte.BYTES); // skip pe type
		ByteBuf logindata = Unpooled.wrappedBuffer(ArraySerializer.readByteArray(clientdata, version));
		//decode chain
		@SuppressWarnings("serial")
		Map<String, List<String>> map = Utils.GSON.fromJson(
			new InputStreamReader(new ByteBufInputStream(logindata, logindata.readIntLE())),
			new TypeToken<Map<String, List<String>>>() {}.getType()
		);
		for (String c : map.get("chain")) {
			JsonObject chainMap = decodeToken(c);
			if ((chainMap != null) && chainMap.has("extraData")) {
				JsonObject extra = chainMap.get("extraData").getAsJsonObject();
				if (extra.has("displayName")) {
					username = extra.get("displayName").getAsString();
				}
			}
		}
		//skip skin data  ---- Skip the rest xD
		logindata.skipBytes(logindata.readIntLE());
		//logindata.skipBytes(logindata.readableBytes());*/
		
		username = "MEEP!";
		clientdata.readBytes(clientdata.readableBytes());
	}

	private JsonObject decodeToken(String token) {
		String[] base = token.split("\\.");
		if (base.length < 2) {
			return null;
		}
		return Utils.GSON.fromJson(new InputStreamReader(new ByteArrayInputStream(Base64.getDecoder().decode(base[1]))), JsonObject.class);
	}

}
