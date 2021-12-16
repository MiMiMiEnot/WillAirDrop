package me.enot.willairdrop;

import me.enot.willairdrop.commands.AirDropCommand;
import me.enot.willairdrop.configs.Settings;
import me.enot.willairdrop.configs.language.Language;
import me.enot.willairdrop.events.DropEvents;
import me.enot.willairdrop.events.utils.Placeholder;
import me.enot.willairdrop.logic.utils.AirDropLogick;
import me.enot.willairdrop.logic.utils.Saver;
import me.enot.willairdrop.serializer.Serialize;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class WillAirDrop extends JavaPlugin {

    public static Plugin getPlugin(){
        return WillAirDrop.getPlugin(WillAirDrop.class);
    }


    @Override
    public void onEnable() {

        PluginManager pm = Bukkit.getPluginManager();
        if(!pm.isPluginEnabled("HolographicDisplays") || !pm.isPluginEnabled("PlaceholderAPI")) {
            Bukkit.getConsoleSender().sendMessage("HolographicDisplays или PlaceholderAPI не найден");
            this.setEnabled(false);
            return;
        }
        Settings.reload();
        Language.reload();

        Serialize.load();
        new Placeholder(this).register();

        pm.registerEvents(new DropEvents(), this);

        getCommand("airdrop").setExecutor(new AirDropCommand());

        AirDropLogick.formatedTimeList();
        if (Settings.debug()) AirDropLogick.getList().forEach(string -> Bukkit.getConsoleSender().sendMessage(string));
        AirDropLogick.startTimer();

        Saver.load();
     }

    @Override
    public void onDisable() {
        Saver.save();
    }
}
