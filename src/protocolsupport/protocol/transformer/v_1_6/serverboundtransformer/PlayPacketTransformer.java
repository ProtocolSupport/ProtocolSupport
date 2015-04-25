package protocolsupport.protocol.transformer.v_1_6.serverboundtransformer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.io.IOException;

import net.minecraft.server.v1_8_R2.BlockPosition;
import net.minecraft.server.v1_8_R2.ChatComponentText;
import net.minecraft.server.v1_8_R2.EnumProtocol;
import net.minecraft.server.v1_8_R2.EnumProtocolDirection;
import net.minecraft.server.v1_8_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R2.Packet;
import net.minecraft.server.v1_8_R2.PacketListener;

import org.bukkit.event.inventory.InventoryType;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.utils.Utils;

public class PlayPacketTransformer implements PacketTransformer {

	@SuppressWarnings("unchecked")
	@Override
	public Packet<PacketListener>[] tranform(Channel channel, int packetId, PacketDataSerializer serializer) throws IOException, IllegalAccessException, InstantiationException {
		PacketDataSerializer packetdata = new PacketDataSerializer(Unpooled.buffer(), serializer.getVersion());
		boolean useOriginalStream = false;
		Packet<PacketListener> packet = null;
		switch (packetId) {
			case 0x00: { //PacketPlayInKeepAlive
				packet = getPacketById(0x00);
				packetdata.writeVarInt(serializer.readInt());
				break;
			}
			case 0x03: { //PacketPlayInChat
				packet = getPacketById(0x01);
				useOriginalStream = true;
				break;
			}
			case 0x07: { //PacketPlayInUseEntity
				packet = getPacketById(0x02);
				serializer.readInt();
				packetdata.writeVarInt(serializer.readInt());
				packetdata.writeVarInt(serializer.readBoolean() ? 1 : 0);
				break;
			}
			case 0x0A: { //PacketPlayInFlying
				packet = getPacketById(0x03);
				useOriginalStream = true;
				break;
			}
			case 0x0B: { //PacketPlayInPosition
				packet = getPacketById(0x04);
				packetdata.writeDouble(serializer.readDouble());
				packetdata.writeDouble(serializer.readDouble());
				serializer.readDouble();
				packetdata.writeDouble(serializer.readDouble());
				packetdata.writeBoolean(serializer.readBoolean());
				break;
			}
			case 0x0C: { //PacketPlayInLook
				packet = getPacketById(0x05);
				useOriginalStream = true;
				break;
			}
			case 0x0D: { //PacketPlayInPositionLook
				packet = getPacketById(0x06);
				packetdata.writeDouble(serializer.readDouble());
				packetdata.writeDouble(serializer.readDouble());
				serializer.readDouble();
				packetdata.writeDouble(serializer.readDouble());
				packetdata.writeFloat(serializer.readFloat());
				packetdata.writeFloat(serializer.readFloat());
				packetdata.writeBoolean(serializer.readBoolean());
				break;
			}
			case 0x0E: { //PacketPlayInBlockDig
				packet = getPacketById(0x07);
				packetdata.writeByte(serializer.readUnsignedByte());
				packetdata.a(new BlockPosition(serializer.readInt(), serializer.readUnsignedByte(), serializer.readInt()));
				packetdata.writeByte(serializer.readUnsignedByte());
				break;
			}
			case 0x0F: { //PacketPlayInBlockPlace
				packet = getPacketById(0x08);
				packetdata.a(new BlockPosition(serializer.readInt(), serializer.readUnsignedByte(), serializer.readInt()));
				packetdata.writeByte(serializer.readUnsignedByte());
				packetdata.writeItemStack(serializer.readItemStack());
				packetdata.writeByte(serializer.readUnsignedByte());
				packetdata.writeByte(serializer.readUnsignedByte());
				packetdata.writeByte(serializer.readUnsignedByte());
				break;
			}
			case 0x10: { //PacketPlayInHeldItemSlot
				packet = getPacketById(0x09);
				useOriginalStream = true;
				break;
			}
			case 0x12: { //PacketPlayInArmAnimation
				packet = getPacketById(0x0A);
				break;
			}
			case 0x13: { //PacketPlayInEntityAction
				packet = getPacketById(0x0B);
				packetdata.writeVarInt(serializer.readInt());
				packetdata.writeVarInt(serializer.readByte());
				packetdata.writeVarInt(serializer.readInt());
				break;
			}
			case 0x1B: { //PacketPlayInSteerVehicle
				packet = getPacketById(0x0C);
				packetdata.writeFloat(serializer.readFloat());
				packetdata.writeFloat(serializer.readFloat());
				packetdata.writeByte((serializer.readBoolean() ? 1 : 0) + (serializer.readBoolean() ? 1 << 1 : 0));
				break;
			}
			case 0x65: { //PacketPlayInCloseWindow
				packet = getPacketById(0x0D);
				useOriginalStream = true;
				break;
			}
			case 0x66: { //PacketPlayInWindowClick
				packet = getPacketById(0x0E);
				if (Utils.getPlayer(channel).getOpenInventory().getType() == InventoryType.ENCHANTING) {
					packetdata.writeByte(serializer.readByte());
					int slot = serializer.readShort();
					if (slot > 0) {
						slot++;
					}
					packetdata.writeShort(slot);
					packetdata.writeByte(serializer.readByte());
					packetdata.writeShort(serializer.readShort());
					packetdata.writeByte(serializer.readByte());
					packetdata.writeItemStack(serializer.readItemStack());
				} else {
					useOriginalStream = true;
				}
				break;
			}
			case 0x6A: { //PacketPlayInTransaction
				packet = getPacketById(0x0F);
				useOriginalStream = true;
				break;
			}
			case 0x6B: { //PacketPlayInSetCreativeSlot
				packet = getPacketById(0x10);
				useOriginalStream = true;
				break;
			}
			case 0x6C: { //PacketPlayInEnchantItem
				packet = getPacketById(0x11);
				useOriginalStream = true;
				break;
			}
			case 0x82: { //PacketPlayInUpdateSign
				packet = getPacketById(0x12);
				packetdata.a(new BlockPosition(serializer.readInt(), serializer.readShort(), serializer.readInt()));
				for (int i = 0; i < 4; i++) {
					packetdata.writeString(ChatSerializer.a(new ChatComponentText(serializer.readString(15))));
				}
				break;
			}
			case 0xCA: { //PacketPlayInAbilities
				packet = getPacketById(0x13);
				useOriginalStream = true;
				break;
			}
			case 0xCB: { //PacketPlayInTabComplete
				packet = getPacketById(0x14);
				packetdata.writeString(serializer.readString(32767));
				packetdata.writeBoolean(false);
				break;
			}
			case 0xCC: { //PacketPlayInSettings
				packet = getPacketById(0x15);
				packetdata.writeString(serializer.readString(32767));
				packetdata.writeByte(serializer.readByte());
				int chatState = serializer.readByte();
				packetdata.writeByte(chatState & 7);
				packetdata.writeBoolean((chatState & 8) == 8);
				serializer.readByte();
				serializer.readBoolean();
				packetdata.writeByte(0);
				break;
			}
			case 0xCD: { //PacketPlayInClientCommand
				packet = getPacketById(0x16);
				packetdata.writeVarInt(0);
				serializer.readByte();
				break;
			}
			case 0xFA: { //PacketPlayInCustomPayload
				packet = getPacketById(0x17);
				String tag = serializer.readString(20);
				packetdata.writeString(tag);
				ByteBuf buf = serializer.readBytes(serializer.readShort());
				//special handle for anvil renaming, in 1.8 it reads string from serializer, but in 1.7 and before it just reads bytes and converts it to string
				if (tag.equalsIgnoreCase("MC|ItemName")) {
					packetdata.writeVarInt(buf.readableBytes());
				}
				//special handle for book sign and book edit, the bytes are written as 1.6 stream, but server will attempt to read bytes as 1.8 stream, so we should rewrite the itemstack
				else if (tag.equals("MC|BSign") || tag.equals("MC|BEdit")) {
					PacketDataSerializer data = new PacketDataSerializer(Unpooled.wrappedBuffer(buf), serializer.getVersion());
					PacketDataSerializer newdata = new PacketDataSerializer(Unpooled.buffer(), ProtocolVersion.MINECRAFT_1_8);
					newdata.writeItemStack(data.readItemStack());
					buf = newdata.readBytes(newdata.readableBytes());
				}
				//special handle for command block, reason is almost the same as for books, but also stream in 1.8 should have a last output flag, and cmd block type
				else if (tag.equals("MC|AdvCdm")) {
					PacketDataSerializer data = new PacketDataSerializer(Unpooled.wrappedBuffer(buf), serializer.getVersion());
					PacketDataSerializer newdata = new PacketDataSerializer(Unpooled.buffer(), ProtocolVersion.MINECRAFT_1_8);
					newdata.writeByte(0);
					newdata.writeInt(data.readInt());
					newdata.writeInt(data.readInt());
					newdata.writeInt(data.readInt());
					newdata.writeString(data.readString(32767));
					newdata.writeBoolean(true);
					buf = newdata.readBytes(newdata.readableBytes());
				}
				packetdata.writeBytes(buf);
				break;
			}
			case 0xFF: { //No corresponding packet
				serializer.readString(32767);
				return new Packet[0];
			}
		}
		if (packet != null) {
			packet.a(useOriginalStream ? serializer : packetdata);
			return new Packet[] {packet};
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private Packet<PacketListener> getPacketById(int realPacketId) throws IllegalAccessException, InstantiationException {
		return EnumProtocol.PLAY.a(EnumProtocolDirection.SERVERBOUND, realPacketId);
	}

}
