package com.carrotsearch.junitbenchmarks;

import java.io.IOException;
import java.io.Writer;
import java.util.Locale;

/**
 * {@link IResultsConsumer} printing benchmark results to a given writer. 
 */
public final class WriterConsumer implements IResultsConsumer
{
    private final Writer w;

    public WriterConsumer(Writer w)
    {
        this.w = w;
    }

    public void accept(Result result)
    {
        try
        {
            w.write(String.format(Locale.ENGLISH,
                "%s: [measured %d out of %d rounds]\n" +
                " round: %s, round.gc: %s, GC.calls: %d, GC.time: %.2f," +
                " time.total: %.2f, time.warmup: %.2f, time.bench: %.2f\n",
                result.target.getClass().getSimpleName() + "." + result.method.getName(),
                result.benchmarkRounds, 
                result.benchmarkRounds + result.warmupRounds, 
                result.roundAverage.toString(), 
                result.gcAverage.toString(), 
                result.gcInfo.accumulatedInvocations(), 
                result.gcInfo.accumulatedTime() / 1000.0,
                (result.warmupTime + result.benchmarkTime) * 0.001,
                result.warmupTime * 0.001, 
                result.benchmarkTime * 0.001
            ));
            w.flush();
        }
        catch (IOException e)
        {
            // Ignore.
        }
    }
}
