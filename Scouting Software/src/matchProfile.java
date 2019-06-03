import java.util.ArrayList;

public class matchProfile
{
    private int matchNumber;
    private int teamNumber;
    private int pointTotal;
    private int penaltyTotal;
    private int sandstormDriveMethod;
    private sandstorm matchSandstorm;
    private teleOp matchTeleOp;
    private ArrayList<hatch> totalHatches;
    private ArrayList<cargo> totalCargoes;
    private String comment;
    private boolean hasComment;
    private ArrayList<String> savedInputList;

    public matchProfile()
    {

    }

    public matchProfile(ArrayList<String> inputList)
    {
        if(inputList.size()!=25)
        {
            hasComment = false;
        }
        ArrayList<Integer> numberInputList = convertInputToNumbers(inputList);
        createSandstormTeleOp(numberInputList);
        parseOtherData(numberInputList);
        parseComment(inputList);
        savedInputList = partiallyEditInputList(inputList);
        checkGetComment(inputList);

        combineSandStormTeleOp();
    }

    public matchProfile(int inputTeamNumber, sandstorm inputSandstorm, teleOp inputTeleOp)
    {
        teamNumber = inputTeamNumber;
        matchSandstorm = inputSandstorm;
        matchTeleOp = inputTeleOp;

        this.combineSandStormTeleOp();
    }

    public void inputSandstorm(sandstorm inputSandstorm)
    {
        matchSandstorm = inputSandstorm;
        this.combineSandStormTeleOp();
    }

    public void inputTeleOp(teleOp inputTeleOp)
    {
        matchTeleOp = inputTeleOp;
        this.combineSandStormTeleOp();
    }

    private ArrayList<Integer> convertInputToNumbers(ArrayList<String> inputList)
    {
        ArrayList<Integer> numberInputList = new ArrayList<Integer>();
        String tempString;
        int intComment = 1;
        if(!hasComment)
        {
            intComment = 0;
        }
        for(int counter=1;counter<inputList.size()-intComment;counter++)
        {
            try
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
            catch (Exception e)
            {
            }
        }
        return numberInputList;
    }

    private void parseComment(ArrayList<String> inputList)
    {
        if(inputList.size()==24)
        {
            comment = inputList.get(23);
        }
        else
        {
            comment = " ";
        }
    }

    private void createSandstormTeleOp(ArrayList<Integer> numberInputList)
    {
        ArrayList<hatch> tempSandstormHatchList = new ArrayList<hatch>();
        ArrayList<cargo> tempSandstormCargoList =  new ArrayList<cargo>();

        ArrayList<hatch> tempTeleOpHatchList = new ArrayList<hatch>();
        ArrayList<cargo> tempTeleOpCargoList =  new ArrayList<cargo>();

        matchNumber = numberInputList.get(0);
        teamNumber = numberInputList.get(1);
        sandstormDriveMethod = numberInputList.get(2);

        int tracker = 0;
        for(int cA=4;cA<12;cA++) //numbers specific to spreadsheet format
        {
            if(cA%2==0)
            {
                tempSandstormHatchList.add(new hatch(tracker, numberInputList.get(cA)));
            }
            else if(cA%2==1)
            {
                tempSandstormCargoList.add(new cargo(tracker,numberInputList.get(cA)));
                tracker++;
            }

        }

        matchSandstorm = new sandstorm(numberInputList.get(2),tempSandstormHatchList,tempSandstormCargoList);

        tracker = 0;
        for(int cA=12;cA<20;cA++) //numbers specific to spreadsheet format
        {
            if(cA%2==0)
            {
                tempTeleOpHatchList.add(new hatch(tracker, numberInputList.get(cA)));
            }
            else if(cA%2==1)
            {
                tempTeleOpCargoList.add(new cargo(tracker,numberInputList.get(cA)));
                tracker++;
            }

        }

        matchTeleOp = new teleOp(numberInputList.get(20),tempTeleOpHatchList,tempTeleOpCargoList);
    }

    private ArrayList<String> partiallyEditInputList(ArrayList<String> inputList)
    {
        ArrayList<String> toEditList = inputList;
        toEditList.remove(0);
        toEditList.remove(1);
        System.out.println("Begin toEditList test");
        System.out.println(toEditList.get(2));
        toEditList.set(2,toEditList.get(2).substring(0,toEditList.get(2).indexOf("[")));
        //toEditList.set(19,toEditList.get(19).substring(0,toEditList.get(19).indexOf("(")));
                //19
        return toEditList;
    }

    private void combineSandStormTeleOp()
    {
        totalHatches = this.combineHatchLists(matchSandstorm.getHatches(),matchTeleOp.getHatches());
        totalCargoes = this.combineCargoLists(matchSandstorm.getCargo(),matchTeleOp.getCargo());
    }

    private void parseOtherData(ArrayList<Integer> numberInputList)
    {
        matchNumber = numberInputList.get(0);
        teamNumber = numberInputList.get(1);
        pointTotal = numberInputList.get(21);
        sandstormDriveMethod = numberInputList.get(3);
        penaltyTotal = numberInputList.get(22);
    }

    private void checkGetComment(ArrayList<String> inputList)
    {
        if(inputList.size()>=23)
        {
            comment = inputList.get(22);
            hasComment = true;
        }
        else
        {
            comment = "no comment";
            hasComment = false;
        }
    }

    private void calculatePointValue()
    {
        pointTotal = 0;

        int sandstormExitValue =matchSandstorm.getExitValue();
        if(sandstormExitValue == 1)
        {
            pointTotal += 3;
        }
        else if(sandstormExitValue == 2)
        {
            pointTotal += 6;
        }

        int teleOpEnterValue =matchTeleOp.getEnterValue();
        if(teleOpEnterValue == 1)
        {
            pointTotal += 3;
        }
        else if(teleOpEnterValue == 2)
        {
            pointTotal += 6;
        }
        else if(teleOpEnterValue == 3)
        {
            pointTotal += 12;
        }

        for(int totalHatchCounter = 0; totalHatchCounter<totalHatches.size();totalHatchCounter++)
        {
            for(int hatchCounter=0;hatchCounter<totalHatches.get(totalHatchCounter).getTotal();hatchCounter++)
            {
                pointTotal += 2;
            }
        }

        for(int totalCargoCounter = 0; totalCargoCounter<totalCargoes.size();totalCargoCounter++)
        {
            for(int hatchCounter=0;hatchCounter<totalCargoes.get(totalCargoCounter).getTotal();hatchCounter++)
            {
                pointTotal += 3;
            }
        }
    }

    private ArrayList<placeable> combinePlacableLists(ArrayList<placeable> placeableList0, ArrayList<placeable> placeableList1)
    {
        ArrayList<placeable> output;
        output = placeableList0;
        for(int counter = 0; counter<placeableList1.size();counter++)
        {
            output.add(placeableList1.get(counter));
        }

        return output;
    }

    private ArrayList<hatch> combineHatchLists(ArrayList<hatch> hatchList0, ArrayList<hatch> hatchList1)
    {
        ArrayList<hatch> output;
        output = hatchList0;
        for(int counter = 0; counter<hatchList1.size();counter++)
        {
            output.add(hatchList1.get(counter));
        }

        return output;
    }

    private ArrayList<cargo> combineCargoLists(ArrayList<cargo> caroList0, ArrayList<cargo> caroList1)
    {
        ArrayList<cargo> output;
        output = caroList0;
        for(int counter = 0; counter<caroList1.size();counter++)
        {
            output.add(caroList1.get(counter));
        }

        return output;
    }

    public int getPieceTotal(int piece)
    {
        int output = 0;
        if(piece == 0)
        {
            for(int counter=0;counter<totalHatches.size();counter++)
            {
                output += totalHatches.get(counter).getTotal();
            }
            return output;
        }
        else if(piece == 1)
        {
            for (int counter=0;counter<totalCargoes.size();counter++)
            {
                output += totalCargoes.get(counter).getTotal();
            }
            return output;
        }
        return -1;
    }

    public int getPieceTotalLocation(int piece, int location)
    {
        int total = 0;
        if(location == 0)
        {
            if(piece == 0)
            {
                for(int counter=0;counter<totalHatches.size();counter++)
                {
                    if(totalHatches.get(counter).getLocation()==0)
                    {
                        total += totalHatches.get(counter).getTotal();
                    }
                }
                return total;
            }
            else if(piece == 1)
            {
                for(int counter=0;counter<totalCargoes.size();counter++)
                {
                    if(totalCargoes.get(counter).getLocation()==0)
                    {
                        total += totalCargoes.get(counter).getTotal();
                    }
                }
                return total;
            }
        }
        else if(location == 1)
        {
            if(piece == 0)
            {
                for(int counter=0;counter<totalHatches.size();counter++)
                {
                    if(totalHatches.get(counter).getLocation()==1)
                    {
                        total += totalHatches.get(counter).getTotal();
                    }
                }
                return total;
            }
            else if(piece == 1)
            {
                for(int counter=0;counter<totalCargoes.size();counter++)
                {
                    if(totalCargoes.get(counter).getLocation()==1)
                    {
                        total += totalCargoes.get(counter).getTotal();
                    }
                }
                return total;
            }
        }
        else if(location == 2)
        {
            if(piece == 0)
            {
                for(int counter=0;counter<totalHatches.size();counter++)
                {
                    if(totalHatches.get(counter).getLocation()==2)
                    {
                        total += totalHatches.get(counter).getTotal();
                    }
                }
                return total;
            }
            else if(piece == 1)
            {
                for(int counter=0;counter<totalCargoes.size();counter++)
                {
                    if(totalCargoes.get(counter).getLocation()==2)
                    {
                        total += totalCargoes.get(counter).getTotal();
                    }
                }
                return total;
            }
        }
        else if(location == 3)
        {
            if(piece == 0)
            {
                for(int counter=0;counter<totalHatches.size();counter++)
                {
                    if(totalHatches.get(counter).getLocation()==3)
                    {
                        total += totalHatches.get(counter).getTotal();
                    }
                }
                return total;
            }
            else if(piece == 1)
            {
                for(int counter=0;counter<totalCargoes.size();counter++)
                {
                    if(totalCargoes.get(counter).getLocation()==3)
                    {
                        total += totalCargoes.get(counter).getTotal();
                    }
                }
                return total;
            }
        }
        System.out.println("Invalid number inputted into .getPieceTotal, please check");
        return -1;
    }

    public int getPartPieceTotal(int part, int piece, int location)
    {
        int total = 0;
        if(location == 0)
        {
            if(part == 0)
            {
                if(piece == 0)
                {
                    for(int counter=0;counter<matchSandstorm.getHatches().size();counter++)
                    {
                        if(matchSandstorm.getHatches().get(counter).getLocation()==0)
                        {
                            total += matchSandstorm.getHatches().get(counter).getTotal();
                        }
                    }
                    return total;
                }
                else if(piece == 1)
                {
                    for(int counter=0;counter<matchSandstorm.getCargo().size();counter++)
                    {
                        if(matchSandstorm.getCargo().get(counter).getLocation()==0)
                        {
                            total += matchSandstorm.getCargo().get(counter).getTotal();
                        }
                    }
                    return total;
                }
            }
            else if(part == 1)
            {
                if(piece == 0)
                {
                    for(int counter=0;counter<matchTeleOp.getHatches().size();counter++)
                    {
                        if(matchTeleOp.getHatches().get(counter).getLocation()==0)
                        {
                            total += matchTeleOp.getHatches().get(counter).getTotal();
                        }
                    }
                    return total;
                }
                else if(piece == 1)
                {
                    for(int counter=0;counter<matchTeleOp.getCargo().size();counter++)
                    {
                        if(matchTeleOp.getCargo().get(counter).getLocation()==0)
                        {
                            total += matchTeleOp.getCargo().get(counter).getTotal();
                        }
                    }
                    return total;
                }
            }
        }
        else if(location == 1)
        {
            if(part == 0)
            {
                if(piece == 0)
                {
                    for(int counter=0;counter<matchSandstorm.getHatches().size();counter++)
                    {
                        if(matchSandstorm.getHatches().get(counter).getLocation()==1)
                        {
                            total += matchSandstorm.getHatches().get(counter).getTotal();
                        }
                    }
                    return total;
                }
                else if(piece == 1)
                {
                    for(int counter=0;counter<matchSandstorm.getCargo().size();counter++)
                    {
                        if(matchSandstorm.getCargo().get(counter).getLocation()==1)
                        {
                            total += matchSandstorm.getCargo().get(counter).getTotal();
                        }
                    }
                    return total;
                }
            }
            else if(part == 1)
            {
                if(piece == 0)
                {
                    for(int counter=0;counter<matchTeleOp.getHatches().size();counter++)
                    {
                        if(matchTeleOp.getHatches().get(counter).getLocation()==1)
                        {
                            total += matchTeleOp.getHatches().get(counter).getTotal();
                        }
                    }
                    return total;
                }
                else if(piece == 1)
                {
                    for(int counter=0;counter<matchTeleOp.getCargo().size();counter++)
                    {
                        if(matchTeleOp.getCargo().get(counter).getLocation()==1)
                        {
                            total += matchTeleOp.getCargo().get(counter).getTotal();
                        }
                    }
                    return total;
                }
            }
        }
        else if(location == 2)
        {
            if(part == 0)
            {
                if(piece == 0)
                {
                    for(int counter=0;counter<matchSandstorm.getHatches().size();counter++)
                    {
                        if(matchSandstorm.getHatches().get(counter).getLocation()==2)
                        {
                            total += matchSandstorm.getHatches().get(counter).getTotal();
                        }
                    }
                    return total;
                }
                else if(piece == 1)
                {
                    for(int counter=0;counter<matchSandstorm.getCargo().size();counter++)
                    {
                        if(matchSandstorm.getCargo().get(counter).getLocation()==2)
                        {
                            total += matchSandstorm.getCargo().get(counter).getTotal();
                        }
                    }
                    return total;
                }
            }
            else  if(part == 1)
            {
                if(piece == 0)
                {
                    for(int counter=0;counter<matchTeleOp.getHatches().size();counter++)
                    {
                        if(matchTeleOp.getHatches().get(counter).getLocation()==2)
                        {
                            total += matchTeleOp.getHatches().get(counter).getTotal();
                        }
                    }
                    return total;
                }
                else if(piece == 1)
                {
                    for(int counter=0;counter<matchTeleOp.getCargo().size();counter++)
                    {
                        if(matchTeleOp.getCargo().get(counter).getLocation()==2)
                        {
                            total += matchTeleOp.getCargo().get(counter).getTotal();
                        }
                    }
                    return total;
                }
            }
        }
        else if(location == 3)
        {
            if(part == 0)
            {
                if(piece == 0)
                {
                    for(int counter=0;counter<matchSandstorm.getHatches().size();counter++)
                    {
                        if(matchSandstorm.getHatches().get(counter).getLocation()==3)
                        {
                            total += matchSandstorm.getHatches().get(counter).getTotal();
                        }
                    }
                    return total;
                }
                else if(piece == 1)
                {
                    for(int counter=0;counter<matchSandstorm.getCargo().size();counter++)
                    {
                        if(matchSandstorm.getCargo().get(counter).getLocation()==3)
                        {
                            total += matchSandstorm.getCargo().get(counter).getTotal();
                        }
                    }
                    return total;
                }
            }
            else if(part == 1)
            {
                if(piece == 0)
                {
                    for(int counter=0;counter<matchTeleOp.getHatches().size();counter++)
                    {
                        if(matchTeleOp.getHatches().get(counter).getLocation()==3)
                        {
                            total += matchTeleOp.getHatches().get(counter).getTotal();
                        }
                    }
                    return total;
                }
                else if(piece == 1)
                {
                     for(int counter=0;counter<matchTeleOp.getCargo().size();counter++)
                    {
                        if(matchTeleOp.getCargo().get(counter).getLocation()==3)
                        {
                            total += matchTeleOp.getCargo().get(counter).getTotal();
                        }
                    }
                    return total;
                }
            }
        }
        return -1;
    }

    public int getMatchNumber()
    {
        return matchNumber;
    }

    public int getSandstormDriveMethod()
    {
        return sandstormDriveMethod;
    }

    public sandstorm getSandStorm()
    {
        return matchSandstorm;
    }

    public teleOp getTeleOp()
    {
        return matchTeleOp;
    }

    public ArrayList<hatch> getTotalHatches() {
        return totalHatches;
    }

    public ArrayList<cargo> getTotalCargoes()
    {
        return totalCargoes;
    }

    public int getTeamNumber()
    {
        return teamNumber;
    }

    public int getPointTotal()
    {
        return pointTotal;
    }

    public int getPenaltyTotal()
    {
        return penaltyTotal;
    }

    public boolean checkComment()
    {
        return hasComment;
    }

    public String getComment()
    {
        return comment;
    }

    public ArrayList<String> getSavedInputList()
    {
        return savedInputList;
    }

}
