package protocolsupport.protocol.transformer.mcpe.nbt;

import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.EncoderException;
import protocolsupport.protocol.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagByte;
import net.minecraft.server.v1_8_R3.NBTTagByteArray;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagDouble;
import net.minecraft.server.v1_8_R3.NBTTagFloat;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagIntArray;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagLong;
import net.minecraft.server.v1_8_R3.NBTTagShort;
import net.minecraft.server.v1_8_R3.NBTTagString;

public class PENbtWriter {

	public static void write(NBTTagCompound compound, PacketDataSerializer serializer) {
		writeTag("", compound, serializer);
	}

	private static void writeTag(String name, NBTBase tag, PacketDataSerializer serializer) {
		//write type
		serializer.writeByte(tag.getTypeId());
		//write tag name
		byte[] data = name.getBytes(StandardCharsets.UTF_8);
		serializer.writeShort(ByteBufUtil.swapShort((short) data.length));
		serializer.writeBytes(data);
		//write value
		writeTagValue(tag, serializer);
	}

	private static void writeTagValue(NBTBase tag, PacketDataSerializer serializer) {
		//write contents
		switch (tag.getTypeId()) {
			case 0: {//END
				break;
			}
			case 1: {//BYTE
				serializer.writeByte(((NBTTagByte) tag).f());
				break;
			}
			case 2: {//Short
				serializer.writeShort(ByteBufUtil.swapShort(((NBTTagShort) tag).e()));
				break;
			}
			case 3: {//Int
				serializer.writeInt(ByteBufUtil.swapInt(((NBTTagInt) tag).d()));
				break;
			}
			case 4: {//Long
				serializer.writeLong(ByteBufUtil.swapLong(((NBTTagLong) tag).c()));
				break;
			}
			case 5: {//Float
				serializer.order(ByteOrder.LITTLE_ENDIAN).writeFloat(((NBTTagFloat) tag).h());
				break;
			}
			case 6: {//Double
				serializer.order(ByteOrder.LITTLE_ENDIAN).writeDouble(((NBTTagDouble) tag).g());
				break;
			}
			case 7: {//ByteArray
				byte[] data = ((NBTTagByteArray) tag).c();
				serializer.writeInt(ByteBufUtil.swapInt(data.length));
				serializer.writeBytes(data);
				break;
			}
			case 8: {//String
				byte[] data = ((NBTTagString) tag).a_().getBytes(StandardCharsets.UTF_8);
				serializer.writeShort(ByteBufUtil.swapShort((short) data.length));
				serializer.writeBytes(data);
				break;
			}
			case 9: {//List
				NBTTagList list = (NBTTagList) tag;
				serializer.writeByte(list.f());
				serializer.writeInt(ByteBufUtil.swapInt(list.size()));
				for (int i = 0; i < list.size(); i++) {
					writeTagValue(list.g(i), serializer);
				}
				break;
			}
			case 10: {//Compound
				NBTTagCompound compound = (NBTTagCompound) tag;
				for (String name : compound.c()) {
					writeTag(name, compound.get(name), serializer);
				}
				serializer.writeByte(0);
				break;
			}
			case 11: {//IntArray
				int[] data = ((NBTTagIntArray) tag).c();
				serializer.writeInt(ByteBufUtil.swapInt(data.length));
				for (int i = 0; i < data.length; i++) {
					serializer.writeInt(ByteBufUtil.swapInt(data[i]));
				}
				break;
			}
			default: {
				throw new EncoderException("Tag type"+tag.getTypeId()+" is not supported");
			}
		}
	}

}
