package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChangeDimension;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.types.Environment;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ChangeDimension extends MiddleChangeDimension {

	public ChangeDimension(ConnectionImpl connection) {
		super(connection);
	}

	boolean hadSkyLightInOldDimension;

	@Override
	public boolean postFromServerRead() {
		hadSkyLightInOldDimension = cache.getAttributesCache().hasSkyLightInCurrentDimension();
		return super.postFromServerRead();
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		if (!hadSkyLightInOldDimension && (dimension != Environment.OVERWORLD)) {
			RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
			packets.add(create(Environment.OVERWORLD));
			packets.add(create(remapDimension(dimension)));
			return packets;
		} else {
			return RecyclableSingletonList.create(create(dimension));
		}
	}

	public static Environment remapDimension(Environment dimension) {
		return dimension == Environment.THE_END ? Environment.NETHER : dimension;
	}

	protected static ClientBoundPacketData create(Environment dimension) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_RESPAWN);
		serializer.writeByte(dimension.getId());
		return serializer;
	}

}
