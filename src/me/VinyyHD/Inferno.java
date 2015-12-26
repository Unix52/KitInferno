package me.VinyyHD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Inferno
implements Listener
{
	  public Plugin plugin;
	  
	  public Inferno(Main plugin)
	  {
	    this.plugin = plugin;
	  }
	  
	  public boolean generateGlass = true;
	  public HashMap<String, Location> oldl = new HashMap<String, Location>();
	  public static HashMap<String, String> fighting = new HashMap<String, String>();
	  public HashMap<Integer, ArrayList<Location>> blocks = new HashMap<Integer, ArrayList<Location>>();
	  public HashMap<Player, Location> localizacao = new HashMap<Player, Location>();
	  public HashMap<Location, Block> bloco = new HashMap<Location, Block>();
	  public HashMap<Integer, String[]> players = new HashMap<Integer, String[]>();
	  public HashMap<String, Integer> tasks = new HashMap<String, Integer>();
	  public static List<Player> Infer = new ArrayList<Player>();
	  int nextID = 0;
	  public int id1;
	  public int id2;
	  ArrayList<String> tempo = new ArrayList<String>();
	  
	  @SuppressWarnings({ "deprecation", "static-access" })
	@EventHandler
	  public void OnInfernoKit(PlayerInteractEntityEvent event)
	  {
	    final Player p = event.getPlayer();
	    if ((event.getRightClicked() instanceof Player))
	    {
	    	if (this.Infer.contains(p)) {
	    		p.sendMessage("§6-> §c§oEspere antes de usar este kit");
	    	}
	    	if (!this.Infer.contains(p)) {
	      final Player r = (Player)event.getRightClicked();
	      if ((p.getItemInHand().getTypeId() == 113))
	      {
	        Location loc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY() + 70, p.getLocation().getBlockZ());
	        this.localizacao.put(p, loc);
	        this.localizacao.put(r, loc);
	        Location loc2 = new Location(p.getWorld(), p.getLocation().getBlockX() + 8, p.getLocation().getBlockY() + 73, p.getLocation().getBlockZ() + 8);
	        Location loc3 = new Location(p.getWorld(), p.getLocation().getBlockX() - 8, p.getLocation().getBlockY() + 73, p.getLocation().getBlockZ() - 8);
	        if ((fighting.containsKey(p.getName())) || (fighting.containsKey(r.getName())))
	        {
	          event.setCancelled(true);
	          p.sendMessage(ChatColor.RED + "Voce ja esta em combate!");
	          return;
	        }
	        Integer currentID = Integer.valueOf(this.nextID);
	        this.nextID += 1;
	        ArrayList<String> list = new ArrayList<String>();
	        list.add(p.getName());
	        list.add(r.getName());
	        this.players.put(currentID, (String[])list.toArray(new String[1]));
	        this.oldl.put(p.getName(), p.getLocation());
	        this.oldl.put(r.getName(), r.getLocation());
	        this.tempo.add(p.getName());
	        if (this.generateGlass)
	        {
	          List<Location> cuboid = new ArrayList<Location>();
	          cuboid.clear();
	          int bY;
	          for (int bX = -10; bX <= 10; bX++) {
	            for (int bZ = -10; bZ <= 10; bZ++) {
	              for (bY = -1; bY <= 10; bY++)
	              {
	                Block b = loc.clone().add(bX, bY, bZ).getBlock();
	                if (!b.isEmpty())
	                {
	                  event.setCancelled(true);
	                  p.sendMessage(ChatColor.RED + "Voce nao pode criar sua arena aqui");
	                  return;
	                }
	                if (bY == 10) {
	                  cuboid.add(loc.clone().add(bX, bY, bZ));
	                } else if (bY == -1) {
	                  cuboid.add(loc.clone().add(bX, bY, bZ));
	                } else if ((bX == -10) || (bZ == -10) || (bX == 10) || (bZ == 10)) {
	                  cuboid.add(loc.clone().add(bX, bY, bZ));
	                }
	              }
	            }
	          }
	          for (Location loc1 : cuboid)
	          {
	              if (new Random().nextBoolean())
	              {
	            	  loc1.getBlock().setData(DyeColor.RED.getData());
	            	  loc1.getBlock().setType(Material.STAINED_GLASS);
	    
	              }
	              else
	              {

	            	  loc1.getBlock().setType(Material.NETHERRACK);
	                    loc1.getBlock().getRelative(BlockFace.UP).setType(Material.FIRE);
	              }
	          }
	          loc2.setYaw(135.0F);
	          p.teleport(loc2);
	          loc3.setYaw(-45.0F);
	          r.teleport(loc3);
	          p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 0));
	          r.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 300, 0));
	          this.Infer.add(p);
	          fighting.put(p.getName(), r.getName());
	          fighting.put(r.getName(), p.getName());
	          Main.Infernoo.add(p.getName());
	          Main.Infernoo.add(r.getName());
	        }
	          this.id2 = Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
	          {
	            public void run()
	            {
	              if ((Inferno.fighting.containsKey(p.getName())) && (((String)Inferno.fighting.get(p.getName())).equalsIgnoreCase(r.getName())) && (Inferno.fighting.containsKey(r.getName())) && (((String)Inferno.fighting.get(r.getName())).equalsIgnoreCase(p.getName())))
	              {
	              }
	            }
	          }, 1800L);
	          this.id1 = Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
	          {
	            public void run()
	            {
	              if ((Inferno.fighting.containsKey(p.getName())) && (((String)Inferno.fighting.get(p.getName())).equalsIgnoreCase(r.getName())) && (Inferno.fighting.containsKey(r.getName())) && (((String)Inferno.fighting.get(r.getName())).equalsIgnoreCase(p.getName())))
	              {
	                Inferno.fighting.remove(p.getName());
	                Inferno.fighting.remove(r.getName());
	                Main.Infernoo.remove(p.getName());
	                Main.Infernoo.remove(r.getName());
	                p.teleport((Location)Inferno.this.oldl.get(p.getName()));
	                r.teleport((Location)Inferno.this.oldl.get(r.getName()));
	                Inferno.this.oldl.remove(p.getName());
	                Inferno.this.oldl.remove(r.getName());
	                p.sendMessage(ChatColor.RED + "Nao houve nenhum vencedor, voce foi teleportado para o seu lugar de origem!");
	                r.sendMessage(ChatColor.RED + "Nao houve nenhum vencedor, voce foi teleportado para o seu lugar de origem!");
	                Location loc = (Location)Inferno.this.localizacao.get(p);
	                List<Location> cuboid = new ArrayList<Location>();
	                int bY;
	                for (int bX = -10; bX <= 10; bX++) {
	                  for (int bZ = -10; bZ <= 10; bZ++) {
	                    for (bY = -1; bY <= 10; bY++) {
	                      if (bY == 10) {
	                        cuboid.add(loc.clone().add(bX, bY, bZ));
	                      } else if (bY == -1) {
	                        cuboid.add(loc.clone().add(bX, bY, bZ));
	                      } else if ((bX == -10) || (bZ == -10) || (bX == 10) || (bZ == 10)) {
	                        cuboid.add(loc.clone().add(bX, bY, bZ));
	                      }
	                    }
	                  }
	                }
	                for (Location loc1 : cuboid)
	                {
	                  loc1.getBlock().setType(Material.AIR);
	                  ((Block)Inferno.this.bloco.get(loc1)).setType(Material.AIR);
	                }
	              }
	            }
	          }, 2000L);
	       
	      }
	    }
        this.id1 = Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
        {
          public void run()
          {
           if (Inferno.this.Infer.contains(p))
           {
        	   Inferno.this.Infer.remove(p);
        	   p.sendMessage("§6-> §c§o Você já pode usar seu Inferno.");
        	   
           }
        	   
          }
        }, 1200L);
	    }
	  }
	    
	  
	  @SuppressWarnings("deprecation")
	@EventHandler
	  public void onPlayerInteractGlad(PlayerInteractEvent e)
	  {
	    Player p = e.getPlayer();
	    if ((p.getItemInHand().getTypeId() == 113))
	    {
	      e.setCancelled(true);
	      p.updateInventory();
	      return;
	    }
	  }
	  
	  
	  @EventHandler(priority=EventPriority.MONITOR)
	  public void onPlyaerInteract(final PlayerInteractEvent e)
	  {
	    if ((e.getAction() == Action.LEFT_CLICK_BLOCK) && (e.getClickedBlock().getType() == Material.STAINED_GLASS) && (e.getClickedBlock().getData() == DyeColor.RED.getData()) && (e.getPlayer().getGameMode() != GameMode.CREATIVE) && (fighting.containsKey(e.getPlayer().getName())))
	    {
	      e.setCancelled(true);
	      e.getClickedBlock().setType(Material.BEDROCK);
	      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
	      {
	        public void run()
	        {
	          if (Inferno.fighting.containsKey(e.getPlayer().getName())) {	            
	            e.getClickedBlock().setType(Material.NETHERRACK);
	            e.getClickedBlock().getRelative(BlockFace.UP).setType(Material.FIRE);
	            
	          }
	        }
	      }, 30L);
	    }
	  }
	  
	  @EventHandler(priority=EventPriority.MONITOR)
	  public void onBlockBreak(final BlockBreakEvent e)
	  {
	    if ((e.getBlock().getType() == Material.STAINED_GLASS) &&  (e.getPlayer().getGameMode() != GameMode.CREATIVE) && (fighting.containsKey(e.getPlayer().getName())))
	    {
	      e.setCancelled(true);
	      e.getBlock().setType(Material.BEDROCK);
	      Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
	      {
	        public void run()
	        {
	          if ((e.getPlayer().getGameMode() != GameMode.CREATIVE) && (Inferno.fighting.containsKey(e.getPlayer().getName()))) {
	            	
	            e.getBlock().setType(Material.NETHERRACK);	   
	            e.getBlock().getRelative(BlockFace.UP).setType(Material.FIRE);
	            
	          }
	        }
	      }, 30L);
	    }
	  }
	  
	  @EventHandler(priority=EventPriority.MONITOR)
	  public void onPlyaerInteract2(final PlayerInteractEvent e)
	  {
	    if ((e.getAction() == Action.LEFT_CLICK_BLOCK) && (e.getClickedBlock().getType() == Material.NETHERRACK) && (e.getPlayer().getGameMode() != GameMode.CREATIVE) && (fighting.containsKey(e.getPlayer().getName())))
	    {
	      e.setCancelled(true);
	      e.getClickedBlock().setType(Material.BEDROCK);
	      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
	      {
	        public void run()
	        {
	          if (Inferno.fighting.containsKey(e.getPlayer().getName())) {
	            
	        	  e.getClickedBlock().setType(Material.STAINED_GLASS);
	        	  e.getClickedBlock().setData(DyeColor.RED.getData());
	          }
	        }
	      }, 30L);
	    }
	  }
	  
	  @EventHandler(priority=EventPriority.MONITOR)
	  public void onBlockBreak2(final BlockBreakEvent e)
	  {
	    if ((e.getBlock().getType() == Material.NETHERRACK) &&  (e.getPlayer().getGameMode() != GameMode.CREATIVE) && (fighting.containsKey(e.getPlayer().getName())))
	    {
	      e.setCancelled(true);
	      e.getBlock().setType(Material.BEDROCK);
	      Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
	      {
	        public void run()
	        {
	          if ((e.getPlayer().getGameMode() != GameMode.CREATIVE) && (Inferno.fighting.containsKey(e.getPlayer().getName()))) {
	        	  e.getBlock().setType(Material.STAINED_GLASS);
	        	  e.getBlock().setData(DyeColor.RED.getData());
	            
	          }
	        }
	      }, 30L);
	    }
	  }
	  
	  @EventHandler(priority=EventPriority.HIGHEST)
	  public void onPlayerLeft(PlayerQuitEvent e)
	  {
	    Player p = e.getPlayer();
	    if (fighting.containsKey(p.getName()))
	    {
	      Player t = Bukkit.getServer().getPlayer((String)fighting.get(p.getName()));
	      fighting.remove(t.getName());
	      fighting.remove(p.getName());
	      Main.Infernoo.remove(p.getName());
	      Main.Infernoo.remove(t.getName());
	      Location old = (Location)this.oldl.get(t.getName());
	      t.teleport(old);
	      t.sendMessage(ChatColor.RED + "O jogador " + p.getName() + " deslogou-se!");
	      Bukkit.getScheduler().cancelTask(this.id1);
	      Bukkit.getScheduler().cancelTask(this.id2);
	      t.removePotionEffect(PotionEffectType.WITHER);
	      Location loc = (Location)this.localizacao.get(p);
	      List<Location> cuboid = new ArrayList<Location>();
	      int bY;
	      for (int bX = -10; bX <= 10; bX++) {
	        for (int bZ = -10; bZ <= 10; bZ++) {
	          for (bY = -1; bY <= 10; bY++) {
	            if (bY == 10) {
	              cuboid.add(loc.clone().add(bX, bY, bZ));
	            } else if (bY == -1) {
	              cuboid.add(loc.clone().add(bX, bY, bZ));
	            } else if ((bX == -10) || (bZ == -10) || (bX == 10) || (bZ == 10)) {
	              cuboid.add(loc.clone().add(bX, bY, bZ));
	            }
	          }
	        }
	      }
	      for (Location loc1 : cuboid)
	      {
	        loc1.getBlock().setType(Material.AIR);
	        ((Block)this.bloco.get(loc1)).setType(Material.AIR);
	      }
	      for (Location loc1 : cuboid)
	      {
	        loc1.getBlock().setType(Material.AIR);
	        ((Block)this.bloco.get(loc1)).setType(Material.AIR);
	      }
	      for (Location loc1 : cuboid)
	      {
	        loc1.getBlock().setType(Material.AIR);
	        ((Block)this.bloco.get(loc1)).setType(Material.AIR);
	      }
	    }
	  }
	  
	  @EventHandler(priority=EventPriority.HIGHEST)
	  public void onDeathInferno(PlayerDeathEvent e)
	  {
	    Player p = e.getEntity();
	    if (fighting.containsKey(p.getName()))
	    {
	      Player k = Bukkit.getServer().getPlayer((String)fighting.get(p.getName()));
	      Location old = (Location)this.oldl.get(p.getName());
	      k.teleport(old);
	      k.sendMessage(ChatColor.GREEN + "Voce ganhou a batalha contra " + p.getName() + ChatColor.GREEN + "!");
	      Bukkit.getScheduler().cancelTask(this.id1);
	      Bukkit.getScheduler().cancelTask(this.id2);
	      k.removePotionEffect(PotionEffectType.WITHER);
	      k.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 10));
	      fighting.remove(k.getName());
	      fighting.remove(p.getName());
	      Main.Infernoo.remove(p.getName());
	      Main.Infernoo.remove(k.getName());
	      Location loc = (Location)this.localizacao.get(p);
	      List<Location> cuboid = new ArrayList<Location>();
	      cuboid.clear();
	      int bY;
	      for (int bX = -10; bX <= 10; bX++) {
	        for (int bZ = -10; bZ <= 10; bZ++) {
	          for (bY = -1; bY <= 10; bY++) {
	            if (bY == 10) {
	              cuboid.add(loc.clone().add(bX, bY, bZ));
	            } else if (bY == -1) {
	              cuboid.add(loc.clone().add(bX, bY, bZ));
	            } else if ((bX == -10) || (bZ == -10) || (bX == 10) || (bZ == 10)) {
	              cuboid.add(loc.clone().add(bX, bY, bZ));
	            }
	          }
	        }
	      }
	      for (Location loc1 : cuboid)
	      {
	        loc1.getBlock().setType(Material.AIR);
	        if (this.bloco.containsKey(loc1)) {
	          ((Block)this.bloco.get(loc1)).setType(Material.AIR);
	        }
	      }
	      return;
	    }
	  }

}