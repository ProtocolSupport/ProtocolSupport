package protocolsupport.protocol.transformer.mcpe;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;

import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public class VirtualChannel implements Channel {

	protected Channel realChannel;
	protected InetSocketAddress address;
	public VirtualChannel(Channel realChannel, InetSocketAddress sender) {
		this.realChannel = realChannel;
		this.address = sender;
	}

	protected boolean open = true;

	@Override
	public boolean isActive() {
		return open;
	}

	@Override
	public boolean isOpen() {
		return open;
	}

	@Override
	public boolean isRegistered() {
		return true;
	}

	@Override
	public boolean isWritable() {
		return true;
	}

	@Override
	public SocketAddress remoteAddress() {
		return address;
	}

	@Override
	public ChannelFuture write(Object packet) {
		return realChannel.write(packet);
	}

	@Override
	public ChannelFuture write(Object packet, ChannelPromise pr) {
		return realChannel.write(packet, pr);
	}

	@Override
	public ChannelFuture writeAndFlush(Object packet) {
		return realChannel.writeAndFlush(packet);
	}

	@Override
	public ChannelFuture writeAndFlush(Object packet, ChannelPromise pr) {
		return realChannel.writeAndFlush(packet, pr);
	}

	@Override
	public ChannelFuture close() {
		open = false;
		return null;
	}

	@Override
	public ChannelFuture close(ChannelPromise pr) {
		open = false;
		return null;
	}

	@Override
	public ChannelConfig config() {
		return new ChannelConfig() {

			@Override
			public boolean isAutoRead() {
				return open;
			}

			@Override
			public ChannelConfig setWriteSpinCount(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ChannelConfig setWriteBufferLowWaterMark(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ChannelConfig setWriteBufferHighWaterMark(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean setOptions(Map<ChannelOption<?>, ?> arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public <T> boolean setOption(ChannelOption<T> arg0, T arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public ChannelConfig setMessageSizeEstimator(MessageSizeEstimator arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ChannelConfig setMaxMessagesPerRead(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ChannelConfig setConnectTimeoutMillis(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ChannelConfig setAutoRead(boolean arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ChannelConfig setAutoClose(boolean arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ChannelConfig setAllocator(ByteBufAllocator arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean isAutoClose() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public int getWriteSpinCount() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getWriteBufferLowWaterMark() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getWriteBufferHighWaterMark() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public RecvByteBufAllocator getRecvByteBufAllocator() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Map<ChannelOption<?>, Object> getOptions() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <T> T getOption(ChannelOption<T> arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public MessageSizeEstimator getMessageSizeEstimator() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getMaxMessagesPerRead() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getConnectTimeoutMillis() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public ByteBufAllocator getAllocator() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}


	@Override
	public ByteBufAllocator alloc() {
		return UnpooledByteBufAllocator.DEFAULT;
	}

	@Override
	public <T> Attribute<T> attr(AttributeKey<T> packet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(Channel o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ChannelFuture bind(SocketAddress arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelFuture bind(SocketAddress arg0, ChannelPromise arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelFuture closeFuture() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelFuture connect(SocketAddress arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelFuture connect(SocketAddress arg0, SocketAddress arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelFuture connect(SocketAddress arg0, ChannelPromise arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelFuture connect(SocketAddress arg0, SocketAddress arg1, ChannelPromise arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelFuture deregister() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelFuture deregister(ChannelPromise arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelFuture disconnect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelFuture disconnect(ChannelPromise arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventLoop eventLoop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Channel flush() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SocketAddress localAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelMetadata metadata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelFuture newFailedFuture(Throwable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelProgressivePromise newProgressivePromise() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelPromise newPromise() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelFuture newSucceededFuture() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Channel parent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelPipeline pipeline() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Channel read() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Unsafe unsafe() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelPromise voidPromise() {
		// TODO Auto-generated method stub
		return null;
	}

}
