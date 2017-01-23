package protocolsupport.protocol.pipeline.timeout;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.util.concurrent.ScheduledFuture;

public class SimpleReadTimeoutHandler extends ChannelInboundHandlerAdapter {

	private volatile ScheduledFuture<?> timeoutTask;
	protected final long timeoutTime;
	protected long lastReadTime;

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
		this.destroy();
		super.channelInactive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object message) {
		setLastRead();
		ctx.fireChannelRead(message);
	}

	public void setLastRead() {
		this.lastReadTime = System.currentTimeMillis();
	}

	private void initialize(final ChannelHandlerContext ctx) {
		this.lastReadTime = System.currentTimeMillis();
		this.timeoutTask = ctx.executor().schedule(new Runnable() {
			@Override
			public void run() {
				if (ctx.channel().isOpen()) {
					long untilTimeout = timeoutTime - (System.currentTimeMillis() - lastReadTime);
					if (untilTimeout <= 0) {
						ctx.fireExceptionCaught(ReadTimeoutException.INSTANCE);
					} else {
						ctx.executor().schedule(this, untilTimeout, TimeUnit.MILLISECONDS);
					}
				}
			}
		}, this.timeoutTime, TimeUnit.MILLISECONDS);
	}

	private void destroy() {
		if (this.timeoutTask != null) {
			this.timeoutTask.cancel(false);
			this.timeoutTask = null;
		}
	}

}
