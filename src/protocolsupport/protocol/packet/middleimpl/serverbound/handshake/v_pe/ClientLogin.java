package protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_pe;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import protocolsupport.ProtocolSupport;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PESkin;
import protocolsupport.protocol.typeremapper.pe.PEUserData;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;
import java.util.Map;

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
    public void readFromClientData(ByteBuf clientdata) {
        clientdata.readInt(); //protocol version
        ByteBuf logindata = Unpooled.wrappedBuffer(ArraySerializer.readByteArray(clientdata, connection.getVersion()));
        //decode chain
        @SuppressWarnings("serial")
        Map<String, List<String>> map = Utils.GSON.fromJson(
                new InputStreamReader(new ByteBufInputStream(logindata, logindata.readIntLE())),
                new TypeToken<Map<String, List<String>>>() {
                }.getType()
        );
        for (String c : map.get("chain")) {
            JsonObject chainMap = decodeToken(c);
            if ((chainMap != null) && chainMap.has("extraData")) {
                JsonObject extra = chainMap.get("extraData").getAsJsonObject();
                if (extra.has("displayName")) {
                    username = extra.get("displayName").getAsString();
                }
                if (extra.has("locale")) {
                    cache.setLocale(extra.get("locale").getAsString());
                }
            }
        }

        //Load verified JWT data
        try {
            //TODO: Allow check if it's signed by Mojang (XBox live)
            String payload = JWT.decode(logindata.readBytes(logindata.readIntLE()).toString(Charset.forName("UTF-8"))).getPayload();
            PEUserData clientData = new Gson().fromJson(new String(Base64.getDecoder().decode(payload), "UTF-8"), PEUserData.class);

            PESkin skin = new PESkin(clientData.getSkinId(), clientData.getSkinData());
            if (!skin.isValid()) throw new UnsupportedOperationException("Invalid Skin");

            ProtocolSupport.getInstance().getLogger().info(String.format("Bedrock player %s logged in with %s on a %s", username, clientData.getGameVersion(), clientData.getDeviceModel()));
        } catch (JWTDecodeException | UnsupportedEncodingException e) {
            ProtocolSupport.getInstance().getLogger().warning("Error while parsing JWT Client data");
            e.printStackTrace();
        }
    }

    private JsonObject decodeToken(String token) {
        String[] base = token.split("\\.");
        if (base.length < 2) {
            return null;
        }
        return Utils.GSON.fromJson(new InputStreamReader(new ByteArrayInputStream(Base64.getDecoder().decode(base[1]))), JsonObject.class);
    }

}
