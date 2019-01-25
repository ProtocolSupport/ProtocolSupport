package protocolsupport.protocol.packet.middleimpl.serverbound.play.abs_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2;

import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

import org.bukkit.Material;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleEditBook;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleNameItem;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePickItem;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSelectTrade;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSetBeaconEffect;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateStructureBlock;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.CustomPayloadChannelsCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.UsedHand;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTList;
import protocolsupport.protocol.utils.types.nbt.NBTString;
import protocolsupport.protocol.utils.types.nbt.NBTType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class AbstractCustomPayload extends ServerBoundMiddlePacket {

	public AbstractCustomPayload(ConnectionImpl connection) {
		super(connection);
	}

	protected String tag;
	protected ByteBuf data;

	protected RecyclableCollection<ServerBoundPacketData> transformRegisterUnregister(boolean register) {
		CustomPayloadChannelsCache ccache = connection.getCache().getChannelsCache();
		String modernTag = LegacyCustomPayloadChannelName.fromPre13(tag);
		StringJoiner payloadModernTagJoiner = new StringJoiner("\u0000");
		for (String payloadLegacyTag : data.toString(StandardCharsets.UTF_8).split("\u0000")) {
			String payloadModernTag = LegacyCustomPayloadChannelName.fromPre13(payloadLegacyTag);
			if (register) {
				ccache.register(payloadModernTag, payloadLegacyTag);
			} else {
				ccache.unregister(payloadModernTag);
			}
			payloadModernTagJoiner.add(payloadModernTag);
		}
		return RecyclableSingletonList.create(MiddleCustomPayload.create(modernTag, payloadModernTagJoiner.toString().getBytes(StandardCharsets.UTF_8)));
	}

	protected RecyclableCollection<ServerBoundPacketData> transformBookEdit() {
		String locale = connection.getCache().getAttributesCache().getLocale();
		NetworkItemStack book = ItemStackSerializer.readItemStack(data, connection.getVersion(), locale);
		book.setTypeId(ItemMaterialLookup.getRuntimeId(Material.WRITABLE_BOOK));
		if (!book.isNull()) {
			return RecyclableSingletonList.create(MiddleEditBook.create(book, false, UsedHand.MAIN));
		} else {
			return RecyclableEmptyList.get();
		}
	}

	protected RecyclableCollection<ServerBoundPacketData> transformBookSign() {
		ProtocolVersion version = connection.getVersion();
		String locale = connection.getCache().getAttributesCache().getLocale();
		NetworkItemStack book = ItemStackSerializer.readItemStack(data, version, locale);
		if (!book.isNull()) {
			book.setTypeId(ItemMaterialLookup.getRuntimeId(Material.WRITABLE_BOOK));
			if (connection.getVersion() == ProtocolVersion.MINECRAFT_1_8) {
				NBTCompound rootTag = book.getNBT();
				if (rootTag != null) {
					NBTList<NBTString> pages = rootTag.getTagListOfType("pages", NBTType.STRING);
					if (pages != null) {
						NBTList<NBTString> newPages = new NBTList<>(NBTType.STRING);
						for (NBTString page : pages.getTags()) {
							newPages.addTag(new NBTString(ChatAPI.fromJSON(page.getValue()).toLegacyText(locale)));
						}
						rootTag.setTag("pages", newPages);
						book.setNBT(rootTag);
					}
				}
			}
			return RecyclableSingletonList.create(MiddleEditBook.create(book, true, UsedHand.MAIN));
		} else {
			return RecyclableEmptyList.get();
		}
	}

	protected RecyclableCollection<ServerBoundPacketData> transformStructureBlock() {
		Position position = PositionSerializer.readLegacyPositionI(data);
		MiddleUpdateStructureBlock.Action action = MiddleUpdateStructureBlock.Action.CONSTANT_LOOKUP.getByOrdinal(data.readByte() - 1);
		MiddleUpdateStructureBlock.Mode mode = MiddleUpdateStructureBlock.Mode.valueOf(StringSerializer.readString(data, connection.getVersion()));
		String name = StringSerializer.readString(data, connection.getVersion());
		byte offsetX = (byte) data.readInt();
		byte offsetY = (byte) data.readInt();
		byte offsetZ = (byte) data.readInt();
		byte sizeX = (byte) data.readInt();
		byte sizeY = (byte) data.readInt();
		byte sizeZ = (byte) data.readInt();
		MiddleUpdateStructureBlock.Mirror mirror = MiddleUpdateStructureBlock.Mirror.valueOf(StringSerializer.readString(data, connection.getVersion()));
		MiddleUpdateStructureBlock.Rotation rotation = MiddleUpdateStructureBlock.Rotation.valueOf(StringSerializer.readString(data, connection.getVersion()));
		String metadata = StringSerializer.readString(data, connection.getVersion());
		int ignoreEntities = data.readBoolean() ? 0x01 : 0;
		int showAir = data.readBoolean() ? 0x02 : 0;
		int showBoundingBox = data.readBoolean() ? 0x04 : 0;
		float integrity = data.readFloat();
		long seed = VarNumberSerializer.readVarLong(data);
		byte flags = (byte) (ignoreEntities | showAir | showBoundingBox);
		return RecyclableSingletonList.create(MiddleUpdateStructureBlock.create(
			position, action, mode, name,
			offsetX, offsetY, offsetZ, sizeX, sizeY, sizeZ,
			mirror, rotation, metadata, integrity, seed, flags
		));
	}

	protected RecyclableCollection<ServerBoundPacketData> transformSetBeaconEffect() {
		int primary = data.readInt();
		int secondary = data.readInt();
		return RecyclableSingletonList.create(MiddleSetBeaconEffect.create(primary, secondary));
	}

	protected RecyclableCollection<ServerBoundPacketData> transformNameItem() {
		String name = StringSerializer.readString(data, connection.getVersion());
		return RecyclableSingletonList.create(MiddleNameItem.create(name));
	}

	protected RecyclableCollection<ServerBoundPacketData> transformPickItem() {
		int slot = VarNumberSerializer.readVarInt(data);
		return RecyclableSingletonList.create(MiddlePickItem.create(slot));
	}

	protected RecyclableCollection<ServerBoundPacketData> transformTradeSelect() {
		int slot = data.readInt();
		return RecyclableSingletonList.create(MiddleSelectTrade.create(slot));
	}

	protected RecyclableCollection<ServerBoundPacketData> transformCustomPayload() {
		String modernName = LegacyCustomPayloadChannelName.fromPre13(tag);
		return RecyclableSingletonList.create(MiddleCustomPayload.create(modernName, data));
	}

}
