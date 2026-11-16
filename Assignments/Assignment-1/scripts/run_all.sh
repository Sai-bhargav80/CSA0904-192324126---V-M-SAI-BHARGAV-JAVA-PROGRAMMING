#!/usr/bin/env bash
# Compiles and runs every task from a clean state. Usage: bash scripts/run_all.sh   (needs JDK 16+)
set -e
cd "$(dirname "$0")/.."
echo "== Task01"; (cd Task01 && javac Task1Demo.java && java Task1Demo)
echo "== Task02"; (cd Task02 && javac Task2Demo.java && java Task2Demo < input.txt)
echo "== Task03"; (cd Task03 && javac *.java && java ParkingFacility && java FastAllocator)
echo "== Task04"; (cd Task04 && javac Task4Demo.java && java Task4Demo)
echo "== Task05"; (cd Task05 && javac Task5Demo.java && java Task5Demo)
echo "== Task06"; (cd Task06 && javac Task6Demo.java && java Task6Demo)
echo "== Task07"; (cd Task07 && javac Task7Demo.java && java Task7Demo)
echo "== Task08"; (cd Task08 && javac *.java && java FeeDemo && java PolymorphismDemo)
echo "== Task09"; (cd Task09 && javac Task9Demo.java && java Task9Demo)
echo "== Task10"; (cd Task10 && mkdir -p out && javac -d out $(find src -name '*.java') && java -cp out parking.app.Task10Main)
echo "== Task11"; (cd Task11 && mkdir -p out && javac -d out $(find src -name '*.java') && java -cp out parking.app.Main && java -cp out parking.app.Tests | tail -3)
echo "== Task12"; (cd Task12 && mkdir -p out && javac -d out $(find src -name '*.java') && java -cp out parking.app.Main12 && java -cp out parking.app.Tests && java -cp out parking.app.Tests12 | tail -3)
echo "All tasks compiled and ran."
