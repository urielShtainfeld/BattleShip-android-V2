package data;

import java.util.Comparator;

/**
 * Created by ushtinfeld on 12/01/2018.
 */

public class RecordComparator implements Comparator<Record> {

    @Override
    public int compare(Record record1, Record record2) {
        return record1.compareTo(record2);
    }
}
