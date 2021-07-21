package protocolsupport.protocol.pipeline.common;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.util.concurrent.ScheduledFuture;
import protocolsupport.utils.MiscUtils;

public class SimpleReadTimeoutHandler extends ChannelInboundHandlerAdapter {

	private ScheduledFuture<?> timeoutTask;
	private final long timeoutTime;
	private long lastReadTime;

	public SimpleReadTimeoutHandler(int timeout) {
		this(timeout, TimeUnit.SECONDS);
	}

	public SimpleReadTimeoutHandler(long timeout, TimeUnit tu) {
		this.timeoutTime = tu.toMillis(timeout);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		this.initialize(ctx);
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		if (this.timeoutTask != null) {
			this.timeoutTask.cancel(false);
			this.timeoutTask = null;
		}
		super.channelInactive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object message) {
		setLastRead();
		ctx.fireChannelRead(message);
	}

	public void setLastRead() {
		this.lastReadTime = MiscUtils.currentTimeMillisFromNanoTime();
	}

	private void initialize(final ChannelHandlerContext ctx) {
		this.lastReadTime = System.nanoTime();
		this.timeoutTask = ctx.executor().schedule(new Runnable() {
			@Override
			public void run() {
				if (ctx.channel().isOpen()) {
					long untilTimeout = timeoutTime - (MiscUtils.currentTimeMillisFromNanoTime() - lastReadTime);
					if (untilTimeout <= 0) {
						ctx.fireExceptionCaught(ReadTimeoutException.INSTANCE);
					} else {
						ctx.executor().schedule(this, untilTimeout, TimeUnit.MILLISECONDS);
					}
				}
			}
		}, this.timeoutTime, TimeUnit.MILLISECONDS);
	}

}
