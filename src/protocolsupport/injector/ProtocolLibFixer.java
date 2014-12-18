package protocolsupport.injector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import net.minecraft.server.v1_8_R1.Packet;

import org.bukkit.Bukkit;

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
						null, new PipelineEncodeDecodeMethodAccessor(true)
					);
					Utils.<Field>setAccessible(protocolLibDecoder.getClass().getDeclaredField("ENCODE_BUFFER")).set(
						null, new PipelineEncodeDecodeMethodAccessor(false)
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

	private static class PipelineEncodeDecodeMethodAccessor implements MethodAccessor {

		private boolean isForDecoder;
		public PipelineEncodeDecodeMethodAccessor(boolean isForDecoder) {
			this.isForDecoder = isForDecoder;
		}

		private HashMap<Class<?>, Method> cachedMethods = new HashMap<Class<?>, Method>();

		@Override
		public Method getMethod() {
			throw new RuntimeException("PipelineEncodeDecodeMethodAccessor is dynamic and can't provide method instance");
		}

		@Override
		public Object invoke(Object obj, Object... args) {
			Class<?> clazz = obj.getClass();
			try {
				if (!cachedMethods.containsKey(clazz)) {
					if (isForDecoder) {
						Method method = clazz.getDeclaredMethod("decode", ChannelHandlerContext.class, ByteBuf.class, List.class);
						cachedMethods.put(clazz, method);
					} else {
						try {
							Method method = clazz.getDeclaredMethod("encode", ChannelHandlerContext.class, Packet.class, ByteBuf.class);
							cachedMethods.put(clazz, method);
						} catch (NoSuchMethodException nosuchmethod) {
							Method method = clazz.getDeclaredMethod("encode", ChannelHandlerContext.class, Object.class, ByteBuf.class);
							cachedMethods.put(clazz, method);
						}
					}
				}
				Method method = cachedMethods.get(clazz);
				method.setAccessible(true);
				return method.invoke(obj, args);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException t) {
				System.err.println("Failed to access pipeline decode and encode methods, shutting down");
				t.printStackTrace();
				Bukkit.shutdown();
				return null;
			}
		}
		
	}

}
