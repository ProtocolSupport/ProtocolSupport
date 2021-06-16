package protocolsupport.protocol.typeremapper.legacy;

import java.nio.charset.StandardCharsets;

import org.bukkit.Material;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.PacketDataCodec;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleEditBook;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleNameItem;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePickItem;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSelectTrade;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSetBeaconEffect;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateCommandBlock;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateCommandMinecart;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateStructureBlock;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.basic.CommonNBTTransformer;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.utils.BitUtils;

public class LegacyCustomPayloadData {

	private LegacyCustomPayloadData() {
	}

	public static ByteBuf transformBrandDirectToString(ByteBuf data) {
		ByteBuf buffer = Unpooled.buffer(data.readableBytes() + VarNumberSerializer.MAX_LENGTH);
		StringSerializer.writeVarIntUTF8String(buffer, data.toString(StandardCharsets.UTF_8));
		return buffer;
	}

	public static void transformAndWriteBookEdit(PacketDataCodec codec, ProtocolVersion version, int heldSlot, ByteBuf data) {
		NetworkItemStack book = ItemStackSerializer.readItemStack(data, version);
		if (!book.isNull()) {
			book.setTypeId(ItemMaterialLookup.getRuntimeId(Material.WRITABLE_BOOK));
			codec.writeServerbound(MiddleEditBook.create(book, false, heldSlot));
		}
	}

	public static void transformAndWriteBookSign(PacketDataCodec codec, ProtocolVersion version, int heldSlot, ByteBuf data) {
		NetworkItemStack book = ItemStackSerializer.readItemStack(data, version);
		if (!book.isNull()) {
			book.setTypeId(ItemMaterialLookup.getRuntimeId(Material.WRITABLE_BOOK));
			if (version == ProtocolVersion.MINECRAFT_1_8) {
				NBTCompound rootTag = book.getNBT();
				if (rootTag != null) {
					CommonNBTTransformer.toLegacyChatList(rootTag.getStringListTagOrNull(CommonNBT.BOOK_PAGES), I18NData.DEFAULT_LOCALE);
				}
			}
			codec.writeServerbound(MiddleEditBook.create(book, true, heldSlot));
		}
	}

	public static void transformAndWriteStructureBlock(PacketDataCodec codec, ByteBuf data) {
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
		codec.writeServerbound(MiddleUpdateStructureBlock.create(
			position, action, mode, name,
			offsetX, offsetY, offsetZ, sizeX, sizeY, sizeZ,
			mirror, rotation, metadata, integrity, seed, flags
		));
	}

	public static void transformAndWriteSetBeaconEffect(PacketDataCodec codec, ByteBuf data) {
		int primary = data.readInt();
		int secondary = data.readInt();
		codec.writeServerbound(MiddleSetBeaconEffect.create(primary, secondary));
	}

	public static void transformAndWriteNameItemSString(PacketDataCodec codec, ByteBuf data) {
		codec.writeServerbound(MiddleNameItem.create(StringSerializer.readVarIntUTF8String(data, Short.MAX_VALUE)));
	}

	public static void transformAndWriteNameItemDString(PacketDataCodec codec, ByteBuf data) {
		String name = data.toString(StandardCharsets.UTF_8);
		codec.writeServerbound(MiddleNameItem.create(name));
	}

	public static void transformAndWritePickItem(PacketDataCodec codec, ByteBuf data) {
		int slot = VarNumberSerializer.readVarInt(data);
		codec.writeServerbound(MiddlePickItem.create(slot));
	}

	public static void transformAndWriteTradeSelect(PacketDataCodec codec, ByteBuf data) {
		int slot = data.readInt();
		codec.writeServerbound(MiddleSelectTrade.create(slot));
	}

	public static void transformAndWriteBasicCommandBlockEdit(PacketDataCodec codec, ByteBuf data) {
		Position position = PositionSerializer.readLegacyPositionI(data);
		String command = StringSerializer.readShortUTF16BEString(data, Short.MAX_VALUE);
		codec.writeServerbound(MiddleUpdateCommandBlock.create(
			position, command, MiddleUpdateCommandBlock.Mode.REDSTONE, 0
		));
	}

	public static void transformAndWriteAdvancedCommandBlockEdit(PacketDataCodec codec, ByteBuf data, boolean useTrackOutput) {
		int type = data.readByte();
		switch (type) {
			case 0: {
				Position position = PositionSerializer.readLegacyPositionI(data);
				String command = StringSerializer.readVarIntUTF8String(data, Short.MAX_VALUE);
				int trackOutput = useTrackOutput ? data.readByte() : 0;
				codec.writeServerbound(MiddleUpdateCommandBlock.create(
					position, command, MiddleUpdateCommandBlock.Mode.REDSTONE,
					BitUtils.createIBitMaskFromBit(MiddleUpdateCommandBlock.FLAGS_BIT_TRACK_OUTPUT, trackOutput)
				));
				break;
			}
			case 1: {
				int entityId = data.readInt();
				String command = StringSerializer.readVarIntUTF8String(data, Short.MAX_VALUE);
				boolean trackOutput = useTrackOutput ? data.readBoolean() : false;
				codec.writeServerbound(MiddleUpdateCommandMinecart.create(
					entityId, command, trackOutput
				));
				break;
			}
			default: {
				break;
			}
		}
	}

	protected static final int[] auto_command_block_bits = new int[] {
		MiddleUpdateCommandBlock.FLAGS_BIT_TRACK_OUTPUT, MiddleUpdateCommandBlock.FLAGS_BIT_CONDITIONAL, MiddleUpdateCommandBlock.FLAGS_BIT_AUTO
	};

	public static void transformAndWriteAutoCommandBlockEdit(PacketDataCodec codec, ByteBuf data) {
		Position position = PositionSerializer.readLegacyPositionI(data);
		String command = StringSerializer.readVarIntUTF8String(data, Short.MAX_VALUE);
		int trackOutput = data.readByte();
		String modeString = StringSerializer.readVarIntUTF8String(data, 16);
		MiddleUpdateCommandBlock.Mode mode = switch (modeString) {
			case "SEQUENCE" -> MiddleUpdateCommandBlock.Mode.SEQUENCE;
			case "AUTO" -> MiddleUpdateCommandBlock.Mode.AUTO;
			case "REDSTONE" -> MiddleUpdateCommandBlock.Mode.REDSTONE;
			default -> throw new IllegalArgumentException("Unexpected command block mode " + modeString);
		};
		int conditional = data.readByte();
		int auto = data.readByte();
		codec.writeServerbound(MiddleUpdateCommandBlock.create(
			position, command, mode,
			BitUtils.createIBitMaskFromBits(auto_command_block_bits, new int[] {trackOutput, conditional, auto})
		));
	}

}
