package rosreestr;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by VPetrov on 06.04.2017.
 */

/**
 * Класс для парсинга сайта
 */
public class Rosreestr_Site {
	/**
	 * Парсит сайт для получения информации об объекте
	 * @param cad_Num
	 * @return
	 */
	public static RealEstate_Object_Data parse(String cad_Num){
		RealEstate_Object_Data data = new RealEstate_Object_Data();
		data.cadastrial_Num = cad_Num;
		try {
			Document doc = Jsoup.parse(get_HTML(cad_Num), "UTF8", "https://rosreestr.ru");
			Elements el = doc.select("div.portlet-body table td.brdw1010 table");

			Elements tmp = el.select("td:contains(Статус объекта:)");
			data.state = tmp.isEmpty() ? "" : tmp.first().nextElementSibling().text();

			tmp = el.select("td:contains(Дата постановки на кадастровый учет:)");
			data.cadastrial_Accounting_Date = tmp.isEmpty() ? "" : tmp.first().nextElementSibling().text();

			tmp = el.select("td:contains(Адрес (местоположение):)");
			data.address = tmp.isEmpty() ? "" : tmp.first().nextElementSibling().text();

			tmp = el.select("td:contains(Площадь ОКС'a:)");
			data.OKS_Area = tmp.isEmpty() ? "" : tmp.first().nextElementSibling().text();

			tmp = el.select("td:contains((ОКС) Тип:)");
			data.OKS_Type = tmp.isEmpty() ? "" : tmp.first().nextElementSibling().text();

			tmp = el.select("td:contains(Единица измерения (код):)");
			data.unit = tmp.isEmpty() ? "" : tmp.first().nextElementSibling().text();

			tmp = el.select("td:contains(Кадастровая стоимость:)");
			data.cadastrial_Cost = tmp.isEmpty() ? "" : tmp.first().nextElementSibling().text();

			tmp = el.select("td:contains(Дата внесения стоимости:)");
			data.cost_Insert_Date = tmp.isEmpty() ? "" : tmp.first().nextElementSibling().text();

			tmp = el.select("td:contains(Дата утверждения стоимости:)");
			data.cost_Commit_Date = tmp.isEmpty() ? "" : tmp.first().nextElementSibling().text();

			tmp = el.select("td:contains(Дата определения стоимости:)");
			data.cost_Determine_Date = tmp.isEmpty() ? "" : tmp.first().nextElementSibling().text();

			tmp = el.select("td:contains(Этаж:)");
			data.floor = tmp.isEmpty() ? "" : tmp.first().nextElementSibling().text();

			tmp = el.select("td:contains(Форма собственности:)");
			data.ownership_Type = tmp.isEmpty() ? "" : tmp.first().nextElementSibling().text();

			tmp = el.select("td:contains(Дата обновления информации:)");
			data.update_Date = tmp.isEmpty() ? "" : tmp.first().nextElementSibling().text();

			el = doc.select("div#r_enc table tr td[width=35%]");
			if(!el.isEmpty()){
				Rights_n_Restrictions r = null;
				for (Element item : el ) {
					r = new Rights_n_Restrictions();
					r.rights = item.text();
					r.restrictions = item.nextElementSibling().text();
					data.rights.add(r);
				}
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}

		return data;
	}

	/**
	 * Получаем HTML с данными объекта
	 * @param cad_Num кадастровый номер объекта
	 * @return
	 */
	private static InputStream get_HTML(String cad_Num){
		InputStream res = null;
		try{
			URL address = new URL(
			"https://rosreestr.ru/wps/portal/p/cc_ib_portal_services/online_request/!ut/p/z1/" +
				"pVHLTsMwEPyaXOt12tDHzS1VgYpneTS5RI5ZkiDHjhwHib_HTiJACNoDlg_enZnd2TVJyJ4kir-VObelVly6OE5O0s3tZE1XE7" +
				"rd3NIpsHO2pWO6AbiPyNNBwjUlyX_0juD18Mdh4PTJwRar8AjBWzzW5IIkudRZvw-msvEsJ4nBFzRoRq1x6cLaulkEEEDFSznKtc4l" +
				"joSu-kQAbQAO_E1f6MaS_Q8Zid1epinQM0bZJNzCej0Htnx8XO7GuxCAkp23LbSyRkuJhsQB7JAbUTDh_86jhW4bdJXcU8vnVLVV5o" +
				"k-bqxBtJ_vVtjWDFRec2MrVAMqeKckMYSLKFpACBSiBZ1Ppl91UvteO7ULoE-6Cmmtm7JzEkdznzVlXtjUYD50bbNXFEOTrC3lc6kG" +
				"yHE6XUdDayV-2Wm6GVPeDxk769jNl732Lr9xelMrdppePVwu13ceQiXcFgxXAgcrdfWwh_KmeprZGYyr2R-XfQBlZZxy/p0/IZ7_01" +
				"HA1A42K0EE90ABVVBS3S2001=CZ6_GQ4E1C41KGQ170AIAK131G00T5=MEcontroller!QCPObjectDataController==/?" +
				"object_data_id=" + cad_Num + "&dbName=firLite&region_key=" + Rosreestr_API.get_Region_Code(cad_Num)
			);

			res = address.openStream();
		} catch(Exception ex){
			ex.printStackTrace();
		}

		return  res;
	}
}
