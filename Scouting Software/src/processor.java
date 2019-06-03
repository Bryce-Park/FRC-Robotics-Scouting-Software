import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

//TODO add sort methods to speed up process

public class processor
{
    private excelReader localExcelReader;
    private excelWriter localExcelWriter;

    public processor () throws Throwable
    {
        localExcelReader = new excelReader();
        localExcelWriter = new excelWriter();
    }

    public void generateSheets(String inputSheet, String commentSheet) throws Throwable
    {
        excelReader localExcelReader = new excelReader();
        //create available team list
        ArrayList<Integer> availableTeams = getAvailableTeamNumbers(inputSheet);

        //get required numbers from user
        ArrayList<Integer> userRequests = getTeamNumbers(availableTeams);
        System.out.println("Begin availableTeam test");
        for(int counter=0;counter<availableTeams.size();counter++)
        {
            System.out.println(availableTeams.get(counter));
        }

        //create pit comment list
        ArrayList<pitComment> pitComments = generatePitComments(commentSheet);

        //compile lists of matches to sent to teams
        ArrayList<teamProfile> teamProfileList = new ArrayList<>();
        for(int cA=0;cA<userRequests.size();cA++)
        {
            ArrayList<matchProfile> matchProfileList = new ArrayList<>();
            ArrayList<pitComment> pitCommentsList = new ArrayList<>();
            for(int cB=0;cB<availableTeams.size();cB++)
            {
                System.out.println("availableTeams: "+availableTeams.get(cB)+" userRequest: "+userRequests.get(cA));
                if(availableTeams.get(cB).equals(userRequests.get(cA)))
                {
                    matchProfileList.add(new matchProfile(localExcelReader.getRow(1+cB,inputSheet)));
                }
            }
            for(int counter=0;counter<pitComments.size();counter++)
            {
                if(pitComments.get(counter).getTeamNumber()==userRequests.get(cA))
                {
                    pitCommentsList.add(pitComments.get(counter));
                }
            }
            System.out.println("Begin match list test");
            for(int counter=0;counter<matchProfileList.size();counter++)
            {
                System.out.println(matchProfileList.get(counter));
            }
            teamProfileList.add(new teamProfile(matchProfileList,pitCommentsList));
        }
        //print team profiles
        for(int cA=0;cA<teamProfileList.size();cA++)
        {
            localExcelWriter.printTeamProfile(teamProfileList.get(cA));
        }
    }

    private ArrayList<Integer> getAvailableTeamNumbers(String inputSheet)
    {
        ArrayList<Integer> output = convertColumnToInteger(localExcelReader.getColumn(1,3,inputSheet));
        System.out.println("Begin getAvailableTeamNumbers Test");
        for(int counter=0;counter<output.size();counter++)
        {
            System.out.println(output.get(counter));
        }
        return output;
    }

    private ArrayList<Integer> getTeamNumbers (ArrayList<Integer> availableTeams) throws Throwable
    {

        ArrayList<Integer> output = new ArrayList<Integer>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int holder = -1;
        boolean isZero = false;
        String temp;
        boolean alreadyInputted;

        while(!isZero)
        {
            System.out.println("Please enter team number");
            try
            {
                if(holder==0)
                {
                    break;
                }
                holder = Integer.parseInt(br.readLine());

                alreadyInputted = false;
                for(int counter=0;counter<output.size();counter++)
                {
                    if(holder == output.get(counter))
                    {
                        alreadyInputted = true;
                    }
                }


                if(findIndex(holder,availableTeams).get(0)==-1)
                {
                    System.out.println("Error: Inputted number could not be found, please try again.");
                }
                else if(alreadyInputted)
                {
                    System.out.println("Error: Inputted number already used");
                }
                else
                {
                    output.add(holder);
                }
            }
            catch(NumberFormatException nfe)
            {
                System.err.println("Please use numbers!");
            }
        }
        return output;

    }

    private ArrayList<pitComment> generatePitComments(String inputSheet)
    {
        ArrayList<pitComment> output= new ArrayList<>();
        ArrayList<String> tempString;
        int commentCount = localExcelReader.getColumn(1,1,inputSheet).size();
        if(localExcelReader.getRow(1,inputSheet).get(0).length()!=0)
        {
            for(int counter=0;counter<commentCount;counter++)
            {
                tempString = new ArrayList<>();
                tempString = localExcelReader.getRow(counter+1,inputSheet);
                output.add(new pitComment(tempString));
            }
        }
        return output;
    }

    private ArrayList<Integer> generateGetMatchList(int requestedTeam)
    {
        ArrayList<Integer> output = new ArrayList<>();
        autoScoutLiason localAutoScourLiason = new autoScoutLiason();

        for(int counter=0;counter<)

        return output;
    }

    private ArrayList<Integer> convertToIntegerList(ArrayList<String> inputList) //causes unknown issues
    {
        ArrayList<Integer> numberInputList = new ArrayList<Integer>();
        String tempString;
        for(int counter=1;counter<inputList.size()-1;counter++)
        {
            if(inputList.get(counter).contains("["))
            {
                tempString = inputList.get(counter);
                numberInputList.add(Integer.parseInt(tempString.substring(tempString.indexOf("[")+1,tempString.indexOf("]"))));
            }
            else if(inputList.get(counter).contains("."))
            {
                numberInputList.add(Integer.parseInt(inputList.get(counter).substring(0,inputList.get(counter).indexOf("."))));
            }
            else
            {
                numberInputList.add(Integer.parseInt(inputList.get(counter)));
            }

            }

            return numberInputList;

        /*
        ArrayList<Integer> output = new ArrayList<Integer>();
        for(int counter=0;counter<inputList.size();counter++)
        {
            output.add(Integer.parseInt(inputList.get(counter)));
        }
        return output;
        */
    }

    private ArrayList<Integer> convertColumnToInteger(ArrayList<String> inputList)
    {
        ArrayList<Integer> output = new ArrayList<>();
        for(int counter=0;counter<inputList.size();counter++)
        {
            if(inputList.get(counter).contains("."))
            {
                output.add(Integer.parseInt(inputList.get(counter).substring(0,inputList.get(counter).indexOf("."))));
            }
        }
        return output;
    }

    private ArrayList<Integer> findIndex(int target, ArrayList<Integer> inputList)
    {
        ArrayList<Integer> output = new ArrayList<Integer>();
        int tracker = 0;
        boolean isLooking = true;
        while(tracker < inputList.size())
        {
            if(inputList.get(tracker)==target)
            {
                output.add(tracker);
            }
                tracker++;
        }
        if(output.size()<1)
        {
            System.out.println("Error: Can't find inputted number");
            output.add(-1);
        }
        return output;
    }

}