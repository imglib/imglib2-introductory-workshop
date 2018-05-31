#!/bin/sh
curl -fsLO https://raw.githubusercontent.com/scijava/scijava-scripts/master/travis-build.sh
sh travis-build.sh $encrypted_3e1a956cbc25_key $encrypted_3e1a956cbc25_iv
