package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.api.unsafe.peskins.DefaultPESkinsProvider;
import protocolsupport.api.unsafe.peskins.PESkinsProvider;
import protocolsupport.api.unsafe.peskins.PESkinsProviderSPI;
import protocolsupport.api.utils.Any;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerInfo;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.pe.PESkinModel;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class PlayerInfo extends MiddlePlayerInfo {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		switch (action) {
			case ADD: {
				ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.PLAYER_INFO, version);
				serializer.writeByte(0);
				PESkinsProvider skinprovider = PESkinsProviderSPI.getProvider();
				VarNumberSerializer.writeVarInt(serializer, infos.length);
				for (Info info : infos) {
					MiscSerializer.writeUUID(serializer, connection.getVersion(), info.uuid.equals(connection.getPlayer().getUniqueId()) ? cache.getPEClientUUID() : info.uuid);
					VarNumberSerializer.writeVarInt(serializer, 0); //entity id
					StringSerializer.writeString(serializer, version, info.getName(cache.getLocale()));
					Any<Boolean, String> skininfo = getSkinInfo(info); //TODO Erm where does this come from?
					byte[] skindata = skininfo != null ? skinprovider.getSkinData(skininfo.getObj2()) : null;
					if (skindata != null) {
						writeSkinData(version, serializer, false, skininfo.getObj1(), skindata);
					} else {
						writeSkinData(version, serializer, false, false, DefaultPESkinsProvider.DEFAULT_STEVE);
						if (skininfo != null) {
							skinprovider.scheduleGetSkinData(skininfo.getObj2(), new SkinUpdate(connection, info.uuid, cache.getPEClientUUID(), skininfo.getObj1()));
						}
					}
					StringSerializer.writeString(serializer, version, ""); //xuid
				}
				return RecyclableSingletonList.create(serializer);
			}
			case REMOVE: {
				ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.PLAYER_INFO, version);
				serializer.writeByte(1);
				VarNumberSerializer.writeVarInt(serializer, infos.length);
				for (Info info : infos) {
					MiscSerializer.writeUUID(serializer, connection.getVersion(), info.uuid);
				}
				return RecyclableSingletonList.create(serializer);
			}
			default: {
				return RecyclableEmptyList.get();
			}
		}
	}

	protected static Any<Boolean, String> getSkinInfo(Info info) {
		Optional<ProfileProperty> property = Arrays.stream(info.properties)
		.filter(p -> p.getName().equals("textures"))
		.findAny();
		if (property.isPresent()) {
			JsonElement propertyjson = new JsonParser().parse(new InputStreamReader(new ByteArrayInputStream(Base64.getDecoder().decode(property.get().getValue())), StandardCharsets.UTF_8));
			JsonObject texturesobject = JsonUtils.getJsonObject(JsonUtils.getAsJsonObject(propertyjson, "root element"), "textures");
			if (!texturesobject.has("SKIN")) {
				return null;
			}
			JsonObject skinobject = JsonUtils.getJsonObject(texturesobject, "SKIN");
			JsonObject skinMetadata; //Contains data about the skinModel. Currently only supports Slim and not Slim or Steve and Alex.
			boolean isSlim = skinobject.has("metadata") && (skinMetadata = skinobject.get("metadata").getAsJsonObject()).has("model") && JsonUtils.getString(skinMetadata, "model").equals("slim");
			return new Any<>(isSlim, JsonUtils.getString(skinobject, "url"));
		} else {
			return null;
		}
	}

	public static class SkinUpdate implements Consumer<byte[]> {
		private final Connection connection;
		private final UUID uuid;
		private final UUID clientUUID;
		private final Boolean isNormalModel;
		public SkinUpdate(Connection connection, UUID uuid, UUID clientUUID, Boolean isNormalModel) {
			this.connection = connection;
			this.uuid = uuid;
			this.clientUUID = clientUUID;
			this.isNormalModel = isNormalModel;
		}
		@Override
		public void accept(byte[] skindata) {
			ByteBuf serializer = Unpooled.buffer();
			VarNumberSerializer.writeVarInt(serializer, PEPacketIDs.PLAYER_SKIN);
			serializer.writeByte(0);
			serializer.writeByte(0);
			MiscSerializer.writeUUID(serializer, connection.getVersion(), uuid.equals(connection.getPlayer().getUniqueId()) ? clientUUID : uuid);
			writeSkinData(connection.getVersion(), serializer, true, isNormalModel, skindata);
			byte[] rawpacket = MiscSerializer.readAllBytes(serializer);
			connection.sendRawPacket(rawpacket);
		}
	}

	protected static void writeSkinData(ProtocolVersion version, ByteBuf serializer, boolean isSkinUpdate, boolean isSlim, byte[] skindata) {
		PESkinModel model = PESkinModel.getSkinModel(isSlim);
		if (isSkinUpdate) {
			StringSerializer.writeString(serializer, version, model.getSkinId());
		}
		StringSerializer.writeString(serializer, version, model.getSkinName());
		if (isSkinUpdate) {
			//TODO: find out how it is used and if its use matters.
			StringSerializer.writeString(serializer, version, "Steve");
		}
		ArraySerializer.writeByteArray(serializer, version, skindata);
		ArraySerializer.writeByteArray(serializer, version, new byte[0]); //cape data
		StringSerializer.writeString(serializer, version, model.getGeometryId());
		StringSerializer.writeString(serializer, version, model.getGeometryData());
	}

}
