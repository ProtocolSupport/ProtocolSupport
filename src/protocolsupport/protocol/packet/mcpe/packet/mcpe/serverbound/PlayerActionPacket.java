package protocolsupport.protocol.packet.mcpe.packet.mcpe.serverbound;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_9_R2.BlockPosition;
import net.minecraft.server.v1_9_R2.Packet;
import net.minecraft.server.v1_9_R2.PacketPlayInClientCommand;
import net.minecraft.server.v1_9_R2.PacketPlayInClientCommand.EnumClientCommand;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ServerboundPEPacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.protocol.storage.SharedStorage;

public class PlayerActionPacket implements ServerboundPEPacket {

	private final static int START_BREAK = 0;
	private final static int CANCEL_BREAK = 1;
	private final static int CONSUME_ITEM = 5;
	private final static int WAKE_UP = 6;
	private final static int RESPAWN = 7;
	private final static int START_SPRINT = 9;
	private final static int STOP_SPRINT = 10;
	private final static int START_SNEAK = 11;
	private final static int STOP_SNEAK = 12;

	protected long id;
	protected int action;
	protected int x;
	protected int y;
	protected int z;
	protected int face;

	@Override
	public int getId() {
		return PEPacketIDs.PLAYER_ACTION_PACKET;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		id = buf.readLong();
		action = buf.readInt();
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		face = buf.readInt();
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom(SharedStorage storage) throws Exception {
		switch (action) {
			case START_BREAK:
			case CANCEL_BREAK:
			case CONSUME_ITEM: {
				PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_BLOCK_DIG.get());
				creator.writeByte(action);
				creator.a(new BlockPosition(x, y, z));
				creator.writeByte(1);
				return Collections.singletonList(creator.create());
			}
			case RESPAWN: {
				//TODO: migrate to PacketCreator
				return Collections.singletonList(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
			}
			case WAKE_UP: {
				return getEntityActionPacket(2);
			}
			case START_SPRINT: {
				return getEntityActionPacket(3);
			}
			case STOP_SPRINT: {
				return getEntityActionPacket(4);
			}
			case START_SNEAK: {
				return getEntityActionPacket(0);
			}
			case STOP_SNEAK: {
				return getEntityActionPacket(1);
			}
			default: {
				return Collections.emptyList();
			}
		}
	}

	private static List<? extends Packet<?>> getEntityActionPacket(int action) throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_ENTITY_ACTION.get());
		creator.writeVarInt(0);
		creator.writeVarInt(action);
		creator.writeVarInt(0);
		return Collections.singletonList(creator.create());
	}

}
