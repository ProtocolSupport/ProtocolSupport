package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17r1_17r2;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleInventoryData;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;
import protocolsupport.protocol.storage.netcache.window.WindowEnchantmentCache;
import protocolsupport.protocol.storage.netcache.window.WindowFurnaceCache;

public class InventoryData extends MiddleInventoryData implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7,
IClientboundMiddlePacketV8,
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2,
IClientboundMiddlePacketV10,
IClientboundMiddlePacketV11,
IClientboundMiddlePacketV12r1,
IClientboundMiddlePacketV12r2,
IClientboundMiddlePacketV13,
IClientboundMiddlePacketV14r1,
IClientboundMiddlePacketV14r2,
IClientboundMiddlePacketV15,
IClientboundMiddlePacketV16r1,
IClientboundMiddlePacketV16r2,
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2 {

	protected static final int ENCHANTMENT_TYPE_ID_TOP = 4;
	protected static final int ENCHANTMENT_TYPE_ID_MIDDLE = 5;
	protected static final int ENCHANTMENT_TYPE_ID_BOTTOM = 6;
	protected static final int ENCHANTMENT_TYPE_LEVEL_TOP = 7;
	protected static final int ENCHANTMENT_TYPE_LEVEL_MIDDLE = 8;
	protected static final int EHCNAHTMENT_TYPE_LEVEL_BOTTOM = 9;

	protected static final int FURNACE_TYPE_FUEL_TIME_CURRENT = 0;
	protected static final int FURNACE_TYPE_FUEL_TIME_MAX = 1;
	protected static final int FURNACE_TYPE_PROGRESS_CURRENT = 2;
	protected static final int FURNACE_TYPE_PROGRESS_MAX = 3;

	public InventoryData(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		switch (windowCache.getOpenedWindowType()) {
			case ENCHANTMENT: {
				if (version.isBetween(ProtocolVersion.MINECRAFT_1_9_4, ProtocolVersion.MINECRAFT_1_8)) {
					WindowEnchantmentCache enchantmentCache = (WindowEnchantmentCache) windowCache.getOpenedWindowMetadata();
					if ((type >= ENCHANTMENT_TYPE_ID_TOP) && (type <= ENCHANTMENT_TYPE_ID_BOTTOM)) {
						io.writeClientbound(create(windowId, type, enchantmentCache.updateEnchantmentId(type - ENCHANTMENT_TYPE_ID_TOP, value)));
						return;
					} else if ((type >= ENCHANTMENT_TYPE_LEVEL_TOP) && (type <= EHCNAHTMENT_TYPE_LEVEL_BOTTOM)) {
						io.writeClientbound(create(windowId, type - 3, enchantmentCache.updateEnchantmentLevel(type - ENCHANTMENT_TYPE_LEVEL_TOP, value)));
						return;
					}
				}
				break;
			}
			case FURNACE: {
				if (version.isBefore(ProtocolVersion.MINECRAFT_1_8)) {
					WindowFurnaceCache furnaceCache = (WindowFurnaceCache) windowCache.getOpenedWindowMetadata();
					switch (type) {
						case FURNACE_TYPE_FUEL_TIME_CURRENT: {
							io.writeClientbound(create(windowId, 1, furnaceCache.scaleCurrentFuelBurnTime(value)));
							return;
						}
						case FURNACE_TYPE_FUEL_TIME_MAX: {
							furnaceCache.setMaxFuelBurnTime(value);
							return;
						}
						case FURNACE_TYPE_PROGRESS_CURRENT: {
							io.writeClientbound(create(windowId, 0, furnaceCache.scaleCurrentCurrentProgress(value)));
							return;
						}
						case FURNACE_TYPE_PROGRESS_MAX: {
							furnaceCache.setMaxProgress(value);
							return;
						}
					}
				}
				break;
			}
			default: {
				break;
			}
		}
		io.writeClientbound(create(windowId, type, value));
	}

	protected static ClientBoundPacketData create(byte windowId, int type, int value) {
		ClientBoundPacketData windowdata = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WINDOW_DATA);
		windowdata.writeByte(windowId);
		windowdata.writeShort(type);
		windowdata.writeShort(value);
		return windowdata;
	}

}
