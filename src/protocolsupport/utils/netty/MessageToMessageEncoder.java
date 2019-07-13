package protocolsupport.utils.netty;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.RecyclableArrayList;
import io.netty.util.internal.TypeParameterMatcher;

public abstract class MessageToMessageEncoder<I> extends ChannelOutboundHandlerAdapter {

	private final TypeParameterMatcher matcher;

	protected MessageToMessageEncoder() {
		this.matcher = TypeParameterMatcher.find(this, MessageToMessageEncoder.class, "I");
	}

	protected MessageToMessageEncoder(final Class<? extends I> clazz) {
		this.matcher = TypeParameterMatcher.get(clazz);
	}

	public boolean acceptOutboundMessage(final Object o) {
		return this.matcher.match(o);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void write(final ChannelHandlerContext channelHandlerContext, final Object o, final ChannelPromise channelPromise) {
		RecyclableArrayList instance = null;
		try {
			if (this.acceptOutboundMessage(o)) {
				instance = RecyclableArrayList.newInstance();
				try {
					this.encode(channelHandlerContext, (I) o, (List<Object>) instance);
				} finally {
					ReferenceCountUtil.release(o);
				}
				if (instance.isEmpty()) {
					instance.recycle();
					instance = null;
					channelPromise.setSuccess();
				}
			} else {
				channelHandlerContext.write(o, channelPromise);
			}
		} catch (EncoderException ex) {
			throw ex;
		} catch (Throwable t) {
			throw new EncoderException(t);
		} finally {
			if (instance != null) {
				final int n = instance.size() - 1;
				if (n == 0) {
					channelHandlerContext.write(instance.get(0), channelPromise);
				} else if (n > 0) {
					ChannelPromise voidPromise = channelHandlerContext.voidPromise();
					boolean b = channelPromise == voidPromise;
					for (int i = 0; i < n; ++i) {
						ChannelPromise promise;
						if (b) {
							promise = voidPromise;
						} else {
							promise = channelHandlerContext.newPromise();
						}
						channelHandlerContext.write(instance.get(i), promise);
					}
					channelHandlerContext.write(instance.get(n), channelPromise);
				}
				instance.recycle();
			}
		}
	}

	protected abstract void encode(final ChannelHandlerContext ctx, final I msg, final List<Object> list) throws Exception;

}