package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4_5_6;

import org.bukkit.inventory.MainHand;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleClientSettings;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV6;
import protocolsupport.protocol.utils.EnumConstantLookup;

public class ClientSettings extends MiddleClientSettings implements
IServerboundMiddlePacketV4,
IServerboundMiddlePacketV5,
IServerboundMiddlePacketV6 {

	public ClientSettings(IMiddlePacketInit init) {
		super(init);
		mainHand = MainHand.RIGHT;
		disableTextFilter = true;
		list = true;
	}

	@Override
	protected void read(ByteBuf clientdata) {
		locale = StringCodec.readShortUTF16BEString(clientdata, 16);
		viewDist = MiscDataCodec.readByteEnum(clientdata, ViewDistanceType.CONSTANT_LOOKUP).getDistance();
		int chatState = clientdata.readByte();
		chatMode = ChatMode.CONSTANT_LOOKUP.getByOrdinal(chatState & 7);
		chatColors = (chatState & 8) == 8;
		clientdata.readByte();
		clientdata.readBoolean();
		skinFlags = 255;
	}

	protected enum ViewDistanceType {

		FAR(16),
		NORMAL(8),
		SHORT(4),
		TINY(2);

		public static final EnumConstantLookup<ViewDistanceType> CONSTANT_LOOKUP = new EnumConstantLookup<>(ViewDistanceType.class);

		private final int distance;

		private ViewDistanceType(int distance) {
			this.distance = distance;
		}

		public int getDistance() {
			return distance;
		}

	}

}
