package me.VinyyHD;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class Main
extends JavaPlugin
implements Listener

{
	  public static ArrayList<String> Infernoo = new ArrayList<String>();
	  public void onEnable()
	  {
		getServer().getConsoleSender().sendMessage("§bInfernor §aBy:VinyyHD §bAnd §4Adrian");
	    Bukkit.getServer().getPluginManager().registerEvents(new Inferno(this), this);
		
	  }

public void onDisable() {}




}