package protocolsupport.protocol.transformer.mcpe;

import java.net.SocketAddress;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.EventExecutor;

public class FakeChannelHandlerContext implements ChannelHandlerContext {

	private VirtualChannel channel;
	public FakeChannelHandlerContext(VirtualChannel channel) {
		this.channel = channel;
	}

	@Override
	public Channel channel() {
		return channel;
	}

	@Override
	public <T> Attribute<T> attr(AttributeKey<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ByteBufAllocator alloc() {
		// TODO Auto-generated method stub
		return null;
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
	public ChannelFuture close() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelFuture close(ChannelPromise arg0) {
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
	public EventExecutor executor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelHandlerContext fireChannelActive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelHandlerContext fireChannelInactive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelHandlerContext fireChannelRead(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelHandlerContext fireChannelReadComplete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelHandlerContext fireChannelRegistered() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelHandlerContext fireChannelUnregistered() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelHandlerContext fireChannelWritabilityChanged() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelHandlerContext fireExceptionCaught(Throwable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelHandlerContext fireUserEventTriggered(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelHandlerContext flush() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelHandler handler() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRemoved() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String name() {
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
	public ChannelPipeline pipeline() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelHandlerContext read() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelPromise voidPromise() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelFuture write(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelFuture write(Object arg0, ChannelPromise arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelFuture writeAndFlush(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChannelFuture writeAndFlush(Object arg0, ChannelPromise arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
