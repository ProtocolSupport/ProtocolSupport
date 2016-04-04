package protocolsupport.protocol.transformer.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.AddEntityPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleSpawnObject;
import protocolsupport.protocol.typeremapper.id.RemappingTable;
import protocolsupport.protocol.typeremapper.watchedentity.WatchedDataRemapper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnObject extends MiddleSpawnObject<RecyclableCollection<? extends ClientboundPEPacket>> {

	private static final RemappingTable objectRemapper = new RemappingTable(256) {{
		for (int i = 0; i < 256; i++) {
			setRemap(i, -1);
		}
		//Correct entity ids
		//boat
		setRemap(1, 90);
		//minecart
		setRemap(10, 84);
		//tnt
		setRemap(50, 65);
		//arrow
		setRemap(60, 80);
		//snowball
		setRemap(61, 81);
		//egg
		setRemap(62, 82);
		//fireball
		setRemap(63, 85);
		setRemap(64, 85);
		setRemap(66, 85);
		//falling
		setRemap(70, 66);
		//fishing float
		setRemap(90, 77);
		//potion
		setRemap(73, 86);
		//exp potion
		setRemap(75, 68);
	}};

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		if (type == 2) {
			storage.getPEStorage().addItemInfo(entityId, (float) x, (float) y, (float) z, motX, motY, motZ);
			return RecyclableEmptyList.get();
		}
		int remappedId = objectRemapper.getRemap(type);
		if (remappedId == -1) {
			return RecyclableEmptyList.get();
		} else {
			return RecyclableSingletonList.create(new AddEntityPacket(
				entityId, remappedId,
				(float) x, (float) y, (float) z,
				yaw / 256.0F * 360.0F, pitch / 256.0F * 360.0F,
				motX / 8000.0F, motY / 8000F, motZ / 8000.0F,
				WatchedDataRemapper.transform(null, null, null)
			));
		}
	}

}
