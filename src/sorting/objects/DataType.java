package sorting.objects;

import java.util.Objects;

public abstract class DataType {

    private final String val;
    private int count = 1;

    public DataType(String val) {
        this.val = val;
    }

    public int getCount() {
        return count;
    }

    public String getVal() {
        return val;
    }

    public void incrementCount() {
        this.count += 1;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataType dataType = (DataType) o;

        return Objects.equals(val, dataType.val);
    }

    @Override
    public int hashCode() {
        return val != null ? val.hashCode() : 0;
    }


}
