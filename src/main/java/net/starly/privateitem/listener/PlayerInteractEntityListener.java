package net.starly.privateitem.listener;

import net.starly.privateitem.context.MessageType;
import org.bukkit.GameMode;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractEntityListener extends PrivateItemListener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof ItemFrame)) return;
        if (!(event.getPlayer() instanceof Player)) return;
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) return;

        ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();
        checkNbtTag(itemStack, player, MessageType.ERROR, "noItemFrame", event::setCancelled);
    }
}

