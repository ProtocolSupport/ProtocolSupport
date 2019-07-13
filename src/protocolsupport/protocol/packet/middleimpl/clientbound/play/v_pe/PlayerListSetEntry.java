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

	protected static final int PE_PLAYER_LIST_ADD = 0;
	protected static final int PE_PLAYER_LIST_REMOVE = 1;

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
				ByteBuf scratchBuffer = Unpooled.buffer();
				int numEntries = 0;
				for (Entry<UUID, Any<PlayerListEntry, PlayerListEntry>> entry : infos.entrySet()) {
					UUID uuid = entry.getKey();
					if (uuid.equals(connection.getPlayer().getUniqueId())) {
						continue;
					}
					PlayerListEntry currentEntry = entry.getValue().getObj2();
					String username = currentEntry.getCurrentName(attrscache.getLocale());
					Any<Boolean, String> skininfo = getSkinInfo(currentEntry.getProperties(true));
					byte[] skindata = skininfo != null ? skinprovider.getSkinData(skininfo.getObj2()) : null;
					if (skindata != null) {
						writePlayerListEntry(version, scratchBuffer, skindata, uuid, skininfo.getObj1(), username);
					} else {
						writePlayerListEntry(version, scratchBuffer, DefaultPESkinsProvider.DEFAULT_STEVE, uuid, false, username);
						if (skininfo != null) {
							skinprovider.scheduleGetSkinData(skininfo.getObj2(), new SkinUpdate(connection, uuid, skininfo.getObj1(), username));
						}
					}
					numEntries++;
				}
				writePlayerListHeader(serializer, PE_PLAYER_LIST_ADD, numEntries);
				serializer.writeBytes(scratchBuffer);
				scratchBuffer.release();
				return RecyclableSingletonList.create(serializer);
			}
			case REMOVE: {
				ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.PLAYER_INFO);
				writePlayerListHeader(serializer, PE_PLAYER_LIST_REMOVE, infos.size());
				for (Entry<UUID, Any<PlayerListEntry, PlayerListEntry>> entry : infos.entrySet()) {
					MiscSerializer.writePEUUID(serializer, entry.getKey());
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
		private final String username;
		public SkinUpdate(ConnectionImpl connection, UUID uuid, Boolean isNormalModel, String username) {
			this.connection = connection;
			this.uuid = uuid;
			this.isNormalModel = isNormalModel;
			this.username = username;
		}

		@Override
		public void accept(byte[] skindata) {
			ProtocolVersion version = connection.getVersion();
			ByteBuf serializer = Unpooled.buffer();
			writePlayerListHeader(serializer, PE_PLAYER_LIST_ADD, 1);
			writePlayerListEntry(version, serializer, skindata, uuid, isNormalModel, username);
			connection.sendPacket(ServerPlatform.get().getPacketFactory().createOutboundPluginMessagePacket(InternalPluginMessageRequest.PESkinUpdateSuffix, serializer));
		}
	}

	protected static void writePlayerListHeader(ByteBuf serializer, int type, int count) {
		serializer.writeByte(type);
		VarNumberSerializer.writeVarInt(serializer, count);
	}

	protected static void writePlayerListEntry(ProtocolVersion version, ByteBuf serializer, byte[] skindata, UUID uuid, Boolean isNormalModel, String username) {
		MiscSerializer.writePEUUID(serializer, uuid);
		VarNumberSerializer.writeSVarLong(serializer, 0); //entity id
		StringSerializer.writeString(serializer, version, username);
		writeSkinData(version, serializer, isNormalModel, skindata);
		StringSerializer.writeString(serializer, version, ""); //xuid
		StringSerializer.writeString(serializer, version, ""); //Chat channel thing
	}

	protected static void writeSkinData(ProtocolVersion version, ByteBuf serializer, boolean isSlim, byte[] skindata) {
		PESkinModel model = PESkinModel.getSkinModel(isSlim);
		StringSerializer.writeString(serializer, version, model.getSkinName());
		ArraySerializer.writeVarIntByteArray(serializer, skindata);
		ArraySerializer.writeVarIntByteArray(serializer, new byte[0]); //cape data
		StringSerializer.writeString(serializer, version, model.getGeometryId());
		StringSerializer.writeString(serializer, version, model.getGeometryData());
	}

}
