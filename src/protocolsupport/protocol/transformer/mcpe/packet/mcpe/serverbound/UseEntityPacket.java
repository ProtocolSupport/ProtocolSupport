package protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity.EnumEntityUseAction;

import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketCreator;

public class UseEntityPacket implements ServerboundPEPacket {

	protected byte action;
	protected long targetId;

	@Override
	public int getId() {
		return PEPacketIDs.INTERACT_PACKET;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		action = buf.readByte();
		targetId = buf.readLong();
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_USE_ENTITY.get());
		creator.writeVarInt((int) targetId);
		creator.a(getAction(action));
		return Collections.singletonList(creator.create());
	}

	protected EnumEntityUseAction getAction(int actionId) {
		switch (action) {
			case 1: {
				return EnumEntityUseAction.ATTACK;
			}
			case 2: {
				return EnumEntityUseAction.INTERACT;
			}
			default: {
				return EnumEntityUseAction.ATTACK;
			}
		}
	}

}
