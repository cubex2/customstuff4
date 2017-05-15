package cubex2.cs4.plugins.vanilla.tileentity;

public interface FieldSupplier
{
    int getFieldCount();

    int getField(int id);

    void setField(int id, int value);
}
