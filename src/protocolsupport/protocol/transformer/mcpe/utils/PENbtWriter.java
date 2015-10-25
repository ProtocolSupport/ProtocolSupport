package protocolsupport.protocol.transformer.mcpe.utils;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.EncoderException;

import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

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

	public static void write(NBTTagCompound compound, ByteBuf serializer) {
		writeTag("", compound, serializer.order(ByteOrder.LITTLE_ENDIAN));
	}

	private static void writeTag(String name, NBTBase tag, ByteBuf bufferLE) {
		//write type
		bufferLE.writeByte(tag.getTypeId());
		//write tag name
		byte[] data = name.getBytes(StandardCharsets.UTF_8);
		bufferLE.writeShort((short) data.length);
		bufferLE.writeBytes(data);
		//write value
		writeTagValue(tag, bufferLE);
	}

	private static void writeTagValue(NBTBase tag, ByteBuf bufferLE) {
		//write contents
		switch (tag.getTypeId()) {
			case 0: {//END
				break;
			}
			case 1: {//BYTE
				bufferLE.writeByte(((NBTTagByte) tag).f());
				break;
			}
			case 2: {//Short
				bufferLE.writeShort(((NBTTagShort) tag).e());
				break;
			}
			case 3: {//Int
				bufferLE.writeInt(((NBTTagInt) tag).d());
				break;
			}
			case 4: {//Long
				bufferLE.writeLong(((NBTTagLong) tag).c());
				break;
			}
			case 5: {//Float
				bufferLE.writeFloat(((NBTTagFloat) tag).h());
				break;
			}
			case 6: {//Double
				bufferLE.writeDouble(((NBTTagDouble) tag).g());
				break;
			}
			case 7: {//ByteArray
				byte[] data = ((NBTTagByteArray) tag).c();
				bufferLE.writeInt(data.length);
				bufferLE.writeBytes(data);
				break;
			}
			case 8: {//String
				byte[] data = ((NBTTagString) tag).a_().getBytes(StandardCharsets.UTF_8);
				bufferLE.writeShort((short) data.length);
				bufferLE.writeBytes(data);
				break;
			}
			case 9: {//List
				NBTTagList list = (NBTTagList) tag;
				bufferLE.writeByte(list.f());
				bufferLE.writeInt(list.size());
				for (int i = 0; i < list.size(); i++) {
					writeTagValue(list.g(i), bufferLE);
				}
				break;
			}
			case 10: {//Compound
				NBTTagCompound compound = (NBTTagCompound) tag;
				for (String name : compound.c()) {
					writeTag(name, compound.get(name), bufferLE);
				}
				bufferLE.writeByte(0);
				break;
			}
			case 11: {//IntArray
				int[] data = ((NBTTagIntArray) tag).c();
				bufferLE.writeInt(data.length);
				for (int i = 0; i < data.length; i++) {
					bufferLE.writeInt(data[i]);
				}
				break;
			}
			default: {
				throw new EncoderException("Tag type"+tag.getTypeId()+" is not supported");
			}
		}
	}

}
