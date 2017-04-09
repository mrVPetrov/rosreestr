import java.util.Hashtable;

/**
 * Created by VPetrov on 23.12.2016.
 */

/**
 * Обработка параметров командной строки
 */
public class Command_Line_Args {
	private static Command_Line_Args inst;
	private String cad_Num;
	private Parse_Type parse_Type;
	private Hashtable<String, String> args;

	private Command_Line_Args(String[] cmd_Args){
		args = new Hashtable<>(5);
		reset_Properties();
		for (int i = 0; i < cmd_Args.length; i += 2) {
			if(i + 1 > cmd_Args.length - 1){
				args.put(cmd_Args[i], null);
			} else {
				args.put(cmd_Args[i], cmd_Args[i + 1]);
			}
		}
	}

	public static Command_Line_Args getSingle(String[] cmd_Args){
		if(inst == null){
			inst = new Command_Line_Args(cmd_Args);
		}
		return inst;
	}

	private void reset_Properties(){
		cad_Num = "";
		parse_Type = Parse_Type.API;
	}

	String get_Cad_Num(){
		String res = "";
		if(args.containsKey("-cn")) {
			res = args.get("-cn");
		}
		return res;
	}

	Parse_Type get_Parse_Type(){
		Parse_Type res = Parse_Type.SITE;

		if(args.containsKey("-pt")) {
			switch (args.get("-pt")){
				case "api" :
					res = Parse_Type.API;
					break;
				case "site" :
					res = Parse_Type.SITE;
					break;
				default:
					res = Parse_Type.SITE;
			}
		}
		return res;
	}
}