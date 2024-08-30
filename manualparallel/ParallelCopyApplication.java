package com.example.parallelcopy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootApplication
public class ParallelCopyApplication implements CommandLineRunner {


    private final DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(ParallelCopyApplication.class);


    public ParallelCopyApplication(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    private static final int NUMBER_OF_THREADS = 4;
    private static final String RANGE_START_TIMESTAMP = "2024-01-01 00:00:00";
    private static final String RANGE_END_TIMESTAMP = "2024-01-01 02:47:00";
    private static final int RANGE_PARTITION_SIZE_SECOND = 60;

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        SpringApplication.run(ParallelCopyApplication.class, args);

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
        List<DataCopyTask> tasks = new ArrayList<>();

        LocalDateTime rangeStartTimestamp = LocalDateTime.parse(RANGE_START_TIMESTAMP, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime rangeEndTimestamp = LocalDateTime.parse(RANGE_END_TIMESTAMP, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        while (rangeStartTimestamp.isBefore(rangeEndTimestamp)) {
            LocalDateTime start = rangeStartTimestamp;
            LocalDateTime end = rangeStartTimestamp.plusSeconds(RANGE_PARTITION_SIZE_SECOND);
            tasks.add(new DataCopyTask(start, end));
            rangeStartTimestamp = end;
        }

        for (DataCopyTask task : tasks) {
            executorService.execute(task);
        }

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    private class DataCopyTask implements Runnable {
        private final LocalDateTime startTimestamp;
        private final LocalDateTime endTimestamp;

        DataCopyTask(LocalDateTime startTimestamp, LocalDateTime endTimestamp) {
            this.startTimestamp = startTimestamp;
            this.endTimestamp = endTimestamp;
        }

        @Override
        public void run() {

            logger.info("Start to Process data range {} to {} by Thread: {}", startTimestamp, endTimestamp, Thread.currentThread().getId());

            try (Connection conn = dataSource.getConnection()) {
                String insertQuery = "INSERT INTO t_txn_new (id, c1, c2, update_ts) " +
                        "SELECT id, c1, c2, update_ts " +
                        "FROM t_txn " +
                        "WHERE update_ts >= ? AND update_ts < ?";

                PreparedStatement stmt = conn.prepareStatement(insertQuery);
                stmt.setObject(1, startTimestamp);
                stmt.setObject(2, endTimestamp);

                long startTime = System.currentTimeMillis();
                int rowsInserted = stmt.executeUpdate();
                long endTime = System.currentTimeMillis();

                // System.out.println("Inserted " + rowsInserted + " rows in range " + startTimestamp + " to " + endTimestamp +
                //        " in " + (endTime - startTime) + " ms" + " by Thread: " + Thread.currentThread().getId());
                logger.info("Inserted {} rows in range {} to {} in {} ms by Thread: {}", rowsInserted, startTimestamp, endTimestamp, (endTime - startTime), Thread.currentThread().getId());

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
