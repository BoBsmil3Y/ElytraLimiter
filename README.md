# ElytraLimiter
Allow you to limit the amount of elytra loot in the end for every player.


## Description
This lightweight plugin fixes a limit to all players for collecting to much elytra through item frame. The  limit  is the same for every  player.


## How it work ?

For now, this plugin will increase their amount of elytra collected when they collect one. Their amount recolted will be compare to the limit set in the config, and blocked the elytra from being ejected of the item frame if they reached it.
The only way to decrease this number is by dying in the void with one or many elytra.
This is not the best way to manage this, but I can't think of other idea. If you have one, free to you to send me a DM on discord : BoBsmil3Y#9147 .


## Commands / Permissions

|Description     |Commands                       |Permission                   |
|----------------|-------------------------------|-----------------------------|
|Change the limit of elytra collected for all player|`esetlimit <integer>`            |elytra.setlimit           |
|Send you the amount of actual collected elytra you have          |`eamount`            | elytra.amount            |
