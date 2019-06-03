import java.util.ArrayList;

public class pitComment
{
    private int teamNumber;
    private String comment;

    public pitComment(ArrayList<String> inputList)
    {
        teamNumber = parseTeamNumber(inputList.get(1));
        //comment = inputList.get(2);
        for(int counter=2;counter<inputList.size();counter++)
        {
            comment = comment + inputList.get(counter)+ " ";
        }
    }

    private int parseTeamNumber(String input)
    {
        int output;
        output = Integer.parseInt(input.substring(0,input.indexOf(".")));
        return output;
    }

    public int getTeamNumber()
    {
        return teamNumber;
    }

    public String getComment()
    {
        return comment;
    }

}
