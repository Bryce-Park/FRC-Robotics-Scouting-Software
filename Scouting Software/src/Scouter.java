// import javax.json.JsonReader; //https://docs.oracle.com/javaee/7/api/javax/json/package-summary.html
import javax.json.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import java.util.Scanner;
public class Scouter {
	
	/* FIELDS */
	private final String USER_AGENT = "Mozilla/5.0";
	private int matchNum = 1;
	private String eventCode = "CALA";
	private String authCode;
	private String urls="https://frc-api.firstinspires.org";
	private URL url;
	//private URLBuilder build=new URLBuilder();
	private int responseCode=0;
	private JsonObject MATCH;
	private JsonObject MATCH_DATA;
	private JsonArray TEAMS;
	/* END FIELDS */

	// Can now access non-static fields //
	public Scouter() throws Exception {
		File auth=new File("auth.txt");
		Scanner sc=new Scanner(auth);
		authCode=sc.nextLine();

		GET();
	}


	// Send the GET Request //
	public void GET(int m) throws Exception {
		matchNum=m;
		URLBuilder build = new URLBuilder();
		build.addDiv(eventCode).addParam("tournamentLevel","Qualification").addParam("matchNumber",matchNum);
		url=build.getURLType();
		// Opening CONNECTION //
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		
		// Setting HEADERS //
		setHeader(connection,"Accept","application/json");
		setHeader(connection,"Authorization",authCode);
		
		responseCode = connection.getResponseCode();

		/* For getting string version
		*/
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		/* Making response
		*/
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// RESPONSE is stringified JSON

		JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
		JsonObject obj = jsonReader.readObject();
		jsonReader.close();

		MATCH=obj;
		MATCH_DATA=MATCH.getJsonArray("Matches").getJsonObject(0);
		TEAMS=MATCH_DATA.getJsonArray("teams");

	}public void GET() throws Exception {this.GET(matchNum);}//auxiliary
	
	// Ease of access // 
	private void setHeader(HttpURLConnection con, String name, String val) {con.setRequestProperty(name,val);}

	// SET Methods //
	public void setMatch(int m) {matchNum=m;}
	public void setEventCode(String code) {eventCode=code.toUpperCase();}
	public void setAuth(String code) {authCode=code;}
	
	// Accessor Methods //
	public int getResponseCode() {return responseCode;}
	public int getMatchNumber() {return matchNum;}
	public String getEventCode() {return eventCode;}
	public String getURL() {return urls;}
	public URL getURLType() {return url;}

	// ACCESSING JSON VALUES //

	
	// END MATCH GET INFO
	public String getStartTime() {return MATCH_DATA.getString("actualStartTime");}
	public String getTournamentLevel() {return MATCH_DATA.getString("tournamentLevel");}
	
	// public int getMatchNumber() {return matchNum;}
	public int getRedFinal() {return MATCH_DATA.getInt("scoreRedFinal");}
	public int getRedFoul() {return MATCH_DATA.getInt("scoreRedFoul");}
	public int getRedAuto() {return MATCH_DATA.getInt("scoreRedAuto");}
	public int getBlueFinal() {return MATCH_DATA.getInt("scoreBlueFinal");}
	public int getBlueFoul() {return MATCH_DATA.getInt("scoreBlueFoul");}
	public int getBlueAuto() {return MATCH_DATA.getInt("scoreBlueAuto");}
	public int getRed1() {return TEAMS.getJsonObject(0).getInt("teamNumber");}
	public int getRed2() {return TEAMS.getJsonObject(1).getInt("teamNumber");}
	public int getRed3() {return TEAMS.getJsonObject(2).getInt("teamNumber");}
	public int getBlue1() {return TEAMS.getJsonObject(3).getInt("teamNumber");}
	public int getBlue2() {return TEAMS.getJsonObject(4).getInt("teamNumber");}
	public int getBlue3() {return TEAMS.getJsonObject(5).getInt("teamNumber");}
}