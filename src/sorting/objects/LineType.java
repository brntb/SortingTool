package sorting.objects;

public class LineType extends DataType {

    public LineType(String val) {
        super(val);
    }

    @Override
    public String toString() {
        return getVal();
    }

}
