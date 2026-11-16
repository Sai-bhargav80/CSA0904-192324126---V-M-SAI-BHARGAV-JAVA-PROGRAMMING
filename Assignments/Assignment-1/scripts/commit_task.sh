#!/usr/bin/env bash
# Usage: bash scripts/commit_task.sh 03 "Task 3: allocate/release completed, output matches expected"
# Commit ONE task at a time, right after you have run it and understood it.
set -e
cd "$(dirname "$0")/.."
git add "Task$1"
git commit -m "$2"
