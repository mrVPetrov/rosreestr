import rosreestr.RealEstate_Object_Data;
import rosreestr.Rosreestr_API;
import rosreestr.Rosreestr_Site;

/**
 * Created by VPetrov on 06.04.2017.
 */
public class Main {
	public static void main(String[] args){
		RealEstate_Object_Data data = null;
		Command_Line_Args arg = Command_Line_Args.getSingle(args);
		switch (arg.get_Parse_Type()){
			case API:
				data = Rosreestr_API.get_Object_By_Cadastrial_Num(arg.get_Cad_Num());
				break;
			case SITE:
				data = Rosreestr_Site.parse(arg.get_Cad_Num());
				break;
		}
		System.out.println(arg.get_Cad_Num());
		//data = Rosreestr_API.get_Object_By_Cadastrial_Num("02:55:020105:1960");

		data.dump();
	}
}
