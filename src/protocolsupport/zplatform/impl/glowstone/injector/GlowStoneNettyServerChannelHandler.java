//package protocolsupport.zplatform.impl.glowstone.injector;
//
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//import protocolsupport.utils.netty.ChannelInitializer;
//
//public class GlowStoneNettyServerChannelHandler extends ChannelInboundHandlerAdapter {
//
//	@Override
//	public void channelRead(ChannelHandlerContext ctx, Object o) throws Exception {
//		Channel channel = (Channel) o;
//		channel.pipeline().addLast(new ChannelInitializer() {
//			@Override
//			protected void initChannel(Channel channel) throws Exception {
//				channel.pipeline().addLast(new GlowStoneServerConnectionChannel());
//			}
//		});
//		super.channelRead(ctx, o);
//	}
//
//}
