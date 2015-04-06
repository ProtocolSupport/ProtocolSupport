package protocolsupport.protocol.v_1_5.serverboundtransformer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.AttributeKey;

import java.io.IOException;
import java.util.List;

import javax.crypto.Cipher;

import net.minecraft.server.v1_8_R2.EnumProtocol;
import net.minecraft.server.v1_8_R2.NetworkManager;
import net.minecraft.server.v1_8_R2.Packet;
import net.minecraft.server.v1_8_R2.PacketListener;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.pipeline.PublicPacketDecoder;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.utils.Utils;

public class PacketDecoder extends ByteToMessageDecoder implements PublicPacketDecoder {

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
			Packet<PacketListener>[] transformedPackets = transformers[currentProtocol.ordinal()].tranform(
				channel,
				packetId,
				new PacketDataSerializer(bytebuf, ProtocolVersion.MINECRAFT_1_5_2)
			);
			if (transformedPackets != null) {
				for (Packet<PacketListener> transformedPacket : transformedPackets) {
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

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		ProtocolStorage.clearData(ctx.channel().remoteAddress());
		ProtocolStorage.clearData(Utils.getNetworkManagerSocketAddress(ctx.channel()));
	}

}
