public class cargo implements placeable
{
    private int total;
    private int location;
    /*
    0 = Cargo Ship
    1 = Rocket Bottom
    2 = Rocket Middle
    3 = Rocket Top
     */

    public cargo()
    {
        total = 0;
        location = -1;

    }

    public cargo(int inputLocation, int inputTotal)
    {
        total = inputTotal;
        location = inputLocation;
    }

    @Override
    public int getTotal() {
        return total;
    }

    @Override
    public int getLocation() {
        return location;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public void inputTotal(int inputTotal) {
        total =  inputTotal;
    }

    @Override
    public void inputLocation(int inputLocation) {
        location = inputLocation;
    }

    public String toString()
    {
        String tempString = "Type: cargo, "+"Total: " + total + ", at location: " + location;
        return tempString;
    }

}
