package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleAnimation;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.UsedHand;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Animation extends ServerBoundMiddlePacket {

	public Animation(ConnectionImpl connection) {
		super(connection);
	}

	protected int action;
	protected long entityId;
	protected float paddleTime;
	protected float prevRightPaddle;
	protected float prevLeftPaddle;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		action = VarNumberSerializer.readSVarInt(clientdata);
		entityId = VarNumberSerializer.readVarLong(clientdata);
		if ((action & 0x80) != 0) {
			paddleTime = clientdata.readFloatLE();
		}
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		if ((action & 0x80) == 0) {
			return RecyclableSingletonList.create(MiddleAnimation.create(UsedHand.MAIN));
		} else {
			if ((action & 0x01) == 0) {
				cache.getMovementCache().setPELeftPaddleTurning(paddleTime != prevLeftPaddle);
				prevLeftPaddle = paddleTime;
			} else {
				cache.getMovementCache().setPERightPaddleTurning(paddleTime != prevRightPaddle);
				prevRightPaddle = paddleTime;
			}
		}
		return RecyclableEmptyList.get();
	}

}
