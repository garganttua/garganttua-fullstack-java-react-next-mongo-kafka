#!/bin/bash

# Vérification des arguments
if [ $# -ne 5 ]; then
  echo "Usage: $0 <base_package> <project_name> <project_domain_name> <project_container_repo> <project_repo>"
  echo "Exemple: $0 com.garganttua Showcase-Site ghcr.io/garganttua/showcase-site https://maven.pkg.github.com/garganttua/showcase-site"
  exit 1
fi

BASE_PACKAGE=$1
PROJECT_NAME=$2
CONTAINER_REPO=$3
PROJECT_REPO=$4

# Construction du nom de domaine en inversant le package
IFS='.' read -r -a parts <<< "$BASE_PACKAGE"
DOMAIN_NAME=""
for (( idx=${#parts[@]}-1 ; idx>=0 ; idx-- )) ; do
  DOMAIN_NAME+="${parts[idx]}"
  if [ $idx -ne 0 ]; then
    DOMAIN_NAME+="."
  fi
done

# Transformation du nom de projet : minuscule, sans tirets
PROJECT_DIR_NAME=$(echo "$PROJECT_NAME" | tr '[:upper:]' '[:lower:]' | tr -d '-' | tr -d '_')

# Transformation du package en chemin
PACKAGE_PATH=$(echo "$BASE_PACKAGE" | tr '.' '/')

# Construction du chemin final
FINAL_PATH="./backend/src/main/java/${PACKAGE_PATH}/${PROJECT_DIR_NAME}"

# Création du dossier
mkdir -p "$FINAL_PATH"

echo "✅ Dossier créé : $FINAL_PATH"

# Vérification de l'existence du zip
ZIP_SOURCE="./backend/backend-sources.zip"
if [ ! -f "$ZIP_SOURCE" ]; then
  echo "❌ Archive $ZIP_SOURCE introuvable."
  exit 2
fi

# Déplacement du zip
#mv "$ZIP_SOURCE" "$FINAL_PATH/"
#echo "📦 Archive déplacée dans $FINAL_PATH"

# Décompression
unzip -q "$ZIP_SOURCE" -d "$FINAL_PATH"
echo "📂 Archive extraite"

# Remplacement des placeholders dans les fichiers sources
echo "🔄 Remplacement des variables dans les fichiers source..."

find "." -type f \( -name "*.java" -o -name "*.xml" -o -name "*.js" -o -name "*.conf" -o -name "*.yml" -o -name "*.json" -o -name "*.properties" -o -name "*.md" \) | while read -r file; do
  sed -i "s|\${packageName}|$BASE_PACKAGE|g" "$file"
  sed -i "s|\${projectName}|$PROJECT_DIR_NAME|g" "$file"
  sed -i "s|\${domainName}|$DOMAIN_NAME|g" "$file"
  sed -i "s|\${projectContainerRepository}|$CONTAINER_REPO|g" "$file"
  sed -i "s|\${projectRepository}|$PROJECT_REPO|g" "$file"
done

# Renommage des fichiers contenant des placeholders
echo "📝 Renommage des fichiers contenant des variables..."

# Renommage des fichiers contenant des placeholders
echo "📝 Renommage des fichiers contenant des variables..."

find "." -depth -name "*\${*}*" | while read -r file; do
  dir=$(dirname "$file")
  base=$(basename "$file")

  new_base="$base"
  new_base="${new_base//\$\{packageName\}/$BASE_PACKAGE}"
  new_base="${new_base//\$\{projectName\}/$PROJECT_DIR_NAME}"
  new_base="${new_base//\$\{domainName\}/$DOMAIN_NAME}"
  new_base="${new_base//\$\{projectContainerRepository\}/$CONTAINER_REPO}"
  new_base="${new_base//\$\{projectRepository\}/$PROJECT_REPO}"

  new_path="${dir}/${new_base}"

  if [ "$file" != "$new_path" ]; then
    mv "$file" "$new_path"
    echo "🔁 $file → $new_path"
  fi
done

# Suppression de l’archive
#rm "$FINAL_PATH/backend-sources.zip"
echo "🧹 Archive supprimée"
