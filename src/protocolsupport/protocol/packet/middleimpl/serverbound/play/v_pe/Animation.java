package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleAnimation;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Animation extends ServerBoundMiddlePacket {

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
			paddleTime = MiscSerializer.readLFloat(clientdata);
		}
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		System.out.println("ANIMATE - " + Integer.toBinaryString(action) + " eID: " + entityId + "paddleTime: " + paddleTime);
		if ((action & 0x80) == 0) {
			return RecyclableSingletonList.create(MiddleAnimation.create(0));
		} else {
			if ((action & 0x01) == 0) {
				cache.setRightPaddleTurning(paddleTime != prevRightPaddle);
				prevRightPaddle = paddleTime;
			} else {
				cache.setRightPaddleTurning(paddleTime != prevLeftPaddle);
				prevLeftPaddle = paddleTime;
			}
		}
		return RecyclableEmptyList.get();
	}

}
