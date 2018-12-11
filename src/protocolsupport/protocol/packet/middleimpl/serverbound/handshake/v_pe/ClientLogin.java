package protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_pe;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonObject;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.factories.DefaultJWSVerifierFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.utils.Any;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class ClientLogin extends ServerBoundMiddlePacket {

	public ClientLogin(ConnectionImpl connection) {
		super(connection);
	}

	public static final String XUID_METADATA_KEY = "___PS_PE_XUID";

	protected String username;
	protected String host;
	protected int port;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		ServerBoundPacketData hsscreator = ServerBoundPacketData.create(ServerBoundPacket.HANDSHAKE_START);
		VarNumberSerializer.writeVarInt(hsscreator, ProtocolVersion.getLatest(ProtocolType.PC).getId());
		StringSerializer.writeString(hsscreator, ProtocolVersion.getLatest(ProtocolType.PC), host);
		hsscreator.writeShort(port);
		VarNumberSerializer.writeVarInt(hsscreator, 2);
		packets.add(hsscreator);
		ServerBoundPacketData lscreator = ServerBoundPacketData.create(ServerBoundPacket.LOGIN_START);
		StringSerializer.writeString(lscreator, ProtocolVersion.getLatest(ProtocolType.PC), username);
		packets.add(lscreator);
		return packets;
	}

	@SuppressWarnings("serial")
	@Override
	public void readFromClientData(ByteBuf clientdata) {
		clientdata.readInt();
		ByteBuf logindata = ArraySerializer.readVarIntByteArraySlice(clientdata);
		try {
			Any<Key, JsonObject> chaindata = extractChainData(Utils.GSON.fromJson(
				new InputStreamReader(new ByteBufInputStream(logindata, logindata.readIntLE()), StandardCharsets.UTF_8),
				new TypeToken<Map<String, List<String>>>() {}.getType()
			));
			username = JsonUtils.getString(chaindata.getObj2(), "displayName");
			cache.getAttributesCache().setPEClientUUID(UUID.fromString(JsonUtils.getString(chaindata.getObj2(), "identity")));
			if (chaindata.getObj1() != null) {
				connection.addMetadata(XUID_METADATA_KEY, JsonUtils.getString(chaindata.getObj2(), "XUID"));
			}
			JWSObject additionaldata = JWSObject.parse(new String(MiscSerializer.readBytes(logindata, logindata.readIntLE()), StandardCharsets.UTF_8));
			Map<String, String> clientinfo = Utils.GSON.fromJson(additionaldata.getPayload().toString(), new TypeToken<Map<String, String>>() {}.getType());
			String rserveraddress = clientinfo.get("ServerAddress");
			if (rserveraddress == null) {
				throw new DecoderException("ServerAddress is missing");
			}
			int lastIndexOf = rserveraddress.lastIndexOf(":");
			host = rserveraddress.substring(0, lastIndexOf);
			port = Integer.parseInt(rserveraddress.substring(lastIndexOf + 1, rserveraddress.length()));
			cache.getAttributesCache().setLocale(clientinfo.get("LanguageCode"));
		} catch (ParseException e) {
			throw new DecoderException("Unable to parse jwt", e);
		}
	}

	private static final String MOJANG_KEY = "MHYwEAYHKoZIzj0CAQYFK4EEACIDYgAE8ELkixyLcwlZryUQcu1TvPOmI2B7vX83ndnWRUaXm74wFfa5f/lwQNTfrLVHa2PmenpGI6JhIMUJaWZrjmMj90NoKNFSNBuKdm8rYiXsfaz3K36x/1U26HpG0ZxK/V1V";

	private static Any<Key, JsonObject> extractChainData(Map<String, List<String>> maindata) throws ParseException {
		List<String> chain = maindata.get("chain");
		try {
			PublicKey key = parseKey(MOJANG_KEY);
			boolean foundMojangKey = false;
			boolean signatureValid = false;
			for (String element : chain) {
				JWSObject jwsobject = JWSObject.parse(element);
				if (!foundMojangKey && jwsobject.getHeader().getX509CertURL().toString().equals(MOJANG_KEY)) {
					foundMojangKey = true;
					signatureValid = true;
				}
				if (foundMojangKey && !verify(jwsobject, key)) {
					signatureValid = false;
				}
				JsonObject jsonobject = Utils.GSON.fromJson(jwsobject.getPayload().toString(), JsonObject.class);
				key = parseKey(JsonUtils.getString(jsonobject, "identityPublicKey"));
				if (jsonobject.has("extraData")) {
					return new Any<Key, JsonObject>(signatureValid ? key : null, JsonUtils.getJsonObject(jsonobject, "extraData"));
				}
			}
		} catch (InvalidKeySpecException | JOSEException e) {
			throw new DecoderException("Unable to decode login chain", e);
		}
		throw new DecoderException("Unable to find extraData");
	}

	private static final DefaultJWSVerifierFactory jwsverifierfactory = new DefaultJWSVerifierFactory();

	private static boolean verify(JWSObject object, PublicKey key) throws JOSEException {
		return object.verify(jwsverifierfactory.createJWSVerifier(object.getHeader(), key));
	}

	private static final KeyFactory keyfactory = getKeyFactory();

	private static KeyFactory getKeyFactory() {
		try {
			return KeyFactory.getInstance("EC");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Unable to init key factory", e);
		}
	}

	private static PublicKey parseKey(String key) throws InvalidKeySpecException {
		return keyfactory.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(key)));
	}

}
