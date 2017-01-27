package control.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import model.doi.DOI;
import model.isbn.ISBN;
import view.ExceptionDialog;

/**
 * Provides static access to network functionalities.
 * 
 * @author Miklas Boskamp
 */
public class NetUtils {

	public static String ERROR = "error";

	/**
	 * This method checks, if the runtime device has Internet access, by firing
	 * a request to the google DNS server.
	 * 
	 * @return <code>True</code> if google answered to the request and
	 *         <code>false</code> if there was no answer.
	 */
	public static boolean isNetAvailable() {
		try {
			final URL url = new URL("google-public-dns-a.google.com");
			final URLConnection conn = url.openConnection();
			conn.connect();
			return true;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Fires a request to the ISBN database. Returns the returned JSON structure
	 * as string.
	 * 
	 * @param isbn
	 *            the ISBN number to lookup in the database.
	 * @return The JSON response string
	 */
	public static String fireISBNRequest(ISBN isbn) {
		String url = "https://www.googleapis.com/books/v1/volumes";
		Charset charset = StandardCharsets.UTF_8;
		String paramName = "q";
		String param = "ISBN";

		String query = "";

		try {
			query = String.format(paramName + "=%s:%s", URLEncoder.encode(param, charset.toString()), isbn.toString());
			return fireRequest(new URL(url + "?" + query));
		} catch (UnsupportedEncodingException e) {
			//Should not occur as the encoding is set to UTF-8
			ExceptionDialog exDialog = new ExceptionDialog(e, "011"); // Fehler:011
			exDialog.showEcxeptionDialog();
			//e.printStackTrace();
		} catch (MalformedURLException e) {
			//Invalid URL
			ExceptionDialog exDialog = new ExceptionDialog(e, "012"); // Fehler:012
			exDialog.showEcxeptionDialog();
			//e.printStackTrace();
		}
		return ERROR;
	}

	/**
	 * Fires a request to the DOI database. Returns the returned JSON structure
	 * as a string.
	 * 
	 * @param doi
	 *            The DOI to lookup in the database.
	 * @return the JSON response string
	 */
	public static String fireDOIRequest(DOI doi) {
		String url = "http://api.crossref.org/works/" + doi.toString();
		try {
			return fireRequest(new URL(url));
		} catch (MalformedURLException e) {
			ExceptionDialog exDialog = new ExceptionDialog(e, "012"); // Fehler:012
			exDialog.showEcxeptionDialog();
			//e.printStackTrace();
		}
		return ERROR;
	}

	/**
	 * Fires a request to the given {@link URL} and returns the response body.
	 * 
	 * @param url
	 *            The {@link URL} that will be requested
	 * @return The response body
	 */
	private static String fireRequest(URL url) {
		try {
			Charset charset = StandardCharsets.UTF_8;
			URLConnection connection;
			connection = url.openConnection();
			connection.setRequestProperty("Accept-Charset", charset.toString());
			InputStream response = connection.getInputStream();

			Scanner scanner = new Scanner(response);
			String responseBody = scanner.useDelimiter("\\A").next();
			scanner.close();
			return responseBody;
		} catch (IOException e) {
			//Could not open connection.
			ExceptionDialog exDialog = new ExceptionDialog(e, "002", "Keine Verbindung zur Datenbank möglich! Bitte überprüfen Sie Ihre Internetverbindung."); //Fehler:002
			exDialog.showEcxeptionDialog();
			// e.printStackTrace();
		}
		return ERROR;
	}
}