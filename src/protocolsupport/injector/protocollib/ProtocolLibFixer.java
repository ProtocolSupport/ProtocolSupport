package protocolsupport.injector.protocollib;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import protocolsupport.utils.Utils;

public class ProtocolLibFixer {

	private static boolean init = false;

	private static final MethodHandle vanillaDecoder = getVanillaDecoderSetterMethodHandle();
	private static final MethodHandle vanillaEncoder = getVanillaEncoderSetterMethodHandle();

	public static void init() {
		try {
			Class<?> channelInjectorClass = Class.forName("com.comphenix.protocol.injector.netty.ChannelInjector");
			Utils.setAccessible(channelInjectorClass.getDeclaredField("DECODE_BUFFER")).set(null, new MethodAccessors.PipelineDecodeMethodAccessor());
			Utils.setAccessible(channelInjectorClass.getDeclaredField("ENCODE_BUFFER")).set(null, new MethodAccessors.PipelineEncodeMethodAccessor());
			init = true;
		} catch (Throwable t) {
		}
	}

	private static MethodHandle getVanillaDecoderSetterMethodHandle() {
		try {
			Class<?> channelInjectorClass = Class.forName("com.comphenix.protocol.injector.netty.ChannelInjector");
			MethodHandle handle = MethodHandles.lookup().unreflectSetter(Utils.setAccessible(channelInjectorClass.getDeclaredField("vanillaDecoder")));
			return handle.asType(MethodType.methodType(void.class, ChannelHandler.class, ChannelHandler.class));
		} catch (Throwable t) {
		}
		return null;		
	}

	private static MethodHandle getVanillaEncoderSetterMethodHandle() {
		try {
			Class<?> channelInjectorClass = Class.forName("com.comphenix.protocol.injector.netty.ChannelInjector");
			MethodHandle handle = MethodHandles.lookup().unreflectSetter(Utils.setAccessible(channelInjectorClass.getDeclaredField("vanillaEncoder")));
			return handle.asType(MethodType.methodType(void.class, ChannelHandler.class, ChannelHandler.class));
		} catch (Throwable t) {
		}
		return null;		
	}

	public static void fixProtocolLib(ChannelPipeline pipeline, ChannelHandler decoder, ChannelHandler encoder) {
		if (!init) {
			return;
		}
		try {
			ChannelHandler protocolLibDecoder = pipeline.get("protocol_lib_decoder");
			vanillaDecoder.invokeExact(protocolLibDecoder, decoder);
			vanillaEncoder.invokeExact(protocolLibDecoder, encoder);
		} catch (Throwable t) {
			System.err.println("Failed to fix protocollib decoder, closing connection with "+pipeline.channel().remoteAddress());
			t.printStackTrace();
			pipeline.channel().close();
		}
	}

}
