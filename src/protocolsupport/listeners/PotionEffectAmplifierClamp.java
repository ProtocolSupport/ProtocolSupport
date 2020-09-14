package protocolsupport.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffect;

public class PotionEffectAmplifierClamp implements Listener {

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPotionEffectAdd(EntityPotionEffectEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getEntity();
		PotionEffect effect = event.getNewEffect();
		if (effect != null) {
			int amplifierByte = (byte) effect.getAmplifier();
			if (effect.getAmplifier() != amplifierByte) {
				event.setCancelled(true);
				player.addPotionEffect(new PotionEffect(
					effect.getType(), effect.getDuration(), amplifierByte,
					effect.isAmbient(), effect.hasParticles(), effect.hasIcon()
				));
			}
		}
	}

}
