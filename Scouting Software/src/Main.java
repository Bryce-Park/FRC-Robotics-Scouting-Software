import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args) throws Throwable
    {
        //Scouter testScout = new Scouter();

        String inputSheet = "C:/Users/jpark/IdeaProjects/Scouting Software/FRC 2019 Scouting Form (Lancaster) (Responses).xlsx";
        String pitCommentInputSheet = "C:/Users/jpark/IdeaProjects/Scouting Software/FRC 2019 Pit Scouting (Lancaster) (Responses).xlsx";
        processor processorTester = new processor();
        processorTester.generateSheets(inputSheet,pitCommentInputSheet);

    }
}

