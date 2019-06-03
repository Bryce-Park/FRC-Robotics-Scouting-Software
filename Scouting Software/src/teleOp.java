import java.util.ArrayList;

public class teleOp
{
    private int enterValue;
    /*
    0 = didn't enter the HAB
    1 = entered lowest level of the HAB
    2 = entered 2nd level of the HAB
    3 = entered the highest level of the HAB
     */
    private ArrayList<hatch> hatches;
    private ArrayList<cargo> cargoes;

    public teleOp()
    {
        enterValue = -1;
        hatches = new ArrayList<hatch>();
        cargoes = new ArrayList<cargo>();
    }

    public teleOp (int inputExitValue, ArrayList<hatch> inputHatches, ArrayList<cargo> inputCargoes)
    {
        enterValue = inputExitValue;
        hatches = inputHatches;
        cargoes = inputCargoes;
    }

    public void inputExitValue (int inputExitValue)
    {
        enterValue = inputExitValue;
    }

    public void inputHatches (ArrayList<hatch> inputHatches)
    {
        hatches = inputHatches;
    }

    public void inputCargoes (ArrayList<cargo> inputCargoes)
    {
        cargoes = inputCargoes;
    }

    public int getEnterValue ()
    {
        return enterValue;
    }

    public ArrayList<hatch> getHatches()
    {
        return hatches;
    }

    public ArrayList<cargo> getCargo()
    {
        return cargoes;
    }
}