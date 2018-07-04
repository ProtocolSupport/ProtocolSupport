package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddlePlayerAbilities extends ServerBoundMiddlePacket {

	protected static final int flagOffsetIsFlying = 0x2;
	protected static final int flagOffsetCanFly = 0x4;

	protected int flags;
	protected float flySpeed;
	protected float walkSpeed;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_ABILITIES);
		creator.writeByte(flags);
		creator.writeFloat(flySpeed);
		creator.writeFloat(walkSpeed);
		return RecyclableSingletonList.create(creator);
	}

}
