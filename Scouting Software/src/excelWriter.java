import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class excelWriter
{
    public excelWriter() throws Throwable
    {

    }

    public static void printTeamProfile(teamProfile inputTeam) throws Throwable
    {
        SXSSFWorkbook wb = new SXSSFWorkbook(-1); // turn off auto-flushing and accumulate all rows in memory
        Sheet sh = wb.createSheet();
        int dataStartRow = 3;
        int matchCommentStart = 2;

        Row teamNumberRow = sh.createRow(0);
        Cell cell = teamNumberRow.createCell(0);
        cell.setCellValue("Team Number: "+inputTeam.getTeamNumber());

        Row pitCommentsStartRow = sh.createRow(1);
        cell = pitCommentsStartRow.createCell(0);
        cell.setCellValue("Pit Comments");

        Row commentRows = sh.createRow(2);
        int commentRowNum = 0;
        for(int listTracker = 0; listTracker < inputTeam.getPitComments().size(); listTracker++)
        {
                commentRows = sh.createRow(2+commentRowNum);
                cell = commentRows.createCell(0);
                cell.setCellValue(inputTeam.getPitComments().get(listTracker).getComment());

                commentRowNum++;
                matchCommentStart++;
        }

        dataStartRow += matchCommentStart;

        Row commentsStartRow = sh.createRow(1+matchCommentStart);
        cell = commentsStartRow.createCell(0);
        cell.setCellValue("Match Number");
        cell = commentsStartRow.createCell(1);
        cell.setCellValue("Comments");

        commentRowNum = 0;
        for(int listTracker = 0; listTracker < inputTeam.getMatches().size(); listTracker++)
        {
            if(inputTeam.getMatches().get(listTracker).checkComment())
            {
                commentRows = sh.createRow(2+commentRowNum+matchCommentStart);
                cell = commentRows.createCell(0);
                cell.setCellValue(inputTeam.getMatches().get(listTracker).getMatchNumber());
                cell = commentRows.createCell(1);
                cell.setCellValue(inputTeam.getMatches().get(listTracker).getComment());
                commentRowNum++;
                dataStartRow++;
            }
        }

        Row headerRow = sh.createRow(0+dataStartRow);
        cell = headerRow.createCell(0);
        cell.setCellValue("Match Number");
        cell = headerRow.createCell(1);
        cell.setCellValue("Sandstorm Exit");
        cell = headerRow.createCell(2);
        cell.setCellValue("TeleOp Enter");
        cell = headerRow.createCell(3);
        cell.setCellValue("Point Total");
        cell = headerRow.createCell(4);
        cell.setCellValue("Penalty Total");
        cell = headerRow.createCell(5);
        cell.setCellValue("Hatch Total");
        cell = headerRow.createCell(6);
        cell.setCellValue("Cargo Total");

        //header match totals
        int headerTracker = 0;
        String locationHolder;
        String pieceHolder;
        for(int cA=0;cA<4;cA++)
        {
            for(int cB=0;cB<2;cB++)
            {
                if(cB==0)
                {
                    pieceHolder = "H";
                }
                else
                {
                    pieceHolder = "C";
                }
                if(cA==0)
                {
                    locationHolder = "C.S. ";
                }
                else if(cA==1)
                {
                    locationHolder = "R.L. ";
                }
                else if(cA==2)
                {
                    locationHolder = "R.M. ";
                }
                else
                {
                    locationHolder = "R.H. ";
                }
                cell = headerRow.createCell(7+headerTracker);
                cell.setCellValue(locationHolder + pieceHolder + " Total");
                headerTracker++;
            }
        }
        //header sandstorm totals
        headerTracker = 0;
        for(int cA=0;cA<4;cA++)
        {
            for(int cB=0;cB<2;cB++)
            {
                if(cB==0)
                {
                    pieceHolder = "H";
                }
                else
                {
                    pieceHolder = "C";
                }
                if(cA==0)
                {
                    locationHolder = "C.S. ";
                }
                else if(cA==1)
                {
                    locationHolder = "R.L. ";
                }
                else if(cA==2)
                {
                    locationHolder = "R.M. ";
                }
                else
                {
                    locationHolder = "R.H. ";
                }
                cell = headerRow.createCell(15+headerTracker);
                cell.setCellValue("S " + locationHolder + pieceHolder + " Total");
                headerTracker++;
            }
        }

        //header teleOp totals
        headerTracker = 0;
        for(int cA=0;cA<4;cA++)
        {
            for(int cB=0;cB<2;cB++)
            {
                if(cB==0)
                {
                    pieceHolder = "H";
                }
                else
                {
                    pieceHolder = "C";
                }
                if(cA==0)
                {
                    locationHolder = "C.S. ";
                }
                else if(cA==1)
                {
                    locationHolder = "R.L. ";
                }
                else if(cA==2)
                {
                    locationHolder = "R.M. ";
                }
                else
                {
                    locationHolder = "R.H. ";
                }
                cell = headerRow.createCell(23+headerTracker);
                cell.setCellValue("T " + locationHolder + pieceHolder + " Total");
                headerTracker++;
            }
        }

        //match data
        int mDStart = 2;
        for(int rownum = 0; rownum < inputTeam.getMatches().size(); rownum++)
        {
            int totalTracker = 0;
            Row row = sh.createRow(rownum+mDStart+dataStartRow);
            cell = row.createCell(0);
            cell.setCellValue(inputTeam.getMatches().get(rownum).getMatchNumber());
            cell = row.createCell(1);
            cell.setCellValue(inputTeam.getMatches().get(rownum).getSandStorm().getExitValue());
            cell = row.createCell(2);
            cell.setCellValue(inputTeam.getMatches().get(rownum).getTeleOp().getEnterValue());
            cell = row.createCell(3);
            cell.setCellValue(inputTeam.getMatches().get(rownum).getPointTotal());
            cell = row.createCell(4);
            cell.setCellValue(inputTeam.getMatches().get(rownum).getPenaltyTotal());
            cell = row.createCell(5);
            for(int cA=0;cA<4;cA++)
            {
                    totalTracker += inputTeam.getMatches().get(rownum).getPartPieceTotal(0, 0, cA) + inputTeam.getMatches().get(rownum).getPartPieceTotal(1, 0, cA);
            }
            cell.setCellValue(totalTracker);
            totalTracker=0;
            cell = row.createCell(6);
            for(int cA=0;cA<4;cA++)
            {
                    totalTracker += inputTeam.getMatches().get(rownum).getPartPieceTotal(0,1,cA)+inputTeam.getMatches().get(rownum).getPartPieceTotal(1,1,cA);
            }
            cell.setCellValue(totalTracker);

            //cell.setCellValue(inputTeam.getMatches().get(rownum).getPieceTotal(1));

            //match totals
            int tracker = 0;
            for(int cA=0;cA<4;cA++)
            {
                for(int cB=0;cB<2;cB++)
                {

                        cell = row.createCell(7+tracker);
                        cell.setCellValue(inputTeam.getMatches().get(rownum).getPartPieceTotal(0,cB,cA)+inputTeam.getMatches().get(rownum).getPartPieceTotal(1,cB,cA));
                        tracker++;
                }
            }
            //sandstorm input
            cell = row.createCell(15);
            cell.setCellValue(inputTeam.getMatches().get(rownum).getSandstormDriveMethod());
            tracker = 0;

            int testTracker = 0;
            for(int cA=0;cA<4;cA++)
            {
                for(int cB=0;cB<2;cB++)
                {
                    cell = row.createCell(15+tracker);
                    cell.setCellValue(inputTeam.getMatches().get(rownum).getPartPieceTotal(0,cB,cA));
                    tracker++;
                }
                testTracker++;
            }
            tracker = 0;
            for(int cA=0;cA<4;cA++)
            {
                for(int cB=0;cB<2;cB++)
                {
                    cell = row.createCell(23+tracker);
                    cell.setCellValue(inputTeam.getMatches().get(rownum).getPartPieceTotal(1,cB,cA));
                    tracker++;
                }
            }

            // manually control how rows are flushed to disk
            if(rownum % 100 == 0)
            {
                ((SXSSFSheet)sh).flushRows(100); // retain 100 last rows and flush all others

                // ((SXSSFSheet)sh).flushRows() is a shortcut for ((SXSSFSheet)sh).flushRows(0),
                // this method flushes all rows
            }

        }

        //team total averages
        
        int tracker;
        int totalTracker = 0;
        //28
        Row row = sh.createRow(1+dataStartRow);
        cell = row.createCell(0);
        cell.setCellValue("Average");
        cell = row.createCell(1);
        //cell.setCellValue(inputTeam.getMatches().get(rownum).getSandStorm().getExitValue());
        cell = row.createCell(2);
        //cell.setCellValue(inputTeam.getMatches().get(rownum).getTeleOp().getEnterValue());
        cell = row.createCell(3);
        cell.setCellValue(inputTeam.getPointTotalAverage());
        cell = row.createCell(4);
        cell.setCellValue(inputTeam.getPenaltyAverage());
        cell = row.createCell(5);
        for(int cA=0;cA<4;cA++)
        {
                for(int cC=0;cC<inputTeam.getMatches().size();cC++)
                {
                    totalTracker += inputTeam.getMatches().get(cC).getPartPieceTotal(0, 0, cA) + inputTeam.getMatches().get(cC).getPartPieceTotal(1, 0, cA);
                }
        }
        totalTracker = totalTracker/inputTeam.getMatches().size();
        cell.setCellValue(totalTracker);

        totalTracker=0;
        cell = row.createCell(6);
        for(int cA=0;cA<4;cA++)
        {
            for(int cC=0;cC<inputTeam.getMatches().size();cC++)
            {
                totalTracker += inputTeam.getMatches().get(cC).getPartPieceTotal(0, 1, cA) + inputTeam.getMatches().get(cC).getPartPieceTotal(1, 1, cA);
            }
        }
        totalTracker = totalTracker/inputTeam.getMatches().size();
        cell.setCellValue(totalTracker);


        tracker = 0;
        totalTracker = 0;
        for(int cA=0;cA<4;cA++)
        {
            for(int cB=0;cB<2;cB++)
            {
                cell = row.createCell(7+tracker);
                for(int cC=0;cC<inputTeam.getMatches().size();cC++)
                {
                    totalTracker += inputTeam.getMatches().get(cC).getPartPieceTotal(0,cB,cA)+inputTeam.getMatches().get(cC).getPartPieceTotal(1,cB,cA);
                }
                cell.setCellValue(totalTracker/inputTeam.getMatches().size());
                totalTracker = 0;
                tracker++;
            }
        }

        tracker = 0;
        totalTracker = 0;
        for(int cA=0;cA<4;cA++)
        {
            for(int cB=0;cB<2;cB++)
            {
                cell = row.createCell(15+tracker);
                for(int cC=0;cC<inputTeam.getMatches().size();cC++)
                {
                    totalTracker += inputTeam.getMatches().get(cC).getPartPieceTotal(0,cB,cA);
                }
                cell.setCellValue(totalTracker/inputTeam.getMatches().size());
                totalTracker = 0;
                tracker++;
            }
        }

        tracker = 0;
        totalTracker = 0;
        for(int cA=0;cA<4;cA++)
        {
            for(int cB=0;cB<2;cB++)
            {
                cell = row.createCell(23+tracker);
                for(int cC=0;cC<inputTeam.getMatches().size();cC++)
                {
                    totalTracker += inputTeam.getMatches().get(cC).getPartPieceTotal(1,cB,cA);
                }
                cell.setCellValue(totalTracker/inputTeam.getMatches().size());
                totalTracker = 0;
                tracker++;
            }
        }

        // manually control how rows are flushed to disk
        if(1 % 100 == 0)
        {
            ((SXSSFSheet)sh).flushRows(100); // retain 100 last rows and flush all others
        }

        String fileName = "Team "+inputTeam.getTeamNumber()+" Profile.xlsx";
        FileOutputStream out = new FileOutputStream(fileName);
        //FileOutputStream out = new FileOutputStream("C:/Users/jpark/IdeaProjects/roboticsAttendanceSoftware");
        wb.write(out);
        out.close();

        // dispose of temporary files backing this workbook on disk
        wb.dispose();
    }

}
