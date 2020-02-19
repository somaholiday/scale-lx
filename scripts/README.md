## Startup Script Notes

This project runs on a Raspberry Pi.

To start on boot, add the `scale.desktop` file to `/home/pi/.config/autostart`

`scale.desktop` runs `/home/pi/forever.sh`, which runs `/home/pi/scale.sh` and re-runs it if it dies for any reason.

`scale.sh` expects a Java main `com.studioannwn.Scale` in `/home/pi/scale/`, which is packaged in `scale-1.0.0.jar`

To disable the auto-start on boot, go to Start > Preferences > Desktop Session Settings, or remove the `scale.desktop` file.