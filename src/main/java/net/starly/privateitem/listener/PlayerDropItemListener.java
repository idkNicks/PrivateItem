package net.starly.privateitem.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDropItemListener extends PrivateItemListener {

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) return;

        ItemStack itemStack = event.getItemDrop().getItemStack();
        checkNbtTag(itemStack, player, "noDropPrivateItem", event::setCancelled);
    }
}