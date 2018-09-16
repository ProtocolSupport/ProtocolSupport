package protocolsupport.utils.netty;

import java.util.List;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.internal.TypeParameterMatcher;

public abstract class MessageToMessageCodec<INBOUND_IN, OUTBOUND_IN> extends ChannelDuplexHandler {

	private final MessageToMessageEncoder<Object> encoder = new MessageToMessageEncoder<Object>() {

		@Override
		public boolean acceptOutboundMessage(Object msg) {
			return MessageToMessageCodec.this.acceptOutboundMessage(msg);
		}

		@Override
		@SuppressWarnings("unchecked")
		protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
			MessageToMessageCodec.this.encode(ctx, (OUTBOUND_IN) msg, out);
		}
	};

	private final MessageToMessageDecoder<Object> decoder = new MessageToMessageDecoder<Object>() {

		@Override
		public boolean acceptInboundMessage(Object msg) throws Exception {
			return MessageToMessageCodec.this.acceptInboundMessage(msg);
		}

		@Override
		@SuppressWarnings("unchecked")
		protected void decode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
			MessageToMessageCodec.this.decode(ctx, (INBOUND_IN) msg, out);
		}
	};

	private final TypeParameterMatcher inboundMsgMatcher;
	private final TypeParameterMatcher outboundMsgMatcher;

	protected MessageToMessageCodec() {
		inboundMsgMatcher = TypeParameterMatcher.find(this, MessageToMessageCodec.class, "INBOUND_IN");
		outboundMsgMatcher = TypeParameterMatcher.find(this, MessageToMessageCodec.class, "OUTBOUND_IN");
	}

	protected MessageToMessageCodec(Class<? extends INBOUND_IN> inboundMessageType, Class<? extends OUTBOUND_IN> outboundMessageType) {
		inboundMsgMatcher = TypeParameterMatcher.get(inboundMessageType);
		outboundMsgMatcher = TypeParameterMatcher.get(outboundMessageType);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		decoder.channelRead(ctx, msg);
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		encoder.write(ctx, msg, promise);
	}

	public boolean acceptInboundMessage(Object msg) {
		return inboundMsgMatcher.match(msg);
	}

	public boolean acceptOutboundMessage(Object msg) {
		return outboundMsgMatcher.match(msg);
	}

	protected abstract void encode(ChannelHandlerContext ctx, OUTBOUND_IN msg, List<Object> out) throws Exception;

	protected abstract void decode(ChannelHandlerContext ctx, INBOUND_IN msg, List<Object> out) throws Exception;

}
