import java.util.ArrayList;

public class teamProfile
{
    private int teamNumber;
    private ArrayList<matchProfile> matchProfiles;
    private ArrayList<pitComment> pitComments;

    public teamProfile()
    {

    }

    public teamProfile(ArrayList<matchProfile> inputMatchProfiles, ArrayList<pitComment> inputPitComments)
    {
        matchProfiles = inputMatchProfiles;
        teamNumber = inputMatchProfiles.get(0).getTeamNumber();
        pitComments = inputPitComments;
    }

    public ArrayList<pitComment> getPitComments()
    {
        return pitComments;
    }

    public int getPieceAverage(int type)
    {
        int output = 0;
            for(int counter = 0;counter<matchProfiles.size();counter++)
            {
                output += matchProfiles.get(counter).getPieceTotal(type);
            }
        output = output/matchProfiles.size();
        return output;
    }

    public int getPartPieceAverage(int part, int type)
    {
        int output = 0;
        for(int counter0 = 0;counter0<matchProfiles.size();counter0++)
        {
            for(int counter = 0;counter<4;counter++)
            {
                output += matchProfiles.get(counter0).getPartPieceTotal(part,type,counter);
            }
        }
        output = output/matchProfiles.size();
        return output;
    }

    public int getPieceLocationAverage(int type, int location)
    {
        int output = 0;
        for (int cA=0;cA<matchProfiles.size();cA++)
        {
            for (int cB=0;cB<2;cB++)
            {
                output += matchProfiles.get(cA).getPartPieceTotal(cB,type,location);
            }
        }
        output = output/matchProfiles.size();
        return output;
    }

    public int getPartPieceLocationAverage(int part, int type, int location)
    {
        int output = 0;
        for(int cA=0;cA<matchProfiles.size();cA++)
        {
            output += matchProfiles.get(cA).getPartPieceTotal(part,type,location);
        }
        output = output/matchProfiles.size();
        return output;
    }

    public int getPointTotalAverage()
    {
        int output = 0;
        for(int cA=0;cA<matchProfiles.size();cA++)
        {
            output += matchProfiles.get(cA).getPointTotal();
        }
        output = output/matchProfiles.size();
        return output;
    }

    public int getPenaltyAverage()
    {
        int output = 0;
        for(int cA=0;cA<matchProfiles.size();cA++)
        {
            output += matchProfiles.get(cA).getPenaltyTotal();
        }
        output = output/matchProfiles.size();
        return output;
    }


    public int getTeamNumber()
    {
        return teamNumber;
    }

    public ArrayList<matchProfile> getMatches()
    {
        return matchProfiles;
    }
}
