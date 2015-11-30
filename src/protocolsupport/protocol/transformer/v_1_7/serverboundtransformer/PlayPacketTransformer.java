package protocolsupport.protocol.transformer.v_1_7.serverboundtransformer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;

import org.bukkit.event.inventory.InventoryType;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.ServerboundPacket;
import protocolsupport.utils.PacketCreator;
import protocolsupport.utils.Utils;

public class PlayPacketTransformer implements PacketTransformer {

	@Override
	public Collection<Packet<?>> transform(Channel channel, int packetId, PacketDataSerializer serializer) throws Exception {
		switch (packetId) {
			case 0x00: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_KEEP_ALIVE.get());
				creator.writeVarInt(serializer.readInt());
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			case 0x02: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_USE_ENTITY.get());
				creator.writeVarInt(serializer.readInt());
				creator.writeVarInt(serializer.readByte() % PacketPlayInUseEntity.EnumEntityUseAction.values().length);
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			case 0x04: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_POSITION.get());
				creator.writeDouble(serializer.readDouble());
				creator.writeDouble(serializer.readDouble());
				serializer.readDouble();
				creator.writeDouble(serializer.readDouble());
				creator.writeByte(serializer.readUnsignedByte());
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			case 0x06: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_POSITION_LOOK.get());
				creator.writeDouble(serializer.readDouble());
				creator.writeDouble(serializer.readDouble());
				serializer.readDouble();
				creator.writeDouble(serializer.readDouble());
				creator.writeFloat(serializer.readFloat());
				creator.writeFloat(serializer.readFloat());
				creator.writeByte(serializer.readUnsignedByte());
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			case 0x07: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_BLOCK_DIG.get());
				creator.writeByte(serializer.readUnsignedByte());
				creator.a(new BlockPosition(serializer.readInt(), serializer.readUnsignedByte(), serializer.readInt()));
				creator.writeByte(serializer.readUnsignedByte());
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			case 0x08: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_BLOCK_PLACE.get());
				creator.a(new BlockPosition(serializer.readInt(), serializer.readUnsignedByte(), serializer.readInt()));
				creator.writeByte(serializer.readUnsignedByte());
				creator.writeItemStack(serializer.readItemStack());
				creator.writeByte(serializer.readUnsignedByte());
				creator.writeByte(serializer.readUnsignedByte());
				creator.writeByte(serializer.readUnsignedByte());
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			case 0x0A: {
				serializer.readInt();
				serializer.readByte();
				return Collections.<Packet<?>>singletonList(new PacketCreator(ServerboundPacket.PLAY_ANIMATION.get()).create());
			}
			case 0x0B: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_ENTITY_ACTION.get());
				creator.writeVarInt(serializer.readInt());
				creator.writeByte(serializer.readByte() - 1);
				creator.writeVarInt(serializer.readInt());
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			case 0x0C: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_STEER_VEHICLE.get());
				creator.writeFloat(serializer.readFloat());
				creator.writeFloat(serializer.readFloat());
				creator.writeByte((serializer.readBoolean() ? 1 : 0) + (serializer.readBoolean() ? 1 << 1 : 0));
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			case 0x0E: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_WINDOW_CLICK.get());
				creator.writeByte(serializer.readByte());
				int slot = serializer.readShort();
				if (Utils.getPlayer(channel).getOpenInventory().getType() == InventoryType.ENCHANTING) {
					if (slot > 0) {
						slot++;
					}
				}
				creator.writeShort(slot);
				creator.writeByte(serializer.readByte());
				creator.writeShort(serializer.readShort());
				creator.writeByte(serializer.readByte());
				creator.writeItemStack(serializer.readItemStack());
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			case 0x12: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_UPDATE_SIGN.get());
				creator.a(new BlockPosition(serializer.readInt(), serializer.readShort(), serializer.readInt()));
				for (int i = 0; i < 4; i++) {
					creator.writeString(ChatSerializer.a(new ChatComponentText(serializer.readString(15))));
				}
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			case 0x14: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_TAB_COMPLETE.get());
				creator.writeString(serializer.readString(32767));
				creator.writeBoolean(false);
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			case 0x15: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_SETTINGS.get());
				creator.writeString(serializer.readString(7));
				creator.writeByte(serializer.readByte());
				creator.writeByte(serializer.readByte());
				creator.writeBoolean(serializer.readBoolean());
				serializer.readByte();
				serializer.readBoolean();
				creator.writeByte(0);
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			case 0x17: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_CUSTOM_PAYLOAD.get());
				String tag = serializer.readString(20);
				creator.writeString(tag);
				ByteBuf buf = serializer.readBytes(serializer.readShort());
				try {
					//special handle for anvil renaming, in 1.8 it reads string from serializer, but in 1.7 and before it just reads bytes and converts it to string, so we append data length before data
					if (tag.equals("MC|ItemName")) {
						creator.writeVarInt(buf.readableBytes());
					}
					//special handle for book sign and book edit, the bytes are written as 1.7 stream, but server will attempt to read bytes as 1.8 stream, so we should rewrite the itemstack
					else if (tag.equals("MC|BSign") || tag.equals("MC|BEdit")) {
						PacketDataSerializer data = new PacketDataSerializer(Unpooled.wrappedBuffer(buf), serializer.getVersion());
						PacketDataSerializer newdata = new PacketDataSerializer(Unpooled.buffer(), ProtocolVersion.getLatest());
						newdata.writeItemStack(data.readItemStack());
						buf = newdata;
					}
					//special handle for command block, reason is almost the same as for books, but also stream in 1.8 should have a last output flag
					else if (tag.equals("MC|AdvCdm")) {
						PacketDataSerializer data = new PacketDataSerializer(Unpooled.wrappedBuffer(buf), serializer.getVersion());
						PacketDataSerializer newdata = new PacketDataSerializer(Unpooled.buffer(), ProtocolVersion.getLatest());
						newdata.writeByte(data.readByte());
						newdata.writeInt(data.readInt());
						newdata.writeInt(data.readInt());
						newdata.writeInt(data.readInt());
						newdata.writeString(data.readString(32767));
						newdata.writeBoolean(true);
						buf = newdata;
					}
					//now write
					creator.writeBytes(buf);
				} finally {
					buf.release();
				}
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			default: {
				return Collections.<Packet<?>>singletonList(PacketCreator.createWithData(ServerboundPacket.get(EnumProtocol.PLAY, packetId), serializer));
			}
		}
	}
}
