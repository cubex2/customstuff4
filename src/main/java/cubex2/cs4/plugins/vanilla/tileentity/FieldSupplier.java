package cubex2.cs4.plugins.vanilla.tileentity;

public interface FieldSupplier
{
    int getFieldCount();

    int getField(int id);

    void setField(int id, int value);

    FieldSupplier EMPTY = new FieldSupplier()
    {
        @Override
        public int getFieldCount()
        {
            return 0;
        }

        @Override
        public int getField(int id)
        {
            return 0;
        }

        @Override
        public void setField(int id, int value)
        {

        }
    };
}
