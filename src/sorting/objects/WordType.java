package sorting.objects;

public class WordType extends DataType {

    public WordType(String val) {
        super(val);
    }

    @Override
    public String toString() {
        return getVal();
    }


}
