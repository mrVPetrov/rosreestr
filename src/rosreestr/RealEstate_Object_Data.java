package rosreestr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VPetrov on 06.04.2017.
 */

/**
 * Структура данных объекта недвижимости
 */
public class RealEstate_Object_Data {
	String cadastrial_Num;
	String address;
	String type;
	String area;
	String object_Type;
	String state;
	String unit;
	String cadastrial_Accounting_Date;
	String floor;
	String OKS_Area;
	String cadastrial_Cost;
	String cost_Insert_Date;
	String cost_Commit_Date;
	String cost_Determine_Date;
	String OKS_Type;
	String update_Date;

	String ownership_Type;

	List<Rights_n_Restrictions> rights;

	RealEstate_Object_Data(){
		rights = new ArrayList<>(3);
	}

	public void dump(){
		System.out.println("Кадастровый номер:" + cadastrial_Num);
		System.out.println("Адрес:" + address);
		System.out.println("тип:" + type);
		System.out.println("площадь:" + area);
		System.out.println("Тип объекта:" + object_Type);
		System.out.println("Статус объекта:" + state);
		System.out.println("Единица измерения:" + unit);
		System.out.println("Дата постановки на кадастровый учет:" + cadastrial_Accounting_Date);
		System.out.println("этаж:" + floor);
		System.out.println("площадь ОКС:" + OKS_Area);
		System.out.println("кадастровая стоимость:" + cadastrial_Cost);
		System.out.println("Дата внесения стоимости:" + cost_Insert_Date);
		System.out.println("Дата утверждеия стоимости:" + cost_Commit_Date);
		System.out.println("Дата обновления информации:" + update_Date);
		System.out.println("Форма собственности:" + ownership_Type);

		System.out.println("Права и ограничения:");
		for (Rights_n_Restrictions item : rights) {
			System.out.println("\tПраво:" + item.rights + "Ограничение:" + item.restrictions);
		}
	}
}
