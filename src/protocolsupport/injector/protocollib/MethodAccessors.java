package protocolsupport.injector.protocollib;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;
import java.util.List;

import net.minecraft.server.v1_8_R1.Packet;

import org.spigotmc.SneakyThrow;

import protocolsupport.protocol.PublicPacketDecoder;
import protocolsupport.protocol.PublicPacketEncoder;

import com.comphenix.protocol.reflect.accessors.MethodAccessor;

public class MethodAccessors {

	public static class PipelineEncodeMethodAccessor implements MethodAccessor {

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
				SneakyThrow.sneaky(t);
			}
			return null;
		}

	}

	public static class PipelineDecodeMethodAccessor implements MethodAccessor {

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
				SneakyThrow.sneaky(t);
			}
			return null;
		}

	}

}
