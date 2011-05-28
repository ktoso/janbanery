#!/bin/sh
while true ; do
  mvn clean install
  testsPassed=$?
  if [ testsPassed == 1  ] ; then
    notify-send -i /home/ktoso/red.png "Tests failed"
  else
    notify-send -i /home/ktoso/red.png "Tests failed" 
  fi

  sleep 10
done
