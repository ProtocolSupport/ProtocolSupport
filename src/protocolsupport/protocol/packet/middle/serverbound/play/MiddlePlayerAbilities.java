package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddlePlayerAbilities extends ServerBoundMiddlePacket {

	public MiddlePlayerAbilities(ConnectionImpl connection) {
		super(connection);
	}

	protected int flags;
	protected float flySpeed;
	protected float walkSpeed;

	@Override
	public RecyclableCollection<? extends IPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_ABILITIES);
		creator.writeByte(flags);
		creator.writeFloat(flySpeed);
		creator.writeFloat(walkSpeed);
		return RecyclableSingletonList.create(creator);
	}

}
