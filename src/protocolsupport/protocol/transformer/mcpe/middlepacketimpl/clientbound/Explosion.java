package protocolsupport.protocol.transformer.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.EntityVelocityPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.ExplosionPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleExplosion;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Explosion extends MiddleExplosion<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		RecyclableArrayList<ClientboundPEPacket> list = RecyclableArrayList.create();
		ExplosionPacket.AffectedPosition[] positions = new ExplosionPacket.AffectedPosition[blocks.length];
		for (int i = 0; i < blocks.length; i++) {
			AffectedBlock ablock = blocks[i];
			positions[i] = new ExplosionPacket.AffectedPosition((byte) ablock.offX, (byte) ablock.offY, (byte) ablock.offZ);
		}
		list.add(new ExplosionPacket(x, y, z, radius, positions));
		list.add(new EntityVelocityPacket(storage.getWatchedSelfPlayer().getId(), pMotX, pMotY, pMotZ));
		return list;
	}

}
