package protocolsupport.protocol.transformer.mcpe.utils;

import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.EncoderException;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.utils.Allocator;
import protocolsupport.utils.DataWatcherObject;
import protocolsupport.utils.Utils;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;

public class PEDataWatcherSerializer {

	public static byte[] encode(TIntObjectMap<DataWatcherObject> objects) {
		PacketDataSerializer serializer = new PacketDataSerializer(Allocator.allocateBuffer(), ProtocolVersion.MINECRAFT_PE);
		try {
			TIntObjectIterator<DataWatcherObject> iterator = objects.iterator();
			while (iterator.hasNext()) {
				iterator.advance();
				DataWatcherObject object = iterator.value();
				final int tk = ((object.type.getId(ProtocolVersion.MINECRAFT_PE) << 5) | (iterator.key() & 0x1F)) & 0xFF;
				serializer.writeByte(tk);
				switch (object.type) {
					case BYTE: {
						serializer.writeByte((byte) object.value);
						break;
					}
					case SHORT: {
						serializer.writeShort(ByteBufUtil.swapShort((short) object.value));
						break;
					}
					case INT: {
						serializer.writeInt(ByteBufUtil.swapInt((int) object.value));
						break;
					}
					case FLOAT: {
						serializer.order(ByteOrder.LITTLE_ENDIAN).writeFloat((float) object.value);
						break;
					}
					case STRING: {
						byte[] data = ((String) object.value).getBytes(StandardCharsets.UTF_8);
						serializer.writeShort(ByteBufUtil.swapShort((short) data.length));
						serializer.writeBytes(data);
						break;
					}
					case ITEMSTACK: {
						ItemStack itemstack = (ItemStack) object.value;
						serializer.writeShort(ByteBufUtil.swapShort((short) Item.getId(itemstack.getItem())));
						serializer.writeByte(itemstack.count);
						serializer.writeShort(ByteBufUtil.swapShort((short) itemstack.getData()));
						break;
					}
					case VECTOR3I: {
						BlockPosition blockPos = (BlockPosition) object.value;
						serializer.writeInt(ByteBufUtil.swapInt(blockPos.getX()));
						serializer.writeInt(ByteBufUtil.swapInt(blockPos.getY()));
						serializer.writeInt(ByteBufUtil.swapInt(blockPos.getZ()));
						break;
					}
					default: {
						throw new EncoderException("Datawatcher valuetype "+object.type+" is not supported");
					}
				}
			}
			serializer.writeByte(127);
			return Utils.toArray(serializer);
		} finally {
			serializer.release();
		}
	}

}
