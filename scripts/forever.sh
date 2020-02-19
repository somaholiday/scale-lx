#!/bin/bash
#
# Runs shell script
#
# Restarts process if it dies for any reason.

NAME="scale"
SCRIPTNAME="$NAME.sh"
COMMAND="sudo /home/pi/$SCRIPTNAME"

echo "Starting $NAME forever loop..."
echo ""

while true
do

if pgrep $SCRIPTNAME > /dev/null
then
  echo "$SCRIPTNAME already running!"
  echo "Exiting."
  exit 1
else
  echo "Running..."
  echo $COMMAND
  echo ""

  LOG_FILE="/home/pi/logs/$(date +"%Y%m%d-%H%M%S")-$NAME"

  $COMMAND >& $LOG_FILE
fi

sleep 1

echo "$SCRIPTNAME died."
echo "Restarting..."
echo ""

sleep 1

done
