# Startup Script Notes

## Raspberry Pi

(assumes Raspbian and LXDE)

### To auto-start on boot

Add the `annwn-lx.desktop` file to `/home/pi/.config/autostart`

`annwn-lx.desktop` runs `/home/pi/forever.sh`

`/home/pi/forever.sh` runs `/home/pi/annwn-lx.sh` and re-runs it if it dies for any reason.

`annwn-lx.sh` runs a Java main `com.studioannwn.AnnwnLX` which is packaged in `annwn-lx-1.0.0.jar`.

### To disable the auto-start on boot

Go to Start > Preferences > Desktop Session Settings and uncheck the "Annwn-LX" entry, or remove `/home/pi/.config/autostart/annwn-lx.desktop`