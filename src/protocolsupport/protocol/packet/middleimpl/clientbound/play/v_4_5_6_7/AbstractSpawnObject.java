package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnObject;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.utils.types.networkentity.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableCollection;

/**
 * Created by JunHyeong Lim on 2018-05-12
 */
public abstract class AbstractSpawnObject extends MiddleSpawnObject {

	private void adjustLocation(int data) {
		switch (data) {
			case 0: {
				z -= 32;
				yaw = 128;
				break;
			}
			case 1: {
				x += 32;
				yaw = 64;
				break;
			}
			case 2: {
				z += 32;
				yaw = 0;
				break;
			}
			case 3: {
				x -= 32;
				yaw = 192;
				break;
			}
		}
	}

	private void adjustLocation() {
		ProtocolVersion version = connection.getVersion();
		NetworkEntityType type = IdRemapper.ENTITY.getTable(version).getRemap(entity.getType());
		switch (type) {
			case ITEM_FRAME: {
				adjustLocation(objectdata);
				break;
			}
			case FALLING_OBJECT: {
				int id = IdRemapper.BLOCK.getTable(version).getRemap((objectdata & 4095) << 4) >> 4;
				int data = (objectdata >> 12) & 0xF;
				objectdata = (id | (data << 16));
				y += 16;
				break;
			}
			case TNT: {
				y += 16;
				break;
			}
			case ARROW: {
				objectdata--;
				break;
			}
			case MINECART: {
				if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_6_1)) {
					y += 16;
				}
				break;
			}
			default: {
				break;
			}
		}
	}

	public abstract RecyclableCollection<ClientBoundPacketData> toData0();

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		x *= 32;
		y *= 32;
		z *= 32;
		adjustLocation();
		return toData0();
	}
}
