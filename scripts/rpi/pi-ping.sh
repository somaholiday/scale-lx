#!/bin/bash

#source ./.env
#echo ${PIXLITES[@]}
curl "https://soma.space/.netlify/functions/pi-ping?hostname=$(hostname)"
