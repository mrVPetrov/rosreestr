package rosreestr;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by VPetrov on 06.04.2017.
 */

/**
 * Класс для получения данных через API росреестра
 */
public class Rosreestr_API {
	private static String objectInfoFull(String num){
		String res = "";
		try {
			URL service = new URL("https://apirosreestr.ru/api/cadaster/objectInfoFull");
			HttpURLConnection connection = (HttpURLConnection) service.openConnection();
			connection.setRequestMethod("POST");
			String token = "EZVJ-QFZL-2SZB-NOY2";
			connection.setRequestProperty("Token", token);
			connection.setDoOutput(true);

			DataOutputStream os = new DataOutputStream(connection.getOutputStream());
			os.writeBytes("query=" + num);
			os.close();

			InputStream is = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder();

			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			res = response.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return res;
	}

	private static RealEstate_Object_Data parse_objectInfoFull_JSON(String json){
		RealEstate_Object_Data res = new RealEstate_Object_Data();

		JsonParser parser = new JsonParser();
		JsonObject egrn = parser.parse(json).getAsJsonObject().getAsJsonObject("EGRN");
		JsonObject details = egrn.getAsJsonObject("details");
		//JsonObject object = egrn.getAsJsonObject("object");

		res.object_Type = details.has("Тип объекта") ? details.get("Тип объекта").getAsString() : "";
		res.cadastrial_Num = details.has("Кадастровый номер") ?
			details.get("Кадастровый номер").getAsString() : "";
		res.state = details.has("Статус объекта") ? details.get("Статус объекта").getAsString() : "";
		res.cadastrial_Accounting_Date = details.has("Дата постановки на кадастровый учет") ?
			details.get("Дата постановки на кадастровый учет").getAsString() : "";
		res.floor =  details.has("Этаж") ? details.get("Этаж").getAsString() : "";
		res.OKS_Area =  details.has("Площадь ОКС'a") ? details.get("Площадь ОКС'a").getAsString() : "";
		res.unit =  details.has("Единица измерения (код)") ? details.get("Единица измерения (код)").getAsString() : "";
		res.cadastrial_Cost =  details.has("Кадастровая стоимость") ? details.get("Кадастровая стоимость").getAsString() : "";
		res.cost_Insert_Date = details.has("Кадастровая стоимость") ? details.get("Дата внесения стоимости").getAsString() : "";
		res.cost_Commit_Date = details.has("Дата утверждения стоимости") ? details.get("Дата утверждения стоимости").getAsString() : "";
		res.cost_Determine_Date = details.has("Дата определения стоимости") ? details.get("Дата определения стоимости").getAsString() : "";
		res.address = details.has("Адрес (местоположение)") ? details.get("Адрес (местоположение)").getAsString() : "";
		res.OKS_Type = details.has("(ОКС) Тип") ? details.get("(ОКС) Тип").getAsString() : "";
		res.update_Date = details.has("Дата обновления информации") ? details.get("Дата обновления информации").getAsString() : "";
		/*res.type = object.get("TYPE").getAsString();
		res.area = object.get("AREA").getAsString();*/

		return res;
	}

	public static RealEstate_Object_Data get_Object_By_Cadastrial_Num(String num){
		return parse_objectInfoFull_JSON(objectInfoFull(num));
	}

	public static String get_Region_Code(String num){
		String res = "";
		try {
			URL service = new URL("http://rosreestr.ru/api/online/fir_objects/" + num);
			HttpURLConnection connection = (HttpURLConnection) service.openConnection();
			connection.setRequestMethod("GET");

			InputStream is = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder();

			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}

			JsonParser parser = new JsonParser();
			res = parser.parse(response.toString()).getAsJsonArray().get(0).getAsJsonObject().get("regionKey").getAsString();

			//System.out.print(res);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return  res;
	}
}
