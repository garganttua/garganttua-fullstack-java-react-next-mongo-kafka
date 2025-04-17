#!/bin/bash

KEY_FILE="./envs/prod/garganttua-showcase-site-1-keypair.pem"

USER="admin"
HOST="ec2-13-38-44-88.eu-west-3.compute.amazonaws.com"

REMOTE_COMMAND="./restart_and_update.sh"

if [[ ! -f "$KEY_FILE" ]]; then
    echo "Erreur : Le fichier de clé privée '$KEY_FILE' n'existe pas."
    exit 1
fi

echo "Connexion à la VM et exécution de la commande '$REMOTE_COMMAND'..."
ssh -i "$KEY_FILE" "$USER@$HOST" "./$REMOTE_COMMAND"

if [[ $? -eq 0 ]]; then
    echo "Commande exécutée avec succès sur la VM."
else
    echo "Erreur : Échec de l'exécution de la commande sur la VM."
    exit 1
fi