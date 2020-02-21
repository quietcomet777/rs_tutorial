#! /bin/sh



xvfb-run java -jar /root/osBot.jar -login "difrtydan:temppassword" -bot "$BOT_LOGIN" -script "$SCRIPT" -world "$WORLD" -debug "3379"