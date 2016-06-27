#!/usr/bin/env bash

set -e

CD_DIR=`dirname $0`
PROP_FILE=$CD_DIR/../gradle.properties

if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    echo "signing.keyId=$GPG_KEY_ID" >> $PROP_FILE
    echo "signing.password=$GPG_PASS" >> $PROP_FILE
    echo "signing.secretKeyRingFile=$HOME/.gnupg/secring.gpg" >> $PROP_FILE
    echo "ossrhUsername=$OSSRH_USERNAME" >> $PROP_FILE
    echo "ossrhPassword=$OSSRH_PASSWORD" >> $PROP_FILE
    ./gradlew uploadArchives --stacktrace
fi
