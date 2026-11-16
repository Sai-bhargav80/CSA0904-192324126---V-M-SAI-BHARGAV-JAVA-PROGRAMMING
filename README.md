# CSA09-<RegisterNumber>-Assignment3
**Course:** Programming in Java (CO3: Exception Handling & Multithreading)  
**Mode:** Pair Work (2 Students per Group)  
**Student A Name & Register Number:** <Student A Name> (<Register Number A>)  
**Student B Name & Register Number:** <Student B Name> (<Register Number B>)  
**Java Version:** OpenJDK 17 / 21 / 25  

## Repository Structure
- `Q1/` — Bank ATM Simulation (`ATMWithdrawal.java`, `ATMErrorCodeAlternative.java`)
- `Q2/` — Online Examination Portal (`StorageDemo.java` [Complete the Code], `ExamPortalController.java`)
- `Q3/` — Daily Sales Report Generator (`ManualSalesReport.java`, `SafeSalesReportTWR.java`)
- `Q4/` — Parallel Answer-Script Scanning (`AnswerScriptScannerDemo.java`)
- `Q5/` — Cinema Seat-Booking System (`SeatBooking.java` [Complete the Code], `SynchronizationComparison.java`)
- `Q6/` — Hostel Wallet Transfers & Mess Token Counter (`TokenCounter.java` [Complete the Code], `HostelWalletTransfer.java`)
- `Q7/` — Concurrent Fault-Tolerant Semester Result Processor (`SemesterResultProcessor.java`)
- `Report/` — Complete Joint Report Documentation (`CSA09_Assignment3_Report.md`)

## Compilation & Execution Instructions
From the root directory (`CSA09-<RegisterNumber>-Assignment3`):
```bash
# Q1
javac Q1/*.java && java -cp Q1 ATMWithdrawal && java -cp Q1 ATMErrorCodeAlternative

# Q2
javac Q2/*.java && java -cp Q2 StorageDemo && java -cp Q2 ExamPortalController

# Q3
javac Q3/*.java && java -cp Q3 ManualSalesReport && java -cp Q3 SafeSalesReportTWR

# Q4
javac Q4/*.java && java -cp Q4 AnswerScriptScannerDemo

# Q5
javac Q5/*.java && java -cp Q5 SeatBooking && java -cp Q5 SynchronizationComparison

# Q6
javac Q6/*.java && java -cp Q6 TokenCounter && java -cp Q6 HostelWalletTransfer

# Q7
javac Q7/*.java && java -cp Q7 SemesterResultProcessor
```

## Summary of Results
- **Q1:** Handled checked (`FileNotFoundException`) and unchecked (`NumberFormatException`, `InvalidAmountException`, `InsufficientBalanceException`) failures with multi-catch and guaranteed card ejection in `finally`.
- **Q2:** Implemented layered exception propagation (`Storage` -> `Service` -> `Controller`) with root-cause chaining (`getCause()`). `StorageDemo` matched exact expected output.
- **Q3:** Demonstrated resource leak in manual `try-catch-finally` when an exception occurs mid-loop and verified zero leaked file handles using `try-with-resources`.
- **Q4:** Compared `extends Thread` vs `implements Runnable`, traced `NEW -> RUNNABLE -> TIMED_WAITING -> TERMINATED` states, and benchmarked `ExecutorService`.
- **Q5:** Reproduced double-booking race condition without synchronization and eliminated it using `synchronized`, `ReentrantLock`, and `AtomicBoolean`.
- **Q6:** Solved producer-consumer bounded buffer (`TokenCounter` sum = `210`) using `while` + `wait()`/`notifyAll()`, and prevented wallet deadlock using strict lock ordering.
- **Q7:** Processed 10,000 files concurrently using `ExecutorService` + `Callable` + `Future` + `ConcurrentHashMap`, isolating corrupt/missing files without aborting the batch.
