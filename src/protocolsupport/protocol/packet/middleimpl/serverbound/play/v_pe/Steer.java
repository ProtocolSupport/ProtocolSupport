package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSteerBoat;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSteerVehicle;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.utils.minecraftdata.PocketData.PocketEntityData.PocketRiderInfo;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Steer extends ServerBoundMiddlePacket {

	protected float sideForce;
	protected float forwardForce;
	protected boolean rightPaddleTurning;
	protected boolean leftPaddleTurning;
	
	@Override
	public void readFromClientData(ByteBuf clientdata) {
		sideForce = MiscSerializer.readLFloat(clientdata);
		forwardForce = MiscSerializer.readLFloat(clientdata);
		rightPaddleTurning = clientdata.readBoolean();
		leftPaddleTurning = clientdata.readBoolean();
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		System.out.println("STEEEEER " + sideForce + " - " + forwardForce + " - " + rightPaddleTurning + " - " + leftPaddleTurning);
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		PocketRiderInfo rider = cache.getWatchedSelf().getDataCache().riderInfo;
		if (rider != null && rider.getVehicle() != null) {
			if (rider.getVehicle().getType() == NetworkEntityType.BOAT) {
				//packets.add(MiddleSteerBoat.create(rightPaddleTurning, leftPaddleTurning));
				packets.add(MiddleSteerVehicle.create(sideForce, forwardForce, 0));
			} else if (rider.getVehicle().getType() == NetworkEntityType.MINECART) {
				packets.add(MiddleSteerVehicle.create(sideForce, forwardForce, 0));
			}
			
		}
		return packets;
	}

}
