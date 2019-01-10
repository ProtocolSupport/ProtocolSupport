package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.CollectionsUtils;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public abstract class MiddleEntityAnimation extends ClientBoundMiddlePacket {

	public MiddleEntityAnimation(ConnectionImpl connection) {
		super(connection);
	}

	protected int entityId;
	protected Animation animation;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		entityId = VarNumberSerializer.readVarInt(serverdata);
		animation = Animation.BY_ID.get(serverdata.readUnsignedByte());
	}

	protected static enum Animation {
		SWING_ARM(0), WAKE_UP(2), EAT(3), CRIT(4), MAGIC_CRIT(5);
		public static final ArrayMap<Animation> BY_ID = CollectionsUtils.makeEnumMappingArrayMap(Animation.class, Animation::getId);
		protected int id;
		Animation(int id) {
			this.id = id;
		}
		public int getId() {
			return id;
		}
	}

}
