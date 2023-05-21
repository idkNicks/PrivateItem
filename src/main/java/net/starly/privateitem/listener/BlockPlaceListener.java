package net.starly.privateitem.listener;

import net.starly.core.jb.version.nms.tank.NmsItemStackUtil;
import net.starly.core.jb.version.nms.wrapper.ItemStackWrapper;
import net.starly.core.jb.version.nms.wrapper.NBTTagCompoundWrapper;
import net.starly.privateitem.context.MessageContent;
import net.starly.privateitem.context.MessageType;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;

        Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE) return;

        MessageContent content = MessageContent.getMessageContent();

        ItemStack itemStack = event.getItemInHand();

        if (itemStack == null) return;

        ItemStackWrapper itemStackWrapper = NmsItemStackUtil.getInstance().asNMSCopy(itemStack);
        NBTTagCompoundWrapper nbtTagCompoundWrapper = itemStackWrapper.getTag();

        if (nbtTagCompoundWrapper == null) return;

        String stringNumber = nbtTagCompoundWrapper.getString("stprivateitem");

        if (stringNumber != null && !stringNumber.isEmpty()) {
            content.getMessageAfterPrefix(MessageType.ERROR, "noPlacePrivateItem").ifPresent(message -> player.sendMessage(message));
            event.setCancelled(true);
        }
    }
}
