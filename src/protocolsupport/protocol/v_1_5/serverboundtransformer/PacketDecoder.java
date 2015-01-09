package protocolsupport.protocol.v_1_5.serverboundtransformer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.AttributeKey;

import java.io.IOException;
import java.util.List;

import javax.crypto.Cipher;

import net.minecraft.server.v1_8_R1.EnumProtocol;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.Packet;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.ProtocolVersion;
import protocolsupport.protocol.PublicPacketDecoder;

public class PacketDecoder extends ByteToMessageDecoder implements PublicPacketDecoder {

	private ProtocolVersion version;
	public PacketDecoder(ProtocolVersion version) {
		this.version = version;
	}

	@SuppressWarnings("unchecked")
	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	private static final PacketTransformer[] transformers = new PacketTransformer[] {
		new HandshakePacketTransformer(),
		new PlayPacketTransformer(),
		new StatusPacketTransformer(),
		new LoginPacketTransformer()
	};

	private PacketDecrypter decrypter;

	public void attachDecryptor(Cipher cipher) {
		decrypter = new PacketDecrypter(cipher);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> packets) throws Exception {
		if (bytebuf.readableBytes() == 0) {
			return;
		}
		Channel channel = ctx.channel();
		bytebuf.markReaderIndex();
		try {
			if (decrypter != null) {
				bytebuf = decrypter.decrypt(ctx, bytebuf);
			}
			EnumProtocol currentProtocol = channel.attr(currentStateAttrKey).get();
			int packetId = bytebuf.readUnsignedByte();
			Packet[] transformedPackets = transformers[currentProtocol.ordinal()].tranform(
				channel,
				packetId,
				new PacketDataSerializer(bytebuf, version)
			);
			if (transformedPackets != null) {
				for (Packet transformedPacket : transformedPackets) {
					packets.add(transformedPacket);
				}
				return;
			} else {
				throw new IOException("Can't deserialize unknown packet "+packetId);
			}
		} catch (IndexOutOfBoundsException ex) {
		}
		bytebuf.resetReaderIndex();
	}

	@Override
	public void publicDecode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		decode(ctx, input, list);
	}

}
