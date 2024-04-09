package log;

import java.util.Iterator;

public class LogData implements Iterable<LogEntry> {

    private final int maxSize;
    private final LogEntry[] data;
    private int startPos;
    private int cnt;

    public LogData(int maxSize)
    {
        this.maxSize = maxSize;
        data = new LogEntry[maxSize];
        startPos = 0;
        cnt = 0;
    }

    private static class LogIterator implements Iterator<LogEntry>
    {
        private final LogData logData;
        private int cnt;
        private final int stop_cnt;

        public LogIterator(LogData logData, int cnt, int stop_cnt)
        {
            synchronized (logData)
            {
                this.logData = logData;
                this.cnt = cnt;
                this.stop_cnt = stop_cnt;
            }
        }

        @Override
        public boolean hasNext() {
            synchronized (logData)
            {
                return cnt < logData.cnt && cnt < stop_cnt;
            }
        }

        @Override
        public LogEntry next() {
            synchronized (logData)
            {
                return logData.data[(logData.startPos+(cnt++))% logData.maxSize];
            }
        }
    }

    @Override
    public Iterator<LogEntry> iterator() {
        return new LogIterator(this, 0, maxSize);
    }

    public void add(LogEntry logEntry)
    {
        synchronized (this)
        {
            if(cnt == maxSize)
            {
                startPos++;
                startPos%=maxSize;
                data[(startPos+cnt-1)%maxSize] = logEntry;
            }
            else
            {
                data[startPos+cnt] = logEntry;
                cnt++;
            }
        }
    }

    public int size()
    {
        return cnt;
    }
}
