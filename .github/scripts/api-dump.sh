#!/usr/bin/env bash
# Dumps javap signatures of every vanilla class the mod imports, plus the full class list,
# so the port can be fixed against the real 26.1.2 API without decompiling locally.
set -u
out=ci-output
mkdir -p "$out"
jar=$(find ~/.gradle/caches .gradle build -name "minecraft-*client*.jar" -o -name "minecraft-merged*.jar" -o -name "minecraft-common*.jar" 2>/dev/null | grep -v sources | head -5)
echo "$jar" > "$out/jars.txt"
cp_jars=$(echo "$jar" | tr '\n' ':')
for j in $jar; do unzip -Z1 "$j" | grep '\.class$' | sed 's/\.class$//; s#/#.#g'; done | sort -u > "$out/classes.txt"
grep -rhoE "^import (net\.minecraft|com\.mojang)[A-Za-z0-9_.]+" src | sed 's/^import //' | sort -u > "$out/imports.txt"
# also dump any extra classes requested in .github/scripts/extra-classes.txt
{ cat "$out/imports.txt"; [ -f .github/scripts/extra-classes.txt ] && grep -v '^#' .github/scripts/extra-classes.txt | while read -r pat; do [ -n "$pat" ] && grep -E "^${pat}$" "$out/classes.txt"; done; } | sort -u | while read -r c; do
  [ -z "$c" ] && continue
  echo "===== $c"
  javap -p -cp "$cp_jars" "$c" 2>&1 | head -400
done > "$out/javap.txt"
