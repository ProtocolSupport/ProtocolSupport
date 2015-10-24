package protocolsupport.protocol.transformer.mcpe.utils;

import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.EncoderException;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.utils.Allocator;
import protocolsupport.utils.DataWatcherObject;
import protocolsupport.utils.Utils;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;

public class PEDataWatcherSerializer {

	public static byte[] encode(TIntObjectMap<DataWatcherObject> objects) {
		ByteBuf bufferLE = Allocator.allocateBuffer().order(ByteOrder.LITTLE_ENDIAN);
		try {
			TIntObjectIterator<DataWatcherObject> iterator = objects.iterator();
			while (iterator.hasNext()) {
				iterator.advance();
				DataWatcherObject object = iterator.value();
				final int tk = ((object.type.getId(ProtocolVersion.MINECRAFT_PE) << 5) | (iterator.key() & 0x1F)) & 0xFF;
				bufferLE.writeByte(tk);
				switch (object.type) {
					case BYTE: {
						bufferLE.writeByte((byte) object.value);
						break;
					}
					case SHORT: {
						bufferLE.writeShort((short) object.value);
						break;
					}
					case INT: {
						bufferLE.writeInt((int) object.value);
						break;
					}
					case FLOAT: {
						bufferLE.writeFloat((float) object.value);
						break;
					}
					case STRING: {
						byte[] data = ((String) object.value).getBytes(StandardCharsets.UTF_8);
						bufferLE.writeShort((short) data.length);
						bufferLE.writeBytes(data);
						break;
					}
					case ITEMSTACK: {
						ItemStack itemstack = (ItemStack) object.value;
						bufferLE.writeShort((short) Item.getId(itemstack.getItem()));
						bufferLE.writeByte(itemstack.count);
						bufferLE.writeShort((short) itemstack.getData());
						break;
					}
					case VECTOR3I: {
						BlockPosition blockPos = (BlockPosition) object.value;
						bufferLE.writeInt(blockPos.getX());
						bufferLE.writeInt(blockPos.getY());
						bufferLE.writeInt(blockPos.getZ());
						break;
					}
					default: {
						throw new EncoderException("Datawatcher valuetype "+object.type+" is not supported");
					}
				}
			}
			bufferLE.writeByte(127);
			return Utils.toArray(bufferLE);
		} finally {
			bufferLE.release();
		}
	}

}
