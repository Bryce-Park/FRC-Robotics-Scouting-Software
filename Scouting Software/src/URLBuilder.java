// import javax.json.*; //https://docs.oracle.com/javaee/7/api/javax/json/package-summary.html

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLBuilder {
	private String url  ="";
	private int paramNum = 0;

	public URLBuilder(String base) {
		url=base;
	}public URLBuilder() {this("https://frc-api.firstinspires.org/v2.0/2019/matches");}//auxiliary
	

	// ACTIVE METHODS //
	public URLBuilder addDiv(String div) {
		url+="/"+div;
		return this;
	}
	public URLBuilder addParam(String paramName, String val) {
		url+=(paramNum==0) ? ("?"+paramName+"="+val) : ("&"+paramName+"="+val);
		paramNum++;
		return this;
	}public URLBuilder addParam(String paramName, int val) {return this.addParam(paramName,val+"");}

	// ACCESSOR METHODS //
	public String getURL() {return url;}
	public URL getURLType() throws Exception {return new URL(url);}


	// SET METHODS //
	public URLBuilder setBase(String base) {
		url=base;
		return this;
	}
}