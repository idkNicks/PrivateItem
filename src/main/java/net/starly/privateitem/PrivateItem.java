package net.starly.privateitem;

import lombok.Getter;
import net.starly.privateitem.command.PrivateItemCommand;
import net.starly.privateitem.context.MessageContent;
import net.starly.privateitem.listener.*;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PrivateItem extends JavaPlugin {

    @Getter private static PrivateItem instance;

    @Override
    public void onLoad() { this.instance = this; }

    @Override
    public void onEnable() {
        if (!isPluginEnable("ST-Core")) {
            getServer().getLogger().warning("[" + getName() + "] ST-Core 플러그인이 적용되지 않았습니다! 플러그인을 비활성화합니다.");
            getServer().getLogger().warning("[" + getName() + "] 다운로드 링크 : http://starly.kr/");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        saveDefaultConfig();
        MessageContent.getMessageContent().initializing(getConfig());

        // COMMAND
        PrivateItemCommand privateItemCommand = new PrivateItemCommand();
        PluginCommand privateItem = getServer().getPluginCommand("privateitem");

        if (privateItem != null) {
            privateItem.setExecutor(privateItemCommand);
            privateItem.setTabCompleter(privateItemCommand);
        }

        // LISTENER
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDropItemListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryDragListener(), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);

    }

    private boolean isPluginEnable(String pluginName) {
        Plugin plugin = getServer().getPluginManager().getPlugin(pluginName);
        return plugin != null && plugin.isEnabled();
    }
}
