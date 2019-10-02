package simulator.factories;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;


public class BuilderBasedFactory<T> implements Factory<T> {
	List<Builder<T>> listaBuilders;
	
	public BuilderBasedFactory(List<Builder<T>> listaBuilders) {
		this.listaBuilders = listaBuilders;
	}
	@Override
	public T createInstance(JSONObject info) {
		int i =0;
		T instancia = null;
		
		while (i < listaBuilders.size()) {
			instancia = listaBuilders.get(i).createInstance(info);
			if(instancia != null) {
				return instancia;
			}
			else i++;
		}
		return instancia;
	}
	
	@Override
	public List<JSONObject> getInfo() {
		List<JSONObject> listJson = new ArrayList<JSONObject>();
		JSONObject info = new JSONObject();
		info.put("type", "ftcg");
		info.put("desc",  "FallingToCenterGravity");
		listJson.add(info);
		
		JSONObject info1 = new JSONObject();
		info1.put("type", "nlug");
		info1.put("desc",  "Newton law of universal gravitation");
		listJson.add(info1);
		
		JSONObject info2 = new JSONObject();
		info2.put("type", "ng");
		info2.put("desc",  "NoGravity");
		listJson.add(info2);
		return listJson;
	}

}