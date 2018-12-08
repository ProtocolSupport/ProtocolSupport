package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.unsafe.peskins.DefaultPESkinsProvider;
import protocolsupport.api.unsafe.peskins.PESkinsProvider;
import protocolsupport.api.unsafe.peskins.PESkinsProviderSPI;
import protocolsupport.api.utils.Any;
import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.listeners.InternalPluginMessageRequest;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerListSetEntry;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.pipeline.version.v_pe.PEPacketEncoder;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.AttributesCache;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntry;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.pe.PESkinModel;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.ServerPlatform;

public class PlayerListSetEntry extends MiddlePlayerListSetEntry {

	public PlayerListSetEntry(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		AttributesCache attrscache = cache.getAttributesCache();
		ProtocolVersion version = connection.getVersion();
		switch (action) {
			case ADD: {
				PESkinsProvider skinprovider = PESkinsProviderSPI.getProvider();
				ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.PLAYER_INFO);
				serializer.writeByte(0);
				VarNumberSerializer.writeVarInt(serializer, infos.size());
				for (Entry<UUID, Any<PlayerListEntry, PlayerListEntry>> entry : infos.entrySet()) {
					UUID uuid = entry.getKey();
					PlayerListEntry currentEntry = entry.getValue().getObj2();
					MiscSerializer.writeUUID(serializer, version, uuid);
					VarNumberSerializer.writeVarInt(serializer, 0); //entity id
					StringSerializer.writeString(serializer, version, currentEntry.getCurrentName(attrscache.getLocale()));
					Any<Boolean, String> skininfo = getSkinInfo(currentEntry.getProperties(true));
					byte[] skindata = skininfo != null ? skinprovider.getSkinData(skininfo.getObj2()) : null;
					if (skindata != null) {
						writeSkinData(version, serializer, false, skininfo.getObj1(), skindata);
					} else {
						writeSkinData(version, serializer, false, false, DefaultPESkinsProvider.DEFAULT_STEVE);
						if (skininfo != null) {
							skinprovider.scheduleGetSkinData(skininfo.getObj2(), new SkinUpdate(connection, uuid, skininfo.getObj1()));
						}
					}
					StringSerializer.writeString(serializer, version, ""); //xuid
					StringSerializer.writeString(serializer, version, ""); //Chat channel thing
				}
				return RecyclableSingletonList.create(serializer);
			}
			case REMOVE: {
				ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.PLAYER_INFO);
				serializer.writeByte(1);
				VarNumberSerializer.writeVarInt(serializer, infos.size());
				for (Entry<UUID, Any<PlayerListEntry, PlayerListEntry>> entry : infos.entrySet()) {
					MiscSerializer.writeUUID(serializer, version, entry.getKey());
				}
				return RecyclableSingletonList.create(serializer);
			}
			default: {
				return RecyclableEmptyList.get();
			}
		}
	}

	protected static Any<Boolean, String> getSkinInfo(List<ProfileProperty> properties) {
		Optional<ProfileProperty> property =
			properties.stream()
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
		private final ConnectionImpl connection;
		private final UUID uuid;
		private final Boolean isNormalModel;
		public SkinUpdate(ConnectionImpl connection, UUID uuid, Boolean isNormalModel) {
			this.connection = connection;
			this.uuid = uuid;
			this.isNormalModel = isNormalModel;
		}
		@Override
		public void accept(byte[] skindata) {
			ByteBuf serializer = Unpooled.buffer();
			MiscSerializer.writeUUID(serializer, connection.getVersion(), uuid);
			writeSkinData(connection.getVersion(), serializer, true, isNormalModel, skindata);
			connection.sendPacket(ServerPlatform.get().getPacketFactory().createOutboundPluginMessagePacket(InternalPluginMessageRequest.PESkinUpdateSuffix, serializer));
		}
	}

	protected static void writeSkinData(ProtocolVersion version, ByteBuf serializer, boolean isSkinUpdate, boolean isSlim, byte[] skindata) {
		PESkinModel model = PESkinModel.getSkinModel(isSlim);
		if (isSkinUpdate) {
			StringSerializer.writeString(serializer, version, model.getSkinId());
		}
		StringSerializer.writeString(serializer, version, model.getSkinName());
		if (isSkinUpdate) {
			StringSerializer.writeString(serializer, version, model.getSkinName());
		}
		ArraySerializer.writeVarIntByteArray(serializer, skindata);
		ArraySerializer.writeVarIntByteArray(serializer, new byte[0]); //cape data
		StringSerializer.writeString(serializer, version, model.getGeometryId());
		StringSerializer.writeString(serializer, version, model.getGeometryData());
	}

}
