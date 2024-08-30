package com.example.parallelinsert;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootApplication
public class ParallelInsertApplication implements CommandLineRunner {

    private final DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(ParallelInsertApplication.class);


    public ParallelInsertApplication(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final int NUMBER_OF_THREADS = 4;
    private static final long RANGE_START_ID = 1;
    private static final long RANGE_END_ID = 1000000;
    private static final int RANGE_PARTITION_SIZE = 10000;

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        SpringApplication.run(ParallelInsertApplication.class, args);

        long endTime = System.currentTimeMillis();
        long durationMillis = endTime - startTime;

        Duration duration = Duration.of(durationMillis, ChronoUnit.MILLIS);
        String formattedDuration = String.format("%02d:%02d:%02d", duration.toHoursPart(), duration.toMinutesPart(), duration.toSecondsPart());
        // System.out.println("Total time taken: " + formattedDuration);
        logger.info("Total time taken: {}", formattedDuration);

    }

    @Override
    public void run(String... args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        List<DataInsertTask> tasks = new ArrayList<>();

        for (long i = RANGE_START_ID; i < RANGE_END_ID; i += RANGE_PARTITION_SIZE) {
            long start = i;
            long end = Math.min(i + RANGE_PARTITION_SIZE - 1, RANGE_END_ID);
            tasks.add(new DataInsertTask(start, end));
        }

        for (DataInsertTask task : tasks) {
            executorService.execute(task);
        }

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    private class DataInsertTask implements Runnable {
        private final long startId;
        private final long endId;

        DataInsertTask(long startId, long endId) {
            this.startId = startId;
            this.endId = endId;
        }

        @Override
        public void run() {
            try (Connection conn = dataSource.getConnection()) {
                String insertQuery = "INSERT INTO t_txn(id, c1, c2, update_ts) " +
                        "SELECT i, rpad(i::text, 1000, 'x'), rpad(i::text, 1000, 'y'), " +
                        "to_timestamp('2024-01-01 00:00', 'YYYY-MM-DD HH24:MI') + (interval '0.01 sec') * i " +
                        "FROM generate_series(?, ?) i";

                PreparedStatement stmt = conn.prepareStatement(insertQuery);
                stmt.setLong(1, startId);
                stmt.setLong(2, endId);

                long startTime = System.currentTimeMillis();
                int rowsInserted = stmt.executeUpdate();
                long endTime = System.currentTimeMillis();

                // System.out.println("Inserted " + rowsInserted + " rows in range " + startId + " to " + endId +
                //        " in " + (endTime - startTime) + " ms" + " by Thread: " + Thread.currentThread().getId());
                logger.info("Inserted {} rows in range {} to {} in {} ms by Thread: {}", rowsInserted, startId, endId, (endTime - startTime), Thread.currentThread().getId());

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}