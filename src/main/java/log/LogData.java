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
    Lock lock = new ReentrantLock();

    public LogData(int maxSize)
    {
        this.maxSize = maxSize;
        data = Collections.synchronizedList(new ArrayList<>(maxSize));
    }

    @Override
    public Iterator<LogEntry> iterator() {
        List<LogEntry> new_list = Collections.synchronizedList(data);
        return new_list.iterator();
    }

    public void add(LogEntry logEntry)
    {
        lock.lock();
        try{
            if(data.size() == maxSize)
            {
                data.remove(0);
            }
            data.add(logEntry);
        }
        finally {
            lock.unlock();
        }
    }

    public int size()
    {
        return data.size();
    }
}
