package protocolsupport.protocol.transformer.v_1_5.serverboundtransformer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.Packet;

import org.bukkit.event.inventory.InventoryType;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.ServerboundPacket;
import protocolsupport.utils.PacketCreator;
import protocolsupport.utils.Utils;

public class PlayPacketTransformer implements PacketTransformer {

	@Override
	public Collection<Packet<?>> tranform(Channel channel, int packetId, PacketDataSerializer serializer) throws Exception {
		switch (packetId) {
			case 0x00: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_KEEP_ALIVE.get());
				creator.writeVarInt(serializer.readInt());
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			case 0x03: {
				return Collections.<Packet<?>>singletonList(PacketCreator.createWithData(ServerboundPacket.PLAY_CHAT.get(), serializer));
			}
			case 0x07: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_USE_ENTITY.get());
				serializer.readInt();
				creator.writeVarInt(serializer.readInt());
				creator.writeVarInt(serializer.readBoolean() ? 1 : 0);
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			case 0x0A: {
				return Collections.<Packet<?>>singletonList(PacketCreator.createWithData(ServerboundPacket.PLAY_PLAYER.get(), serializer));
			}
			case 0x0B: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_POSITION.get());
				creator.writeDouble(serializer.readDouble());
				creator.writeDouble(serializer.readDouble());
				serializer.readDouble();
				creator.writeDouble(serializer.readDouble());
				creator.writeBoolean(serializer.readBoolean());
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			case 0x0C: {
				return Collections.<Packet<?>>singletonList(PacketCreator.createWithData(ServerboundPacket.PLAY_LOOK.get(), serializer));
			}
			case 0x0D: {
				double x = serializer.readDouble();
				double yfeet = serializer.readDouble();
				double y = serializer.readDouble();
				double z = serializer.readDouble();
				float yaw = serializer.readFloat();
				float pitch = serializer.readFloat();
				boolean onGroud = serializer.readBoolean();
				//when y is -999.0D than it means that client actually tries to control vehicle movement
				if ((yfeet == -999.0D) && (y == -999.0D)) {
					PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_LOOK.get());
					creator.writeFloat(yaw);
					creator.writeFloat(pitch);
					creator.writeBoolean(onGroud);
					return Collections.<Packet<?>>singletonList(creator.create());
				} else {
					PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_POSITION_LOOK.get());
					creator.writeDouble(x);
					creator.writeDouble(yfeet);
					creator.writeDouble(z);
					creator.writeFloat(yaw);
					creator.writeFloat(pitch);
					creator.writeBoolean(onGroud);
					return Collections.<Packet<?>>singletonList(creator.create());
				}
			}
			case 0x0E: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_BLOCK_DIG.get());
				creator.writeByte(serializer.readUnsignedByte());
				creator.a(new BlockPosition(serializer.readInt(), serializer.readUnsignedByte(), serializer.readInt()));
				creator.writeByte(serializer.readUnsignedByte());
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			case 0x0F: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_BLOCK_PLACE.get());
				creator.a(new BlockPosition(serializer.readInt(), serializer.readUnsignedByte(), serializer.readInt()));
				creator.writeByte(serializer.readUnsignedByte());
				creator.writeItemStack(serializer.readItemStack());
				creator.writeByte(serializer.readUnsignedByte());
				creator.writeByte(serializer.readUnsignedByte());
				creator.writeByte(serializer.readUnsignedByte());
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			case 0x10: {
				return Collections.<Packet<?>>singletonList(PacketCreator.createWithData(ServerboundPacket.PLAY_HELD_SLOT.get(), serializer));
			}
			case 0x12: {
				serializer.readInt();
				serializer.readByte();
				return Collections.<Packet<?>>singletonList(new PacketCreator(ServerboundPacket.PLAY_ANIMATION.get()).create());
			}
			case 0x13: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_ENTITY_ACTION.get());
				creator.writeVarInt(serializer.readInt());
				creator.writeVarInt(serializer.readByte() - 1);
				creator.writeVarInt(0);
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			case 0x65: {
				return Collections.<Packet<?>>singletonList(PacketCreator.createWithData(ServerboundPacket.PLAY_CLOSE_WINDOW.get(), serializer));
			}
			case 0x66: {
				if (Utils.getBukkitPlayer(channel).getOpenInventory().getType() == InventoryType.ENCHANTING) {
					PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_WINDOW_CLICK.get());
					creator.writeByte(serializer.readByte());
					int slot = serializer.readShort();
					if (slot > 0) {
						slot++;
					}
					creator.writeShort(slot);
					creator.writeByte(serializer.readByte());
					creator.writeShort(serializer.readShort());
					creator.writeByte(serializer.readByte());
					creator.writeItemStack(serializer.readItemStack());
					return Collections.<Packet<?>>singletonList(creator.create());
				} else {
					return Collections.<Packet<?>>singletonList(PacketCreator.createWithData(ServerboundPacket.PLAY_WINDOW_CLICK.get(), serializer));
				}
			}
			case 0x6A: {
				return Collections.<Packet<?>>singletonList(PacketCreator.createWithData(ServerboundPacket.PLAY_WINDOW_TRANSACTION.get(), serializer));
			}
			case 0x6B: {
				return Collections.<Packet<?>>singletonList(PacketCreator.createWithData(ServerboundPacket.PLAY_CREATIVE_SET_SLOT.get(), serializer));
			}
			case 0x6C: {
				return Collections.<Packet<?>>singletonList(PacketCreator.createWithData(ServerboundPacket.PLAY_ENCHANT_SELECT.get(), serializer));
			}
			case 0x82: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_UPDATE_SIGN.get());
				creator.a(new BlockPosition(serializer.readInt(), serializer.readShort(), serializer.readInt()));
				for (int i = 0; i < 4; i++) {
					creator.writeString(ChatSerializer.a(new ChatComponentText(serializer.readString(15))));
				}
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			case 0xCA: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_ABILITIES.get());
				creator.writeByte(serializer.readUnsignedByte());
				creator.writeFloat(serializer.readByte() / 255.0F);
				creator.writeFloat(serializer.readByte() / 255.0F);
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			case 0xCB: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_TAB_COMPLETE.get());
				creator.writeString(serializer.readString(32767));
				creator.writeBoolean(false);
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			case 0xCC: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_SETTINGS.get());
				creator.writeString(serializer.readString(32767));
				creator.writeByte(serializer.readByte());
				int chatState = serializer.readByte();
				creator.writeByte(chatState & 7);
				creator.writeBoolean((chatState & 8) == 8);
				serializer.readByte();
				serializer.readBoolean();
				creator.writeByte(0);
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			case 0xCD: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_CLIENT_COMMAND.get());
				creator.writeVarInt(0);
				serializer.readByte();
				return Collections.<Packet<?>>singletonList(creator.create());
			}
			case 0xFA: {
				PacketCreator creator = new PacketCreator(ServerboundPacket.PLAY_CUSTOM_PAYLOAD.get());
				String tag = serializer.readString(20);
				creator.writeString(tag);
				ByteBuf buf = serializer.readBytes(serializer.readShort());
				try {
					//special handle for anvil renaming, in 1.8 it reads string from serializer, but in 1.7 and before it just reads bytes and converts it to string
					if (tag.equalsIgnoreCase("MC|ItemName")) {
						creator.writeVarInt(buf.readableBytes());
					}
					//special handle for book sign and book edit, the bytes are written as 1.5.2 stream, but server will attempt to read bytes as 1.8 stream, so we should rewrite the itemstack
					else if (tag.equals("MC|BSign") || tag.equals("MC|BEdit")) {
						PacketDataSerializer data = new PacketDataSerializer(Unpooled.wrappedBuffer(buf), serializer.getVersion());
						PacketDataSerializer newdata = new PacketDataSerializer(Unpooled.buffer(), ProtocolVersion.getLatest());
						newdata.writeItemStack(data.readItemStack());
						buf = newdata;
					}
					//special handle for command block, reason is almost the same as for books, but also stream in 1.8 should have a last output flag, and cmd block type
					else if (tag.equals("MC|AdvCdm")) {
						PacketDataSerializer data = new PacketDataSerializer(Unpooled.wrappedBuffer(buf), serializer.getVersion());
						PacketDataSerializer newdata = new PacketDataSerializer(Unpooled.buffer(), ProtocolVersion.getLatest());
						newdata.writeByte(0);
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
			case 0xFF: {
				serializer.readString(32767);
				return Collections.emptyList();
			}
		}
		return null;
	}

}
