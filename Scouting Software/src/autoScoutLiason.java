import java.util.ArrayList;

public class autoScoutLiason
{
    private ArrayList<Integer> requested;
    private Scouter localScouter;

    public autoScoutLiason(ArrayList<Integer> inputRequests)
    {
        try
        {
            localScouter = new Scouter();
            requested = inputRequests;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public ArrayList<Integer> getTeamMatchData(int request)
    {
        ArrayList<Integer> output = new ArrayList<>();
        try
        {
            localScouter.GET(request);
            output.add(localScouter.getRed1());
            output.add(localScouter.getRed2());
            output.add(localScouter.getRed3());
            output.add(localScouter.getBlue1());
            output.add(localScouter.getBlue2());
            output.add(localScouter.getBlue3());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return output;
    }

    public ArrayList<Integer> getDataString(int request,boolean color)
    {
        //true = blue
        //false = red
        int temp;
        ArrayList<Integer> output = new ArrayList<>();
        try
        {
            localScouter.GET(request);

            output.add(request);

            if(color)
            {
                output.add(localScouter.getBlueFinal());
                output.add(localScouter.getBlueAuto());
                temp = localScouter.getBlueFinal() - localScouter.getBlueAuto() - localScouter.getBlueFoul();
                output.add(temp);
                output.add(localScouter.getBlueFoul());
            }
            else
            {
                output.add(localScouter.getRedFinal());
                output.add(localScouter.getRedAuto());
                temp = localScouter.getRedFinal() - localScouter.getRedAuto() - localScouter.getRedFoul();
                output.add(temp);
                output.add(localScouter.getRedFoul());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        return output;
    }

    /*
    Final Scores
    Sandstorm
    TeleOp
        (Final - sandstorm +/- fouls)
    Fouls

    match|total|sandstorm|TeleOp|Fouls
     */


}
