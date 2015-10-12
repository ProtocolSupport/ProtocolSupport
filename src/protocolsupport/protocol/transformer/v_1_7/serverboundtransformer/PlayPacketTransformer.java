package protocolsupport.protocol.transformer.v_1_7.serverboundtransformer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;

import org.bukkit.event.inventory.InventoryType;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.utils.Utils;

public class PlayPacketTransformer implements PacketTransformer {

	@Override
	public void tranform(Channel channel, int packetId, Packet<PacketListener> packet, PacketDataSerializer serializer, PacketDataSerializer packetdata) throws IOException {
		switch (packetId) {
			case 0x00: { //PacketPlayInKeepAlive
				packetdata.writeVarInt(serializer.readInt());
				break;
			}
			case 0x02: { //PacketPlayInUseEntity
				packetdata.writeVarInt(serializer.readInt());
				packetdata.writeVarInt(serializer.readByte() % PacketPlayInUseEntity.EnumEntityUseAction.values().length);
				break;
			}
			case 0x04: { //PacketPlayInPosition
				packetdata.writeDouble(serializer.readDouble());
				packetdata.writeDouble(serializer.readDouble());
				serializer.readDouble();
				packetdata.writeDouble(serializer.readDouble());
				packetdata.writeByte(serializer.readUnsignedByte());
				break;
			}
			case 0x06: { //PacketPlayInPositionLook
				packetdata.writeDouble(serializer.readDouble());
				packetdata.writeDouble(serializer.readDouble());
				serializer.readDouble();
				packetdata.writeDouble(serializer.readDouble());
				packetdata.writeFloat(serializer.readFloat());
				packetdata.writeFloat(serializer.readFloat());
				packetdata.writeByte(serializer.readUnsignedByte());
				break;
			}
			case 0x07: { //PacketPlayInBlockDig
				packetdata.writeByte(serializer.readUnsignedByte());
				packetdata.a(new BlockPosition(serializer.readInt(), serializer.readUnsignedByte(), serializer.readInt()));
				packetdata.writeByte(serializer.readUnsignedByte());
				break;
			}
			case 0x08: { //PacketPlayInBlockPlace
				packetdata.a(new BlockPosition(serializer.readInt(), serializer.readUnsignedByte(), serializer.readInt()));
				packetdata.writeByte(serializer.readUnsignedByte());
				packetdata.writeItemStack(serializer.readItemStack());
				packetdata.writeByte(serializer.readUnsignedByte());
				packetdata.writeByte(serializer.readUnsignedByte());
				packetdata.writeByte(serializer.readUnsignedByte());
				break;
			}
			case 0x0A: { //PacketPlayInArmAnimation
				serializer.readInt();
				serializer.readByte();
				break;
			}
			case 0x0B: { //PacketPlayInEntityAction
				packetdata.writeVarInt(serializer.readInt());
				packetdata.writeByte(serializer.readByte() - 1);
				packetdata.writeVarInt(serializer.readInt());
				break;
			}
			case 0x0C: { //PacletPlayInSteerVehicle
				packetdata.writeFloat(serializer.readFloat());
				packetdata.writeFloat(serializer.readFloat());
				packetdata.writeByte((serializer.readBoolean() ? 1 : 0) + (serializer.readBoolean() ? 1 << 1 : 0));
				break;
			}
			case 0x0E: { //PacketPlayInWindowClick
				packetdata.writeByte(serializer.readByte());
				int slot = serializer.readShort();
				if (Utils.getBukkitPlayer(channel).getOpenInventory().getType() == InventoryType.ENCHANTING) {
					if (slot > 0) {
						slot++;
					}
				}
				packetdata.writeShort(slot);
				packetdata.writeByte(serializer.readByte());
				packetdata.writeShort(serializer.readShort());
				packetdata.writeByte(serializer.readByte());
				packetdata.writeItemStack(serializer.readItemStack());
				break;
			}
			case 0x12: { //PacketPlayInUpdateSign
				packetdata.a(new BlockPosition(serializer.readInt(), serializer.readShort(), serializer.readInt()));
				for (int i = 0; i < 4; i++) {
					packetdata.writeString(ChatSerializer.a(new ChatComponentText(serializer.readString(15))));
				}
				break;
			}
			case 0x14: { //PacketPlayInTabComplete
				packetdata.writeString(serializer.readString(32767));
				packetdata.writeBoolean(false);
				break;
			}
			case 0x15: { //PackeyPlayInSettings
				packetdata.writeString(serializer.readString(7));
				packetdata.writeByte(serializer.readByte());
				packetdata.writeByte(serializer.readByte());
				packetdata.writeBoolean(serializer.readBoolean());
				serializer.readByte();
				serializer.readBoolean();
				packetdata.writeByte(0);
				break;
			}
			case 0x17: { //PacketPlayInCustomPayload
				String tag = serializer.readString(20);
				packetdata.writeString(tag);
				ByteBuf buf = serializer.readBytes(serializer.readShort());
				try {
					//special handle for anvil renaming, in 1.8 it reads string from serializer, but in 1.7 and before it just reads bytes and converts it to string, so we append data length before data
					if (tag.equals("MC|ItemName")) {
						packetdata.writeVarInt(buf.readableBytes());
					}
					//special handle for book sign and book edit, the bytes are written as 1.7 stream, but server will attempt to read bytes as 1.8 stream, so we should rewrite the itemstack
					else if (tag.equals("MC|BSign") || tag.equals("MC|BEdit")) {
						PacketDataSerializer data = new PacketDataSerializer(Unpooled.wrappedBuffer(buf), serializer.getVersion());
						PacketDataSerializer newdata = new PacketDataSerializer(Unpooled.buffer(), ProtocolVersion.MINECRAFT_1_8);
						newdata.writeItemStack(data.readItemStack());
						buf = newdata;
					}
					//special handle for command block, reason is almost the same as for books, but also stream in 1.8 should have a last output flag
					else if (tag.equals("MC|AdvCdm")) {
						PacketDataSerializer data = new PacketDataSerializer(Unpooled.wrappedBuffer(buf), serializer.getVersion());
						PacketDataSerializer newdata = new PacketDataSerializer(Unpooled.buffer(), ProtocolVersion.MINECRAFT_1_8);
						newdata.writeByte(data.readByte());
						newdata.writeInt(data.readInt());
						newdata.writeInt(data.readInt());
						newdata.writeInt(data.readInt());
						newdata.writeString(data.readString(32767));
						newdata.writeBoolean(true);
						buf = newdata;
					}
					//now write
					packetdata.writeBytes(buf);
				} finally {
					buf.release();
				}
				break;
			}
			default: { //Any other packet - just read from original stream
				packet.a(serializer);
				return;
			}
		}
		//decode transformed packetdata
		packet.a(packetdata);
	}

}
