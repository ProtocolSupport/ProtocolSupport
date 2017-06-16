package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import io.netty.handler.codec.EncoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTitle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Title extends MiddleTitle {

	private static final int HIDE = 0;
	private static final int RESET = 1;
	private static final int SET_TITLE = 2;
	private static final int SET_SUBTITLE = 3;
	private static final int SET_ACTION_BAR = 4;
	private static final int SET_TIMINGS = 5;

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		switch (this.action) {
			case HIDE: {
				return RecyclableSingletonList.create(create(version, HIDE, "", 0, 0, 0));
			}
			case RESET: {
				return RecyclableSingletonList.create(create(version, RESET, "", 0, 0, 0));
			}
			case SET_TITLE: {
				return RecyclableSingletonList.create(create(version, SET_TITLE, message.toLegacyText(), 0, 0, 0));
			}
			case SET_SUBTITLE: {
				return RecyclableSingletonList.create(create(version, SET_SUBTITLE, message.toLegacyText(), 0, 0, 0));
			}
			case SET_ACTION_BAR: {
				return RecyclableSingletonList.create(create(version, SET_ACTION_BAR, message.toLegacyText(), 0, 0, 0));
			}
			case SET_TIMES: {
				return RecyclableSingletonList.create(create(version, SET_TIMINGS, "", fadeIn, stay, fadeOut));
			}
			default: {
				throw new EncoderException("Should not reach here");
			}
		}
	}

	private static ClientBoundPacketData create(ProtocolVersion version, int action, String text, int fadeIn, int stay, int fadeOut) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SET_TITLE, version);
		VarNumberSerializer.writeSVarInt(serializer, action);
		StringSerializer.writeString(serializer, version, text);
		VarNumberSerializer.writeSVarInt(serializer, fadeIn);
		VarNumberSerializer.writeSVarInt(serializer, stay);
		VarNumberSerializer.writeSVarInt(serializer, fadeOut);
		return serializer;
	}

}