import java.util.ArrayList;

public class sandstorm
{
    private int exitValue;
    /*
    0 = didn't leave the HAB
    1 = exited the HAB from the bottom
    2 = exited HAB from the 2nd level
     */
    private ArrayList<hatch> sandstormHatches;
    private ArrayList<cargo> sandstormCargoes;

    public sandstorm()
    {
        exitValue = -1;
        sandstormHatches = new ArrayList<hatch>();
        sandstormCargoes = new ArrayList<cargo>();
    }

    public sandstorm (int inputExitValue, ArrayList<hatch> inputHatches, ArrayList<cargo> inputCargoes)
    {
        exitValue = inputExitValue;
        sandstormHatches = inputHatches;
        sandstormCargoes = inputCargoes;
    }

    public void inputExitValue (int inputExitValue)
    {
        exitValue = inputExitValue;
    }

    public void inputHatches (ArrayList<hatch> inputHatches)
    {
        sandstormHatches = inputHatches;
    }

    public void inputCargoes (ArrayList<cargo> inputCargoes)
    {
        sandstormCargoes = inputCargoes;
    }

    public int getExitValue ()
    {
        return exitValue;
    }

    public ArrayList<hatch> getHatches()
    {
        while(sandstormHatches.size()>4)
        {
            sandstormHatches.remove(sandstormHatches.size()-1);
        }
        return sandstormHatches;
    }

    public ArrayList<cargo> getCargo()
    {
        while(sandstormCargoes.size()>4)
        {
            sandstormCargoes.remove(sandstormCargoes.size()-1);
        }
        return sandstormCargoes;
    }
}
