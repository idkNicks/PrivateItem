package net.starly.privateitem.listener;

import net.starly.privateitem.context.MessageType;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockPlaceListener extends PrivateItemListener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) return;

        ItemStack itemStack = event.getItemInHand();
        checkNbtTag(itemStack, player, MessageType.ERROR, "noPlacePrivateItem", event::setCancelled);
    }
}