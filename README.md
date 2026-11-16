# CSA09 – Assignment 1: Campus Smart-Parking System (CO1)

| | |
|---|---|
| **Name** | V M Sai Bhargav |
| **Register number** | `<REGISTER NUMBER>` (fill in) |
| **Course** | CSA09 – Programming in Java, SIMATS |
| **Java version** | OpenJDK 21 (any JDK 16+ works – Task 8 uses `instanceof Chargeable c` pattern matching) |
| **Report** | `Report/CSA09_Assignment1_Report.docx` (and `.pdf`) – evidence for Tasks 1–12 |

## How to compile and run

Run everything at once: `bash scripts/run_all.sh`

| Task | Commands (from the repository root) | Output file |
|---|---|---|
| 1 | `cd Task01 && javac Task1Demo.java && java Task1Demo` | `output.txt` |
| 2 | `cd Task02 && javac Task2Demo.java && java Task2Demo < input.txt` | `output.txt` |
| 3 | `cd Task03 && javac *.java && java ParkingFacility` (Complete the Code) and `java FastAllocator` (alternative) | `output.txt`, `output_alternative.txt` |
| 4 | `cd Task04 && javac Task4Demo.java && java Task4Demo` | `output.txt` |
| 5 | `cd Task05 && javac Task5Demo.java && java Task5Demo` | `output.txt` |
| 6 | `cd Task06 && javac Task6Demo.java && java Task6Demo` | `output.txt`, `uml/hierarchy.png` |
| 7 | `cd Task07 && javac Task7Demo.java && java Task7Demo` | `output.txt` |
| 8 | `cd Task08 && javac *.java && java FeeDemo` (Complete the Code) and `java PolymorphismDemo` | `output.txt`, `output_polymorphism.txt` |
| 9 | `cd Task09 && javac Task9Demo.java && java Task9Demo` | `output.txt` |
| 10 | `cd Task10 && mkdir out && javac -d out $(find src -name '*.java') && java -cp out parking.app.Task10Main` | `output.txt` |
| 11 | `cd Task11 && mkdir out && javac -d out $(find src -name '*.java')` then `java -cp out parking.app.Main`, `…Tests`, `…StoreBenchmark` | `output_main.txt`, `output_tests.txt`, `output_benchmark.txt` |
| 12 | `cd Task12 && mkdir out && javac -d out $(find src -name '*.java')` then `java -cp out parking.app.Main12`, `…Tests` (regression), `…Tests12` | `output_main12.txt`, `output_regression_task11_tests.txt`, `output_tests12.txt` |

Each `output*.png` is the screenshot of the matching `output*.txt` (rendered from the real program output).

## Result of each task

1. **Task 1** – Problem analysis in the report. Search by registration number is O(n) in both designs (objects 1.32 ms vs parallel arrays 0.61 ms at n = 100,000); objects are safer to extend.
2. **Task 2** – `int` for slots/minutes, `double` for fees, jagged `String[][]`. Integer division gives Rs 60 instead of Rs 90 for 130 min; fixed with `(minutes + 59) / 60`.
3. **Task 3** – `allocate()`/`release()` completed; output identical to the expected output (`[0, 0] [0, 1] [1, 0] null / true false false / [0, 1]`). Count+stack alternative is ~8× faster in the measured case.
4. **Task 4** – `ParkingTicket` with default, overloaded, chained and copy constructors; setter/factory alternatives compared.
5. **Task 5** – static counter, constant, static block; instance counter would give duplicate numbers for two facilities; GC eligibility demonstrated.
6. **Task 6** – `Vehicle → Car → ElectricCar` hierarchy, UML, constructor order Vehicle → Car → ElectricCar.
7. **Task 7** – overriding with `@Override`, `final`, `equals/hashCode/toString`; switch alternative needs edits to existing code.
8. **Task 8** – `FeeDemo` completed; output identical to expected (`Rs 30.0 / 90.0 / 210.0 / true`); overloading and dispatch trace in the report.
9. **Task 9** – abstract `Vehicle`, interfaces `Chargeable`/`Payable` with default and static methods.
10. **Task 10** – `parking.model / service / app` packages, access modifiers, validating setters, `Integer ==` pitfall.
11. **Task 11** – 18-class system; **25/25 tests pass**; hand total Rs 375 = program total; HashMap exit is ~650× faster than array at n = 40,000.
12. **Task 12** – **Decision: MODIFY (extend)**. 2 of 14 core classes modified (additive), 5 new model classes; **28/28 new tests pass** and the 25 original tests still pass; hand total Rs 360 = program total.

## Assumptions (not given in the assignment)
Rates: two-wheeler Rs 10/h, car Rs 30/h, EV Rs 35/h, daily max 8 × rate, EV charging Rs 12/kWh; e-bike flat Rs 20; van Rs 30/h off-peak and Rs 60/h for hours starting 09:00–16:59 (daily max Rs 480); one-day pass Rs 100. Times are minutes since midnight within one day.
