ProtocolSupport
===============

[![Build Status](https://build.true-games.org/buildStatus/icon?job=ProtocolSupport)](https://build.true-games.org/job/ProtocolSupport/)
[![Chat](https://img.shields.io/badge/chat-on%20discord-7289da.svg)](https://discord.gg/x935y8p)
<span class="badge-paypal"><a href="https://www.paypal.com/cgi-bin/webscr?business=shev4chik.den%40gmail.com&lc=US&item_name=ProtocolSupport&cmd=_donations&rm=1&no_shipping=1&currency_code=USD" title="Donate to this project using Paypal"><img src="https://img.shields.io/badge/paypal-donate-yellow.svg" alt="PayPal donate button" /></a></span>

Support 1.16, 1.15, 1.14, 1.13, 1.12, 1.11, 1.10, 1.9, 1.8, 1.7, 1.6, 1.5, 1.4.7 clients on Spigot 1.16.2

Important notes:
* Only latest version of this plugin is supported
* This plugin can't be reloaded or loaded not at server startup

Known issues:
* [Anything that is not latest] Items in creative mode may not work as expected, or may not work at all
* [1.8 and earlier] Thrown potion texture is invalid

Known wontfix issues:
* [Anything that is not latest] Stats are not sent
* [All] Can't have multiple boats passengers (Intentional to prevent rendering glitches). Disable all versions before 1.9 using API to reenable multiple boat passengers.
* [1.12 and earlier] Chests are seen as enderchests (Intentional to prevent rendering glitches). Block mappings can be changed using API if you with to see chests as chests again.
* [1.8 and earlier] Can't control vehicle (Not directly translatable at network level, too much work to implement serverside)
* [1.4.7 and earlier] Server shows up as "incompatible" in the server list (Impossible to fix due to the lack of an way to verify the client version during server list ping)

---

Website: https://protocol.support/

Spigot: https://www.spigotmc.org/resources/protocolsupport.7201/

BukkitDev: https://dev.bukkit.org/projects/protocolsupport/

MC Market: http://www.mc-market.org/resources/4607/

Main Jenkins: https://build.true-games.org/job/ProtocolSupport/
Backup Jenkins: https://ci.velocitypowered.com/job/ProtocolSupport/

---

Licensed under the terms of GNU AGPLv3
