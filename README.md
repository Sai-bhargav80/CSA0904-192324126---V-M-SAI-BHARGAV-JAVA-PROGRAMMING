# CSA09 – Assignment 2 (CO2): Strings, Collections, hashCode–equals, Generics, Queues

| | |
|---|---|
| **Name** | V M Sai Bhargav |
| **Register number** | `<REGISTER NUMBER>` (fill in) |
| **Course** | CSA09 – Programming in Java, SIMATS |
| **Java version** | OpenJDK 21 (any JDK 16+ works) |
| **Report** | `Report/CSA09_Assignment2_Report.docx` (and `.pdf`) – evidence for Q1–Q7, charts in `charts/` |

## How to compile and run
Everything: `bash scripts/run_all.sh` (≈ 5 min) or `bash scripts/run_all.sh quick` (skips the long benchmarks).

| Q | Commands (from the repository root) | Output / raw data |
|---|---|---|
| 1 | `cd Q1 && javac *.java && java StringBench && java ThreadSafetyDemo` | `output.txt`, `output_threads.txt`, `raw_data.csv` |
| 2 | `cd Q2 && javac ListBench.java && java ListBench` | `output.txt`, `raw_data.csv` |
| 3 | `cd Q3/before_todo && javac Attendance.java && java Attendance` (TODO 1 **not** done), then `cd Q3 && javac *.java && java Attendance` (completed), `java ContractExperiments`, `java DedupBench` | `output_before_todo.txt`, `output_after_todo.txt`, `output_experiments.txt`, `output_dedup.txt` |
| 4 | `cd Q4 && javac PharmacyMaps.java && java -Xmx2g PharmacyMaps` | `output.txt` |
| 5 | `cd Q5 && javac *.java && java LeaderboardDemo && java Q5Experiments` | `output.txt`, `output_experiments.txt` |
| 6 | `cd Q6 && javac *.java && java Triage && java TriageStudy` | `output.txt`, `output_study.txt` |
| 7 | `cd Q7 && javac *.java && java DifferentialTest && java Q7Experiment` | `output_correctness.txt`, `output_experiment.txt`, `uml/gaplist.png` |

Each `output*.png` is the screenshot of the matching `output*.txt` (rendered from the real output).

## Result of each question
1. **Q1** – `+` is O(n²): 467 ms and 4.4 GB allocated for a 20,000-field ticket vs 0.43 ms and 2.1 MB for StringBuilder; for 10 fields the difference is noise. StringBuilder for per-ticket text; shared log needs StringBuffer (StringBuilder corrupted in 5 of 20 runs).
2. **Q2** – ArrayList is fine for append/get/iterate and human-paced edits; for the Up-Next queue ArrayDeque is ≈ 80–95× faster than ArrayList (7.8 ms vs 637 ms).
3. **Q3** – `Unique students: 3` before TODO 1, `2` after. Duplicates, mutation-after-insertion and TreeSet-inconsistency tests with predictions; TreeSet 12× slower than HashSet at 1M.
4. **Q4** – All maps meet a 1 ms lookup budget; LinkedHashMap chosen; remaining cost is report generation (sorting + formatting).
5. **Q5** – Output `[Eagles(9,6), Bisons(9,4), Panthers(9,4), Cobras(6,1)]` reproduced; faulty comparators exposed by an all-triples check.
6. **Q6** – Output `Meena(S1) Kumar(S1) Lata(S2) Ravi(S3) John(S3) Asha(S5)` reproduced; starvation shown; aging rule serves the S5 patient at minute 120; one day of 5,000 patients takes only a few milliseconds (2.7–6.7 ms in repeated runs).
7. **Q7** – Redesign of the Q2 playlist as a gap buffer: 68–72× faster (136–179× fewer moves) for clustered edits, 31 % slower for random edits; correct in 400,000 differential-test operations.
