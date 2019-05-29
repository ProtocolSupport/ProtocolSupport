package protocolsupport.protocol.typeremapper.legacy;

import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

import org.bukkit.Material;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleEditBook;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleNameItem;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePickItem;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSelectTrade;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSetBeaconEffect;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateCommandBlock;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateCommandMinecart;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateStructureBlock;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.CustomPayloadChannelsCache;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.UsedHand;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class LegacyCustomPayloadData {

	public static RecyclableCollection<ServerBoundPacketData> transformRegisterUnregister(CustomPayloadChannelsCache ccache, String tag, ByteBuf data, boolean register) {
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

	public static RecyclableCollection<ServerBoundPacketData> transformBookEdit(ProtocolVersion version, String locale, ByteBuf data) {
		NetworkItemStack book = ItemStackSerializer.readItemStack(data, version, locale);
		book.setTypeId(ItemMaterialLookup.getRuntimeId(Material.WRITABLE_BOOK));
		if (!book.isNull()) {
			return RecyclableSingletonList.create(MiddleEditBook.create(book, false, UsedHand.MAIN));
		} else {
			return RecyclableEmptyList.get();
		}
	}

	public static RecyclableCollection<ServerBoundPacketData> transformBookSign(ProtocolVersion version, String locale, ByteBuf data) {
		NetworkItemStack book = ItemStackSerializer.readItemStack(data, version, locale);
		if (!book.isNull()) {
			book.setTypeId(ItemMaterialLookup.getRuntimeId(Material.WRITABLE_BOOK));
			if (version == ProtocolVersion.MINECRAFT_1_8) {
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

	public static RecyclableCollection<ServerBoundPacketData> transformStructureBlock(ProtocolVersion version, ByteBuf data) {
		Position position = PositionSerializer.readLegacyPositionI(data);
		MiddleUpdateStructureBlock.Action action = MiddleUpdateStructureBlock.Action.CONSTANT_LOOKUP.getByOrdinal(data.readByte() - 1);
		MiddleUpdateStructureBlock.Mode mode = MiddleUpdateStructureBlock.Mode.valueOf(StringSerializer.readString(data, version));
		String name = StringSerializer.readString(data, version);
		byte offsetX = (byte) data.readInt();
		byte offsetY = (byte) data.readInt();
		byte offsetZ = (byte) data.readInt();
		byte sizeX = (byte) data.readInt();
		byte sizeY = (byte) data.readInt();
		byte sizeZ = (byte) data.readInt();
		MiddleUpdateStructureBlock.Mirror mirror = MiddleUpdateStructureBlock.Mirror.valueOf(StringSerializer.readString(data, version));
		MiddleUpdateStructureBlock.Rotation rotation = MiddleUpdateStructureBlock.Rotation.valueOf(StringSerializer.readString(data, version));
		String metadata = StringSerializer.readString(data, version);
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

	public static RecyclableCollection<ServerBoundPacketData> transformSetBeaconEffect(ByteBuf data) {
		int primary = data.readInt();
		int secondary = data.readInt();
		return RecyclableSingletonList.create(MiddleSetBeaconEffect.create(primary, secondary));
	}

	public static RecyclableCollection<ServerBoundPacketData> transformNameItemSString(ProtocolVersion version, ByteBuf data) {
		String name = StringSerializer.readString(data, version);
		return RecyclableSingletonList.create(MiddleNameItem.create(name));
	}

	public static RecyclableCollection<ServerBoundPacketData> transformNameItemDString(ByteBuf data) {
		String name = data.toString(StandardCharsets.UTF_8);
		return RecyclableSingletonList.create(MiddleNameItem.create(name));
	}

	public static RecyclableCollection<ServerBoundPacketData> transformPickItem(ByteBuf data) {
		int slot = VarNumberSerializer.readVarInt(data);
		return RecyclableSingletonList.create(MiddlePickItem.create(slot));
	}

	public static RecyclableCollection<ServerBoundPacketData> transformTradeSelect(ByteBuf data) {
		int slot = data.readInt();
		return RecyclableSingletonList.create(MiddleSelectTrade.create(slot));
	}

	public static RecyclableCollection<ServerBoundPacketData> transformCustomPayload(String tag, ByteBuf data) {
		String modernName = LegacyCustomPayloadChannelName.fromPre13(tag);
		return RecyclableSingletonList.create(MiddleCustomPayload.create(modernName, data));
	}

	public static RecyclableCollection<ServerBoundPacketData> transformBasicCommandBlockEdit(ProtocolVersion version, ByteBuf data) {
		Position position = PositionSerializer.readLegacyPositionI(data);
		String command = StringSerializer.readString(data, version);
		return RecyclableSingletonList.create(MiddleUpdateCommandBlock.create(
			position, command, MiddleUpdateCommandBlock.Mode.REDSTONE, 0
		));
	}

	public static RecyclableCollection<ServerBoundPacketData> transformAdvancedCommandBlockEdit(ProtocolVersion version, ByteBuf data, boolean useTrackOutput) {
		int type = data.readByte();
		switch (type) {
			case 0: {
				Position position = PositionSerializer.readLegacyPositionI(data);
				String command = StringSerializer.readString(data, version);
				boolean trackOutput = useTrackOutput ? data.readBoolean() : false;
				return RecyclableSingletonList.create(MiddleUpdateCommandBlock.create(
					position, command, MiddleUpdateCommandBlock.Mode.REDSTONE,
					(trackOutput ? MiddleUpdateCommandBlock.FLAG_TRACK_OUTPUT : 0)
				));
			}
			case 1: {
				int entityId = data.readInt();
				String command = StringSerializer.readString(data, version);
				boolean trackOutput = useTrackOutput ? data.readBoolean() : false;
				return RecyclableSingletonList.create(MiddleUpdateCommandMinecart.create(
					entityId, command, trackOutput
				));
			}
			default: {
				return RecyclableEmptyList.get();
			}
		}
	}

	public static RecyclableCollection<ServerBoundPacketData> transformAutoCommandBlockEdit(ProtocolVersion version, ByteBuf data) {
		Position position = PositionSerializer.readLegacyPositionI(data);
		String command = StringSerializer.readString(data, version);
		boolean trackOutput = data.readBoolean();
		MiddleUpdateCommandBlock.Mode mode = null;
		switch (StringSerializer.readString(data, version)) {
			case "SEQUENCE": {
				mode = MiddleUpdateCommandBlock.Mode.SEQUENCE;
				break;
			}
			case "AUTO": {
				mode = MiddleUpdateCommandBlock.Mode.AUTO;
				break;
			}
			case "REDSTONE": {
				mode = MiddleUpdateCommandBlock.Mode.REDSTONE;
				break;
			}
		}
		boolean conditional = data.readBoolean();
		boolean auto = data.readBoolean();
		return RecyclableSingletonList.create(MiddleUpdateCommandBlock.create(
			position, command, mode,
			(trackOutput ? MiddleUpdateCommandBlock.FLAG_TRACK_OUTPUT : 0) |
			(conditional ? MiddleUpdateCommandBlock.FLAG_CONDITIONAL : 0) |
			(auto ? MiddleUpdateCommandBlock.FLAG_AUTO : 0)
		));
	}

}
