package com.github.shevchik.protocolsupport.protocol.core;

import io.netty.channel.ChannelPipeline;
import com.github.shevchik.protocolsupport.protocol.core.wrapped.WrappedDecoder;
import com.github.shevchik.protocolsupport.protocol.core.wrapped.WrappedEncoder;
import com.github.shevchik.protocolsupport.protocol.core.wrapped.WrappedPrepender;
import com.github.shevchik.protocolsupport.protocol.core.wrapped.WrappedSplitter;

public class ChannelHandlers {

	public static final String INITIAL_DECODER = "initial_decoder";
	public static final String SPLITTER = "splitter";
	public static final String PREPENDER = "prepender";
	public static final String DECODER = "decoder";
	public static final String ENCODER = "encoder";
	public static final String NETWORK_MANAGER = "packet_handler";
	public static final String DECRYPT = "decrypt";

	public static WrappedDecoder getDecoder(ChannelPipeline pipeline) {
		return (WrappedDecoder) pipeline.get(DECODER);
	}

	public static WrappedEncoder getEncoder(ChannelPipeline pipeline) {
		return (WrappedEncoder) pipeline.get(ENCODER);
	}

	public static WrappedSplitter getSplitter(ChannelPipeline pipeline) {
		return (WrappedSplitter) pipeline.get(SPLITTER);
	}

	public static WrappedPrepender getPrepender(ChannelPipeline pipeline) {
		return (WrappedPrepender) pipeline.get(PREPENDER);
	}

}
