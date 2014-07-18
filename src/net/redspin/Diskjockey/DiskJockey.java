package net.redspin.Diskjockey;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.ChatColor;
 
public final class DiskJockey extends JavaPlugin {
	
	private static boolean toggle;
	
    public void onEnable() {
    	toggle = false;
    	DBwrangler.getDB();
        Bukkit.broadcastMessage("DiskJockey has been enabled!");
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
            	if (toggle){
            	broadcastNext();
            }
          }
        }, 16000L, 16000L);
    }


	@Override
	public void onDisable() {
		// TODO Insert logic to be performed when the plugin is disabled
	}
	
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("dj")){
			if (args.length == 0){
				sender.sendMessage("DiskJockey version 1.0 by suBDavis\nLearn more at http://rsmc.tk/dj.html");
			}
			else if (args.length == 1){
				if (args[0].equals("toggle")){
					toggle = !toggle;
					sender.sendMessage("Toggle set to :" + toggle);
				}
				if (args[0].equals("next")){
	            	 broadcastNext();
				}
			}
		}
		return true;
	}
	private void broadcastNext(){
		String[] elems = DBwrangler.getDB().getNext();
		Bukkit.broadcastMessage(ChatColor.BLUE + elems[0] + ChatColor.GRAY + " by " + ChatColor.BLUE + elems[1]);
		Bukkit.broadcastMessage(ChatColor.GRAY + elems[2]);
    	Bukkit.broadcastMessage(ChatColor.BLUE + "Submit tunes to DiskJockey @ " + ChatColor.AQUA + "http://rsmc.tk/dj.html");
	}

}
