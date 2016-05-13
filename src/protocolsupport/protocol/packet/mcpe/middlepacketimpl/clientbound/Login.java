package protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import org.bukkit.GameMode;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound.AdventureSettingsPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound.SetDifficultyPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound.SetRecipesPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound.SetSpawnPosition;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound.StartGamePacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleLogin;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Login extends MiddleLogin<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public boolean needsPlayer() {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		RecyclableArrayList<ClientboundPEPacket> list = RecyclableArrayList.create();
		list.add(
			new StartGamePacket(
				player.getWorld().getEnvironment().getId(),
				player.getGameMode().getValue(),
				player.getEntityId(),
				player.getWorld().getSpawnLocation(),
				player.getLocation()
			)
		);
		list.add(new SetSpawnPosition(player.getLocation()));
		list.add(new SetDifficultyPacket(player.getWorld().getDifficulty().ordinal()));
		list.add(new AdventureSettingsPacket(player.getGameMode() == GameMode.CREATIVE));
		//TODO: Readd after fixing remap table
		//list.add(new SetRecipesPacket());
		return list;
	}

}
