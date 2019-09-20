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
import protocolsupport.utils.BitUtils;
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
		if (!book.isNull()) {
			book.setTypeId(ItemMaterialLookup.getRuntimeId(Material.WRITABLE_BOOK));
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
					}
				}
			}
			return RecyclableSingletonList.create(MiddleEditBook.create(book, true, UsedHand.MAIN));
		} else {
			return RecyclableEmptyList.get();
		}
	}

	public static RecyclableCollection<ServerBoundPacketData> transformStructureBlock(ByteBuf data) {
		Position position = PositionSerializer.readLegacyPositionI(data);
		MiddleUpdateStructureBlock.Action action = MiddleUpdateStructureBlock.Action.CONSTANT_LOOKUP.getByOrdinal(data.readByte() - 1);
		MiddleUpdateStructureBlock.Mode mode = MiddleUpdateStructureBlock.Mode.valueOf(StringSerializer.readVarIntUTF8String(data, Short.MAX_VALUE));
		String name = StringSerializer.readVarIntUTF8String(data, Short.MAX_VALUE);
		byte offsetX = (byte) data.readInt();
		byte offsetY = (byte) data.readInt();
		byte offsetZ = (byte) data.readInt();
		byte sizeX = (byte) data.readInt();
		byte sizeY = (byte) data.readInt();
		byte sizeZ = (byte) data.readInt();
		MiddleUpdateStructureBlock.Mirror mirror = MiddleUpdateStructureBlock.Mirror.valueOf(StringSerializer.readVarIntUTF8String(data, Short.MAX_VALUE));
		MiddleUpdateStructureBlock.Rotation rotation = MiddleUpdateStructureBlock.Rotation.valueOf(StringSerializer.readVarIntUTF8String(data, Short.MAX_VALUE));
		String metadata = StringSerializer.readVarIntUTF8String(data, Short.MAX_VALUE);
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

	public static RecyclableCollection<ServerBoundPacketData> transformNameItemSString(ByteBuf data) {
		return RecyclableSingletonList.create(MiddleNameItem.create(StringSerializer.readVarIntUTF8String(data, Short.MAX_VALUE)));
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

	public static RecyclableCollection<ServerBoundPacketData> transformBasicCommandBlockEdit(ByteBuf data) {
		Position position = PositionSerializer.readLegacyPositionI(data);
		String command = StringSerializer.readShortUTF16BEString(data, Short.MAX_VALUE);
		return RecyclableSingletonList.create(MiddleUpdateCommandBlock.create(
			position, command, MiddleUpdateCommandBlock.Mode.REDSTONE, 0
		));
	}

	public static RecyclableCollection<ServerBoundPacketData> transformAdvancedCommandBlockEdit(ByteBuf data, boolean useTrackOutput) {
		int type = data.readByte();
		switch (type) {
			case 0: {
				Position position = PositionSerializer.readLegacyPositionI(data);
				String command = StringSerializer.readVarIntUTF8String(data, Short.MAX_VALUE);
				int trackOutput = useTrackOutput ? data.readByte() : 0;
				return RecyclableSingletonList.create(MiddleUpdateCommandBlock.create(
					position, command, MiddleUpdateCommandBlock.Mode.REDSTONE,
					BitUtils.createIBitMaskFromBit(MiddleUpdateCommandBlock.FLAGS_BIT_TRACK_OUTPUT, trackOutput)
				));
			}
			case 1: {
				int entityId = data.readInt();
				String command = StringSerializer.readVarIntUTF8String(data, Short.MAX_VALUE);
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

	protected static final int[] auto_command_block_bits = new int[] {
		MiddleUpdateCommandBlock.FLAGS_BIT_TRACK_OUTPUT, MiddleUpdateCommandBlock.FLAGS_BIT_CONDITIONAL, MiddleUpdateCommandBlock.FLAGS_BIT_AUTO
	};

	public static RecyclableCollection<ServerBoundPacketData> transformAutoCommandBlockEdit(ByteBuf data) {
		Position position = PositionSerializer.readLegacyPositionI(data);
		String command = StringSerializer.readVarIntUTF8String(data, Short.MAX_VALUE);
		int trackOutput = data.readByte();
		MiddleUpdateCommandBlock.Mode mode = null;
		switch (StringSerializer.readVarIntUTF8String(data, 16)) {
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
		int conditional = data.readByte();
		int auto = data.readByte();
		return RecyclableSingletonList.create(MiddleUpdateCommandBlock.create(
			position, command, mode,
			BitUtils.createIBitMaskFromBits(auto_command_block_bits, new int[] {trackOutput, conditional, auto})
		));
	}

}
