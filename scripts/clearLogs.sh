#!/bin/bash
find /home/pi/*.log -maxdepth 1 -mtime +14 -exec rm {} \;
find /home/pi/logs -maxdepth 1 -mtime +14 -exec rm {} \;