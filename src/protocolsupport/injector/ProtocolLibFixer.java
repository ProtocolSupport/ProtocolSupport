package protocolsupport.injector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import net.minecraft.server.v1_8_R1.Packet;

import org.bukkit.Bukkit;
import org.spigotmc.SneakyThrow;

import protocolsupport.protocol.PublicPacketDecoder;
import protocolsupport.protocol.PublicPacketEncoder;
import protocolsupport.utils.Utils;

import com.comphenix.protocol.reflect.accessors.MethodAccessor;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.MessageToByteEncoder;

public class ProtocolLibFixer {

	private static boolean methodAccessorsInjected = false;

	public static void fixProtocolLib(ChannelPipeline pipeline, ChannelHandler decoder, ChannelHandler encoder) {
		try {
			ChannelHandler protocolLibDecoder = pipeline.get("protocol_lib_decoder");
			if (protocolLibDecoder != null) {
				if (!methodAccessorsInjected) {
					Utils.<Field>setAccessible(protocolLibDecoder.getClass().getDeclaredField("DECODE_BUFFER")).set(
						null, new PipelineDecodeMethodAccessor()
					);
					Utils.<Field>setAccessible(protocolLibDecoder.getClass().getDeclaredField("ENCODE_BUFFER")).set(
						null, new PipelineEncodeMethodAccessor()
					);
					methodAccessorsInjected = true;
				}
				Utils.<Method>setAccessible(protocolLibDecoder.getClass().getDeclaredMethod("patchEncoder", MessageToByteEncoder.class)).invoke(protocolLibDecoder, encoder);
				Utils.<Field>setAccessible(protocolLibDecoder.getClass().getDeclaredField("vanillaDecoder")).set(protocolLibDecoder, decoder);
				Utils.<Field>setAccessible(protocolLibDecoder.getClass().getDeclaredField("vanillaEncoder")).set(protocolLibDecoder, encoder);
			}
		} catch (Throwable t) {
			System.err.println("Failed to fix protocollib decoder, shutting down");
			t.printStackTrace();
			Bukkit.shutdown();
		}
	}

	private static class PipelineEncodeMethodAccessor implements MethodAccessor {

		@Override
		public Method getMethod() {
			throw new RuntimeException("PipelineEncodeMethodAccessor is dynamic and can't provide method instance");
		}

		@Override
		public Object invoke(Object obj, Object... args) {
			try {
				PublicPacketEncoder publicEncoder = (PublicPacketEncoder) obj;
				publicEncoder.publicEncode((ChannelHandlerContext) args[0], (Packet) args[1], (ByteBuf) args[2]);
			} catch (Throwable t) {
				SneakyThrow.sneaky(t.getCause());
			}
			return null;
		}

	}

	private static class PipelineDecodeMethodAccessor implements MethodAccessor {

		@Override
		public Method getMethod() {
			throw new RuntimeException("PipelineDecodeMethodAccessor is dynamic and can't provide method instance");
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object invoke(Object obj, Object... args) {
			try {
				PublicPacketDecoder publicDecoder = (PublicPacketDecoder) obj;
				publicDecoder.publicDecode((ChannelHandlerContext) args[0], (ByteBuf) args[1], (List<Object>) args[2]);
			} catch (Throwable t) {
				SneakyThrow.sneaky(t.getCause());
			}
			return null;
		}

	}

}
