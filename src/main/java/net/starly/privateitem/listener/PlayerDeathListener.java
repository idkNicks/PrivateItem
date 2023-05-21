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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerDeathListener implements Listener {

    private final JavaPlugin plugin;

    public PlayerDeathListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (player.getGameMode() == GameMode.CREATIVE) return;

        MessageContent content = MessageContent.getMessageContent();

        ItemStack[] inventory = player.getInventory().getContents();

        for (ItemStack itemStack : inventory) {
            if (itemStack == null) continue;

            ItemStackWrapper itemStackWrapper = NmsItemStackUtil.getInstance().asNMSCopy(itemStack);
            NBTTagCompoundWrapper nbtTagCompoundWrapper = itemStackWrapper.getTag();
            if (nbtTagCompoundWrapper == null) continue;

            String stringNumber = nbtTagCompoundWrapper.getString("stprivateitem");
            if (stringNumber == null || stringNumber.isEmpty()) continue;

            content.getMessageAfterPrefix(MessageType.NORMAL, "restorePrivateItem").ifPresent(message -> player.sendMessage(message));
            event.getDrops().remove(itemStack);
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> player.getInventory().addItem(itemStack), 1L);
        }
    }
}
