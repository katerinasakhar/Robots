package log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LogData implements Iterable<LogEntry> {

    private final int maxSize;
    private final List<LogEntry> data;
    private int startPos;
    private int cnt;
    Lock lock = new ReentrantLock();

    public LogData(int maxSize)
    {
        this.maxSize = maxSize;
        data= Collections.synchronizedList(new ArrayList<>(maxSize));

        startPos = 0;
        cnt = 0;
    }

    /*private static class LogIterator implements Iterator<LogEntry>
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
    }*/

    @Override
    public Iterator<LogEntry> iterator() {
        List<LogEntry>new_list=Collections.synchronizedList(data);;
        return new_list.iterator();
    }

    public void add(LogEntry logEntry)
    {
        lock.lock();
        try{
            if (cnt==maxSize) {
                data.remove(0);
                data.add(logEntry);
            }
            else {
                data.add(logEntry);
                cnt++;
            }
        }
        finally {
            lock.unlock();
        }
        /*synchronized (this)
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
        }*/
    }

    public int size()
    {
        return cnt;
    }
}
